/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Button cancelButton;

    @FXML
    private Label namelabel;
    
    @FXML
    private Label   addresslabel;
    
    @FXML
    private Label annualfeelabel;
    
    @FXML
    private Label annuallabel;
            
    @FXML
    private Label lastpaydatelabel;
    
    @FXML
    private Label paydatelabel;
    
    @FXML
    private Label memvaliduptolabel;
    
    @FXML
    private Label memvaliddatelabel;
    
    @FXML
    private Label memstatuslabel;
    
    @FXML
    private Label statuslabel;
    
    private PaymentModel payModel = new PaymentModel();
    
    String user_type,username,amount="100",name,address,success;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        user_type=AppContext.getInstance().getUserType();
        username=AppContext.getInstance().getUsername();
        setUp();
        
         
    }

    private void setUp() {
        hide(NameValidator,addressValidator);
        
        setUpNameField();
        setUpAddressField();
        setUpMember();

    }
    
    private void setUpMember() {
        
        boolean isCMember,isOverdue;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date payment_date;
        Date date_return;
        
        isCMember=payModel.isMember(username);
        cancelButton.setDisable(true);
        
        
        //System.out.println("Is Club Member"+isCMember);
        
        if(isCMember==true)
        {
            hide(namelabel,NameField,NameValidator,addresslabel,AddressField,addressValidator,
                  annualfeelabel,annuallabel,applyButton,cancelButton);
            payment_date=payModel.MemberPayDate(username);
            date_return=payModel.GetReturnDate(payment_date);
            isOverdue=payModel.isMembershipOverdue(username);
            
            paydatelabel.setText(dateFormat.format(payment_date));
            memvaliddatelabel.setText(dateFormat.format(date_return));
            
            if(isOverdue==true)
            {
            statuslabel.setText("Membership Overdue");
            }
            else 
            {
            statuslabel.setText("Membership Period is Valid");
            hide(makepaymentButton);
            }
        }
          
        else if (isCMember==false)
        {
           hide(lastpaydatelabel,paydatelabel,memvaliduptolabel,memvaliddatelabel,memstatuslabel,statuslabel,
           namelabel,NameField,NameValidator,addresslabel,AddressField,addressValidator,
                  annualfeelabel,annuallabel,makepaymentButton); 
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

    @FXML
    public void handleMakePaymentButton(ActionEvent event)throws IOException, ParseException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Parent next_page_parent = null;
        
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
              boolean updateOK=false;
        
        name=NameField.getText();
        address=AddressField.getText();
                              
        setupNextPage(this, "PaymentCCView.fxml", "Payment");
        
        success=AppContext.getInstance().getTempData("status");
        
        if(success.equals("success"))
        {
            popUpMessage("Payment is successful!");
            updateOK=payModel.updateCustomer(username, name, address);
            hide(namelabel,NameField,NameValidator,addresslabel,AddressField,addressValidator,
                  annualfeelabel,annuallabel,applyButton,cancelButton);
            show(lastpaydatelabel,paydatelabel,memvaliduptolabel,memvaliddatelabel,memstatuslabel,statuslabel);
            
        }
        
            
        }
  }
    @FXML
    public void handleApplyButton(ActionEvent event)throws IOException {
        
        show(namelabel,NameField,NameValidator,addresslabel,AddressField,addressValidator,
                  annualfeelabel,annuallabel,makepaymentButton);
        
        applyButton.setDisable(true);
        cancelButton.setDisable(false);
        
       
    }
    
    @FXML
    public void handleCancelButton(ActionEvent event)throws IOException {
        
        hide(namelabel,NameField,NameValidator,addresslabel,AddressField,addressValidator,
                  annualfeelabel,annuallabel,makepaymentButton,cancelButton);
        
              
       
    }
    
    
}
