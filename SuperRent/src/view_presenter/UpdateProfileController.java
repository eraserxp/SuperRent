/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.AdminModel;
import model.AppContext;
import model.CustomerModel;
import model.ValidationResult;

/**
 * FXML Controller class
 *
 *
 */
public class UpdateProfileController extends AbstractController implements Initializable {

    @FXML
    private Label changeInfoLabel;

    @FXML
    private PasswordField oldpassField;

    @FXML
    private Label oldpassValidatorLabel;

    private boolean oldpassOK ;
    
    @FXML
    private Label newpassValidatorLabel;

    private boolean newpassOK ;

    @FXML
    private PasswordField passwdField;

    @FXML
    private PasswordField repasswdField;

    @FXML
    private Label passwdValidatorLabel;

    private ValidationResult passwdValidation = new ValidationResult();

    @FXML
    private Button changepassButton;
    
    @FXML
    private Button changephoneButton;
    
    @FXML
    private Button changeaddButton;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField addressField;
    
    @FXML
    private Label phoneValidator;
    
     @FXML
    private Label addressValidator;
     
     private boolean addOK = false;
     private boolean phoneOK = false;

    String user_type,user_name;
            
    private CustomerModel cusModel = new CustomerModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user_type=AppContext.getInstance().getUserType();
        user_name=AppContext.getInstance().getUsername();
        setUp();
    }

    private void setUp() {
        hide(changeInfoLabel, oldpassValidatorLabel,newpassValidatorLabel, passwdValidatorLabel,phoneValidator,addressValidator);
        clearText(oldpassField, passwdField, repasswdField,addressField,phoneField);
        setUpOldPassField(oldpassField);
        setUpNewPassField(oldpassField,passwdField);
        setUpPasswordField(passwdField, repasswdField, passwdValidatorLabel, passwdValidation);
        setUpAddressField();
        setUpPhoneField();
    }

    private void setUpOldPassField(TextField oldpasstextfield) {
        
        String currentpass;
        
        currentpass=cusModel.getPassword(user_name);
        //System.out.println("Username "+user_name);
        
        oldpassField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(oldpassField)) {
                    showWarning(oldpassValidatorLabel, "Old Password can't be empty!");
                    oldpassOK = false;
                } else {
                    
                    String oldpasstext=oldpasstextfield.getText().trim();
                    //System.out.println("OLDPASSWORD "+oldpasstext);
                    
                    if(oldpasstext.equals(currentpass))
                     oldpassOK=true;
                   else 
                     {
                      showWarning(oldpassValidatorLabel, "Old Password is incorrect!");
                      oldpassOK=false;            
                      }
                         
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(oldpassValidatorLabel);
            }
        });
        
    }

    private void setUpNewPassField(TextField oldpasstextfield,TextField newpasstextfield) {
        
        //System.out.println("Username "+user_name);
        
        passwdField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(passwdField)) {
                    showWarning(newpassValidatorLabel, "New Password can't be empty!");
                    newpassOK = false;
                } else {
                    
                    String oldpasstext=oldpasstextfield.getText().trim();
                     String newpasstext=newpasstextfield.getText().trim();
                    //System.out.println("OLDPASSWORD "+oldpasstext);
                    
                    if(oldpasstext.equals(newpasstext))
                    {
                        showWarning(newpassValidatorLabel, "Old and New Password can't be same!");
                      newpassOK=false;            
                    }
                     
                   else 
                     {
                      newpassOK=true;            
                      }
                         
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(newpassValidatorLabel);
            }
        });
        
    }
    private void setUpAddressField() {
        addressField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(addressField)) {
                    showWarning(addressValidator, "Address can't be empty!");
                    addOK = false;
                } else {
                    addOK = true;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(addressValidator);
            }
        });
    }
    
   
    
    private void setUpPhoneField() {
        phoneField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(phoneField)) {
                    showWarning(phoneValidator, "Phone can't be empty!");
                    phoneOK = false;
                } else {
                    if(isInputPhoneNo(phoneField))
                    {
                        phoneOK = true;
                    }
                    else 
                    {
                      showWarning(phoneValidator, "Phone Format is incorrect!");
                      phoneOK=false;  
                    }
                    
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(phoneValidator);
            }
        });
    }
    
    @FXML
    public void handleChangePassButton() {
        System.out.println("inside handle change password button ");
        
        oldpassField.requestFocus();
        passwdField.requestFocus();
        repasswdField.requestFocus();
        changepassButton.requestFocus();
        String oldpass = "";

        boolean readyToChange = oldpassOK && newpassOK && passwdValidation.get();
        System.out.println("oldpasswordOK = " + oldpassOK);
        System.out.println("passwordOK = " + passwdValidation.get());
        boolean changeOK = false;
        if (readyToChange) {
            oldpass = oldpassField.getText().trim();
            String passwd = repasswdField.getText().trim();
            changeOK = cusModel.changePasswd(user_name, passwd);
        }

        if (changeOK) {
            showSuccessMessage(changeInfoLabel, "The password for has been updated.");
            hide(oldpassValidatorLabel, passwdValidatorLabel);
            clearText(oldpassField, passwdField, repasswdField);
        }
    }

@FXML
public void handleChangePhoneButton() {
        System.out.println("inside handle change phone button ");
        
        phoneField.requestFocus();
        changephoneButton.requestFocus();
        String phone = "";

        boolean readyToChange = phoneOK;
        System.out.println("phoneOK = " + phoneOK);
        boolean changeOK = false;
        if (readyToChange) {
            phone = phoneField.getText().trim();
            changeOK = cusModel.changePhone(user_name, phone);
        }

        if (changeOK) {
            showSuccessMessage(changeInfoLabel, "The phone for has been updated.");
            hide(phoneValidator);
            clearText(phoneField);
        }
    }

@FXML
public void handleChangeAddressButton() {
        System.out.println("inside handle change address button ");
        
        addressField.requestFocus();
        changeaddButton.requestFocus();
        String address = "";

        boolean readyToChange = addOK;
        System.out.println("addOK = " + addOK);
        boolean changeOK = false;
        if (readyToChange) {
            address = addressField.getText().trim();
            changeOK = cusModel.changeAddress(user_name, address);
        }

        if (changeOK) {
            showSuccessMessage(changeInfoLabel, "The address for has been updated.");
            hide(addressValidator);
            clearText(addressField);
        }
    }
}
