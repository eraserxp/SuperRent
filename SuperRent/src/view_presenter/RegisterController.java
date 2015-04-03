/**
 *
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.LoginModel;

/**
 *
 *
 */
public class RegisterController extends AbstractController implements Initializable {

    @FXML
    private Button register_button;
    
    @FXML
    private Button back_button;
    
     @FXML
    private Button clear_button;
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField repasswordField;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField addressField;
    
     @FXML
    private Label nameValidator;
     
    private boolean nameOK = false;
    
      @FXML
    private Label addressValidator;
     
    private boolean addOK = false;
    
     @FXML
    private Label phoneValidator;
     
    private boolean phoneOK = false;
    
     @FXML
    private Label passwordValidator;
     
    private boolean passOK = false;
    
    
    @FXML
    private Label usernameValidator;
     
    private boolean usernameOK = false;
    
     @FXML
    private Label registerValidator;
    
       String username,password,name,address,phone,type;
    
     private LoginModel loginModel = new LoginModel();
    
     private void setUpUserField() {
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
             if (!newValue) {
             if (isInputEmpty(usernameField)) {
                    showWarning(usernameValidator, "Username can't be empty!");
                    usernameOK = false;
                    
                } else {
                  username=usernameField.getText();                  
                   if(loginModel.isUserExist(username))
                   { 
                       showWarning(usernameValidator, "Username already exist!");
                       usernameOK = false;
                   }
                   else
                    usernameOK = true;
                  }
             }
            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(usernameValidator);
                hide(passwordValidator);
            }
        });
    }
    
    private void setUpNameField() {
        nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(nameField)) {
                    showWarning(nameValidator, "Name can't be empty!");
                    nameOK = false;
                } else {
                    nameOK = true;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(nameValidator);
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
    private void setUpPasswordField(PasswordField passwordField, PasswordField repasswordField, Label infoLabel) {

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                    passOK = true;
                } else {
                    passOK = false;
                }
            }

            //when user is entering something into the password field, clearAndDisable the button
            // and remove the warnings
            if (newValue) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
            }
        });
        
        repasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                    passOK = true;
                } else {
                    passOK = false;
                }
            }
            //when user is entering something into the password field, clearAndDisable the button
            // and remove the warnings
            if (newValue) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
            }
        });
    }
    
    
    @FXML
    private void handleloginButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        app_stage.setScene(scene);
        app_stage.show();
                
    } 
    
    @FXML
    private void handleGoBack(ActionEvent event) throws IOException {
        getStage().close();               
    } 
    
    @FXML
    private void handleregisterButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        
        boolean registerok=false;
        boolean registerdone=false;
        
        usernameField.requestFocus();
        passwordField.requestFocus();
        repasswordField.requestFocus();
        phoneField.requestFocus();
        addressField.requestFocus();
        register_button.requestFocus();  
        
        
        registerok = nameOK && usernameOK && passOK && phoneOK && addOK;
        System.out.println("RegisterOK = "+registerok);
        
        if(registerok)
        {
            username=usernameField.getText();
            password=passwordField.getText();
            name=nameField.getText();
            phone=phoneField.getText();
            phone=formatPhoneNo(phone);
            address=addressField.getText();
            type="customer";
            System.out.println(username);
            System.out.println(password);
            System.out.println(name);
            System.out.println(phone);
            System.out.println(address);
            
            registerdone=loginModel.addCustomer(username, password, name,type, phone, address);
            
            if(registerdone==true)
                showSuccessMessage(registerValidator,"Registration Sucessfull!");
            else
                showWarning(registerValidator,"Registration Unsucessfull!");
        }
        
        
                    
    }

       private void setUpManageAccountTab() {

        //set all validator labels to be invisible
        hide(nameValidator,phoneValidator, addressValidator,
                usernameValidator, passwordValidator);

        //clear the text fields
        clearText(nameField, usernameField, passwordField,
                repasswordField,phoneField,addressField);
        //disable the three buttons
//      disableNodes(addButton, removeButton, changeButton);
        setUpUserField();

        setUpPasswordField(passwordField, repasswordField, passwordValidator);
                
        setUpNameField();

        setUpPhoneField();

        setUpAddressField();

       

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUpManageAccountTab();
        
        
        
        clear_button.setOnAction((event) -> {
    // Button was clicked, do something...
          clearText(nameField, usernameField, passwordField,
                repasswordField,phoneField,addressField);
          hide(nameValidator,phoneValidator, addressValidator,
                usernameValidator, passwordValidator);
      });
        
    }

}
