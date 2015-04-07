/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AppContext;
import model.ClerkModel;
import model.CustomerModel;
import model.UserModel;
import model.VehicleSelection;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class PaymentCCViewController extends AbstractController implements Initializable {

    @FXML
    private RadioButton creditcardRB;
    
    @FXML
    private RadioButton cashRB;

    final private ToggleGroup group = new ToggleGroup();

    @FXML
    private ComboBox<String> cardtypeCMB;
    
    @FXML
    private TextField cardnumberTF;
    
    @FXML
    private TextField expirydateTF;
    
    private UserModel userModel;
    
    @FXML
    private TextField AmountTF;
    
    @FXML
    private Label cardtypelabel;
    
    @FXML
    private Label cardnumberlabel;
    
    @FXML
    private Label expirydatelabel;
    
    @FXML
    private Label amountlabel;
    
    @FXML
    private Label cardnumbervalidator;
    
    @FXML
    private Label expirydatevalidator;
        
    @FXML
    private Label amountvalidator;
    
    @FXML
    private Label cardtypevalidator;
    
    @FXML
    private Label amountvaluelabel; //infoLabel

    @FXML
    private Label sucesslabel;
    
    @FXML
    private Separator sep;
    
    ArrayList cardtypes = new ArrayList();
    
    @FXML
    private Button Proceed_button;
    
    @FXML
    private Button GoBack_button;
    
    
    private String radiobutton,cardtype,user_type,user_name;
    
    boolean cardnumberOK,expirydateOK,amountOK;
    
    
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        hide(cardnumbervalidator,expirydatevalidator,amountvalidator);
        user_type=AppContext.getInstance().getUserType();
        user_name=AppContext.getInstance().getUsername();
        
        switch (user_type) {
            case "CUSTOMER":
                userModel = new CustomerModel();
                break;
            case "CLERK":
                userModel = new ClerkModel();
                break;
        }

            
        // do things differently for customer and employee
       /* String userType = AppContext.getInstance().getUserType();
        if (userType.equals("CUSTOMER")) {
            hide(creditcardRB,cashRB,amountlabel,AmountTF,amountvalidator);
        } else {
                setUpPaymentRB();
        }
        */
        setUpCardTypeCMB();
        setUpPaymentRB();
        setUpCardNumber();
        setUpExpiryDate();
        setUpAmountField();
    }

    private void setUpCardTypeCMB() {
         
         cardtypes.add("MasterCard");
         cardtypes.add("Visa");
         cardtypes.add("American Express");
        configureComboBox(cardtypeCMB, cardtypes);
        cardtypeCMB.getSelectionModel().selectFirst();
        cardtypeCMB.setOnAction((ActionEvent event) -> {
             cardtype = cardtypeCMB.getSelectionModel().getSelectedItem().toString();
             
        });
    }

    private void setUpPaymentRB() {
        creditcardRB.setToggleGroup(group);
        cashRB.setToggleGroup(group);
        creditcardRB.setSelected(true);
        disableNodes(amountlabel,AmountTF,amountvalidator);
        enableNodes(cardtypelabel,cardnumberlabel,cardnumberTF,cardnumbervalidator,cardtypeCMB,cardtypevalidator,
                                        expirydateTF,expirydatelabel,expirydatevalidator);
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
        if (group.getSelectedToggle() != null) {
                
                    RadioButton rb = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                    radiobutton = rb.getText();
                    
                    if(radiobutton.equals("By Credit Card"))
                    {
                                disableNodes(amountlabel,AmountTF,amountvalidator);
                                hide(amountvalidator);
                                enableNodes(cardtypelabel,cardnumberlabel,cardnumberTF,cardnumbervalidator,cardtypeCMB,cardtypevalidator,
                                        expirydateTF,expirydatelabel,expirydatevalidator);
                    }
                    
                    else if(radiobutton.equals("By Cash"))
                    {
                                disableNodes(cardtypelabel,cardnumberlabel,cardnumberTF,cardnumbervalidator,cardtypeCMB,cardtypevalidator,
                                        expirydateTF,expirydatelabel,expirydatevalidator);
                                hide(cardnumbervalidator,expirydatevalidator,cardtypevalidator);
                                enableNodes(amountlabel,AmountTF,amountvalidator);
                    }
                            
                            
        }
    }
});
}

    private void setUpCardNumber() {
        
         cardnumberTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(cardnumberTF)) {
                    showWarning(cardnumbervalidator, "Card Number can't be empty!");
                    cardnumberOK = false;
                } else {
                    if(isCardNo(cardnumberTF,cardtype))
                    {
                        cardnumberOK = true;
                    }
                    else 
                    {
                      
                      showWarning(cardnumbervalidator, "Card Number Format is incorrect!");
                      cardnumberOK=false;  
                    }
                    
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(cardnumbervalidator);
            }
        });
        
        
        
    }

    
    private void setUpExpiryDate() {
        
         expirydateTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(expirydateTF)) {
                    showWarning(expirydatevalidator, "Expiry Date can't be empty!");
                    expirydateOK = false;
                } else {
                    if(isExpiryDateNo(expirydateTF))
                    {
                        expirydateOK = true;
                    }
                    else 
                    {
                      showWarning(expirydatevalidator, "Expiry Date Format is incorrect!");
                      expirydateOK=false;  
                    }
                    
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(expirydatevalidator);
            }
        });
        
        
        
    }
    
    private void setUpAmountField() {
        AmountTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(AmountTF)) {
                    showWarning(amountvalidator, "Amount can't be empty!");
                    amountOK = false;
                } else {
                    amountOK = true;
                }
            }

            
            if (newValue) {
                hide(amountvalidator);
            }
        });
    }
    
    @FXML
    private void handleProceedButtonAction(ActionEvent event) throws IOException {
        
          
        boolean check=false;
       //System.out.print(user_type);
        
        
        
        if(user_type.equals("CUSTOMER"))
        {
            check=cardnumberOK&&expirydateOK;
           // showSuccessMessage(sucesslabel,"creditcardcustomer");
            
            cardnumberTF.requestFocus();
            expirydateTF.requestFocus();
            Proceed_button.requestFocus();
        }
        else if(user_type.equals("CLERK"))
        {
            if(radiobutton.equals("By Credit Card"))
                    {
                             check=cardnumberOK&&expirydateOK;
                            // showSuccessMessage(sucesslabel,"creditcardclerk");
                             cardnumberTF.requestFocus();
                             expirydateTF.requestFocus();
                             Proceed_button.requestFocus();
                    }
                    
                    else if(radiobutton.equals("By Cash"))
                    {
                            check=amountOK;  
                            //showSuccessMessage(sucesslabel,"cashclerk");
                            AmountTF.requestFocus();
                            Proceed_button.requestFocus();
                    }
        }
        
        if(check==true)
        {
            showSuccessMessage(sucesslabel,"Payment Successful");
           // AppContext.getInstance().setUserType("success");  /*To know the payment was successfull*/
        }
        
    }
     @FXML
    private void handleGoBackButtonAction(ActionEvent event) throws IOException {
     Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     app_stage.hide();
     Parent next_page_parent = null;
     AppContext.getInstance().setUsername(user_name);
     AppContext.getInstance().setUserType(user_type);
     next_page_parent = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
    }
}