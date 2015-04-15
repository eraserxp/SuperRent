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

    private String radiobutton, cardtype, user_type, user_name, amount, Totalcost, payment_met;

    boolean cardnumberOK, expirydateOK, amountOK;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        hide(cardnumbervalidator, expirydatevalidator, amountvalidator);
        user_type = AppContext.getInstance().getUserType();
        user_name = AppContext.getInstance().getUsername();
        amount = AppContext.getInstance().getTempData("amount") + ".00";

        payment_met = AppContext.getInstance().getTempData("requestFrom");
//        Totalcost = AppContext.getInstance().getTempData("TotalCost");
        System.out.println("payment amount" + amount);
        amountvaluelabel.setText(amount);

        //set the payment status as failure so that if the window is closed
        //the previous page will know the status of the payment
        AppContext.getInstance().setTempData("status", "failure");

        switch (user_type) {
            case "CUSTOMER":
                userModel = new CustomerModel();
                break;
            case "CLERK":
                userModel = new ClerkModel();
                break;
        }

        // do things differently for customer and employee
        String userType = AppContext.getInstance().getUserType();
        if (userType.equals("CUSTOMER")) {
            hide(creditcardRB, cashRB, amountlabel, AmountTF, amountvalidator, sep);
        } else {
            setUpPaymentRB();
            if (payment_met.equals("rentPage")) {
                hide(creditcardRB, cashRB, amountlabel, AmountTF, amountvalidator, sep);
            } else if (payment_met.equals("returnPage")) {
                show(creditcardRB, cashRB, amountlabel, AmountTF, amountvalidator, sep);
            }

        }

        setUpCardTypeCMB();
        //setUpPaymentRB();
        setUpCardNumber();
        setUpExpiryDate();
        setUpAmountField();
        radiobutton = "By Credit Card";
    }

    private void setUpCardTypeCMB() {

        cardtypes.add("MasterCard");
        cardtypes.add("Visa");
        cardtypes.add("American Express");
        configureComboBox(cardtypeCMB, cardtypes);
        // cardtypeCMB.getSelectionModel().selectFirst();
        cardtypeCMB.setOnAction((ActionEvent event) -> {
            // cardtype = cardtypeCMB.getSelectionModel().getSelectedItem().toString();

            if (cardtypeCMB.getSelectionModel().isEmpty()) {
                showWarning(cardtypevalidator, "Cardtype can't be empty!");

            } else {
                hide(cardtypevalidator);
                showWarning(cardtypevalidator, "");
                cardtype = cardtypeCMB.getSelectionModel().getSelectedItem().toString();

            }

        });
    }

    private void setUpPaymentRB() {
        creditcardRB.setToggleGroup(group);
        cashRB.setToggleGroup(group);
        creditcardRB.setSelected(true);
        disableNodes(amountlabel, AmountTF, amountvalidator);
        enableNodes(cardtypelabel, cardnumberlabel, cardnumberTF, cardnumbervalidator,
                cardtypeCMB, cardtypevalidator,
                expirydateTF, expirydatelabel, expirydatevalidator);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {

                    RadioButton rb = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                    radiobutton = rb.getText();
                    System.out.println(radiobutton + " has been selected");
                    if (radiobutton.equals("By Credit Card")) {
                        disableNodes(amountlabel, AmountTF, amountvalidator);
                        hide(amountvalidator);
                        enableNodes(cardtypelabel, cardnumberlabel, cardnumberTF, cardnumbervalidator, cardtypeCMB, cardtypevalidator,
                                expirydateTF, expirydatelabel, expirydatevalidator);
//                        AppContext.getInstance().setTempData("Paymentmethod", "Visa");
                    } else if (radiobutton.equals("By Cash")) {
                        disableNodes(cardtypelabel, cardnumberlabel, cardnumberTF, cardnumbervalidator, cardtypeCMB, cardtypevalidator,
                                expirydateTF, expirydatelabel, expirydatevalidator);
                        hide(cardnumbervalidator, expirydatevalidator, cardtypevalidator);
                        enableNodes(amountlabel, AmountTF, amountvalidator);
//                        AppContext.getInstance().setTempData("Paymentmethod", "Cash");
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
                    showWarning(cardnumbervalidator, "Can't be empty!");
                    cardnumberOK = false;
                } else {
                    if (isCardNo(cardnumberTF, cardtype)) {
                        cardnumberOK = true;
                    } else {

                        showWarning(cardnumbervalidator, "Incorrect format!");
                        cardnumberOK = false;
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
                    showWarning(expirydatevalidator, "Can't be empty!");
                    expirydateOK = false;
                } else {
                    if (isExpiryDateNo(expirydateTF)) {
                        expirydateOK = true;
                    } else {
                        showWarning(expirydatevalidator, "Incorrect format!");
                        expirydateOK = false;
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
                    showWarning(amountvalidator, "Can't be empty!");
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

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        boolean check = false;
        //System.out.print(user_type);

        if (user_type.equals("CUSTOMER")) {
            check = cardnumberOK && expirydateOK;
            // showSuccessMessage(sucesslabel,"creditcardcustomer");

            cardnumberTF.requestFocus();
            expirydateTF.requestFocus();
            Proceed_button.requestFocus();
        } else if (user_type.equals("CLERK")) {
            if (radiobutton.equals("By Credit Card")) {
                check = cardnumberOK && expirydateOK;
                // showSuccessMessage(sucesslabel,"creditcardclerk");
                AppContext.getInstance().setTempData("Paymentmethod", "Visa");
                cardnumberTF.requestFocus();
                expirydateTF.requestFocus();
                Proceed_button.requestFocus();
            } else if (radiobutton.equals("By Cash")) {
                AppContext.getInstance().setTempData("Paymentmethod", "Cash");
                check = amountOK;
                //showSuccessMessage(sucesslabel,"cashclerk");
                AmountTF.requestFocus();
                Proceed_button.requestFocus();
            }

        }

        if (check == true) {
            //System.out.println(cardtype);
            //System.out.println(cardnumberTF.getText().trim());
            //System.out.println(expirydateTF.getText().trim());

            AppContext.getInstance().setTempData("card_type", cardtype);
            AppContext.getInstance().setTempData("card_no", cardnumberTF.getText().trim());
            AppContext.getInstance().setTempData("expiry_date", expirydateTF.getText().trim());
            //showSuccessMessage(sucesslabel, "Payment Successful");
            AppContext.getInstance().setTempData("status", "success");

            app_stage.hide();
            /*To know the payment was successfull*/
        } else {
            AppContext.getInstance().setTempData("status", "failure");
        }

//        if (check == true && radiobutton.equals("By Cash")) {
//            AppContext.getInstance().setTempData("card_type", "null");
//            AppContext.getInstance().setTempData("card_no", "null");
//            AppContext.getInstance().setTempData("expiry_date", "null");
//            showSuccessMessage(sucesslabel, "Payment Successful");
//            // AppContext.getInstance().setUserType("success");  /*To know the payment was successfull*/
//        }
    }

}
