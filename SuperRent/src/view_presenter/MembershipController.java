/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AdminModel;
import model.AppContext;
import model.PaymentModel;
import model.ValidationResult;

/**
 * FXML Controller class
 *
 *
 */
public class MembershipController extends AbstractController implements Initializable {

    
    @FXML
    private TextField NameField;

    @FXML
    private Label NameValidator;

    private boolean NameOK = false;

    @FXML
    private TextField AddressField;

    @FXML
    private Label addressValidator;
    
    private boolean AddOK = false;

    @FXML
    private Button makepaymentButton;
    
    @FXML
    private Button applyButton;
    
    @FXML
    private Label namelabel;
    
    @FXML
    private Label addresslabel;
    
    @FXML
    private Label annuallabel;
    
    @FXML
    private Label annualrate;

    private PaymentModel payModel = new PaymentModel();
    
    String user_name,user_type,amount;

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
        hide(NameValidator,addressValidator);
        
        setUpNameField();
        setUpAddressField();
        setUpMember();

    }
    
    private void setUpMember() 
    {
    
        boolean isMember=false;
        Date current_date;
        
        System.out.print("IsMember username"+user_name);
        isMember=payModel.isMembership(user_name);
        current_date=payModel.GetCurrentDate();
        System.out.print("IsMember"+isMember);
        System.out.print("Current_Date"+current_date);
        if(isMember==true)
            hide(applyButton,NameField,AddressField,makepaymentButton,namelabel,addresslabel,annuallabel,annualrate);
        else
        {
            
        }
    }

    private void setUpNameField() {
        NameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(NameField)) {
                    showWarning(NameValidator, "Name can't be empty!");
                    NameOK = false;
                } else {
                    NameOK = true;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(NameValidator);
            }
        });
    }
    
     private void setUpAddressField() {
        AddressField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(AddressField)) {
                    showWarning(addressValidator, "Address can't be empty!");
                    AddOK = false;
                } else {
                    AddOK = true;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(addressValidator);
            }
        });
    }

    
    public void handleMakePaymentButton(ActionEvent event)throws IOException {
        
               
        NameField.requestFocus();
        AddressField.requestFocus();
        makepaymentButton.requestFocus();        

        boolean readyToChange = NameOK&&AddOK;
        
        System.out.print("readytochange"+readyToChange);
        
        boolean changeOK = false;
        if (readyToChange) 
        {
            // AppContext.getInstance().setUsername(username);
             //AppContext.getInstance().setUserType(user_type);
             //AppContext.getInstance().setTempData("amount",amount);
            setupNextPage(this, "PaymentCCView.fxml", "Make Payment");
            
       }

        
    }
    
    
    /*public void handleApplyMembershipButton(ActionEvent event)throws IOException {
        
          
        NameField.requestFocus();
        AddressField.requestFocus();
        makepaymentButton.requestFocus();        

        boolean readyToChange = NameOK&&AddOK;
        
        boolean changeOK = false;
        if (readyToChange) 
        {
             AppContext.getInstance().setUsername(username);
             AppContext.getInstance().setUserType(user_type);
             AppContext.getInstance().setTempData("amount",amount);
             
            
        }

        
    }
    */
    
}
