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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ClerkModel;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class ReturnViewController extends AbstractController implements Initializable {

    //Return Details
    @FXML
    private TextField PlateNoTextField;
    @FXML
    private DatePicker ReturnDatePicker;
    @FXML
    private ComboBox ReturnTimeCombox;
    @FXML
    private TextField OdometerTextField;
    @FXML
    private ComboBox TankFullCombox;
    @FXML
    private ComboBox Equip1Combox;
    @FXML
    private ComboBox Equip2Combox;
    @FXML
    private Button ConfirmButton;
    //Rent Summary
    //Customer username
    @FXML
    private Label UsernameLabel;
    @FXML
    private Label PlatenoLabel;
    @FXML
    private Label PickupLocationLabel;
    @FXML
    private Label PickupTimeLabel;
    @FXML
    private Label ReturnLocationLabel;
    @FXML
    private Label ReturnTimeLabel;
    @FXML
    private Label Equipment1;
    @FXML
    private Label Number1;
    @FXML
    private Label Equipment2;
    @FXML
    private Label Number2;
    @FXML
    private Button CheckOutButton;    

    //labels to show the cost
    @FXML
    private Label rentalCharge;
    @FXML
    private Label insuranceCost;
    @FXML
    private Label additionalCost;
    @FXML
    private Label totalCost;

    //check thos in rent but not in return
    //date should be used
    private ClerkModel clerkModel = new ClerkModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hide(UsernameLabel, PlatenoLabel, PickupLocationLabel, PickupTimeLabel, ReturnLocationLabel, ReturnTimeLabel, Equipment1, Number1, Equipment2, Number2);

        handleConfirmButton();
        handleCheckOutButton();
    }

//    public void setUpVehicleTextField() {
//
//        PlateNoTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
//            // lost focus
//            String checkString = PlateNoTextField.getText();
//            ArrayList<String> checkValue = new ArrayList<>();
//            try {
//                checkValue = clerkModel.getRentDetails(checkString);
//            } catch (SQLException ex) {
//                Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            if (!newValue) {
//                if (checkString.isEmpty()) {
//                    //showWarning(Validator, "Vehicle License Number can't be empty!");
//                    PlateNoTextField.setDisable(false);
//                } else if (checkValue == null) {
//                    //showWarning(Validator, "Vehicle License Number is invalid!");
//                    PlateNoTextField.setDisable(false);
//                } else {
//                    //showWarning(Validator, "Valid!");
//                    PlateNoTextField.setDisable(true);
//                }
//            }
//        });
//
//    }
    //handle the actions of the confirm button
    //use to check all input are valid
    //then, show the rent and return details and all kinds of costs with the valid input
    public void handleConfirmButton() {
        ConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String checkString = PlateNoTextField.getText();
                LocalDate returnDate = ReturnDatePicker.getValue();
                Integer returnTime = ReturnTimeCombox.getSelectionModel().getSelectedIndex();
            }
        });

    }

    //when all input are valid
    //check out to the payment view to make payment
    public void handleCheckOutButton() {
        CheckOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                try {
                    showPaymentDialog();
                } catch (IOException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public void showPaymentDialog() throws IOException {
        AnchorPane loader = (AnchorPane) FXMLLoader.load(ReturnDialogViewController.class.getResource("PaymentView.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Payment");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader);
        dialogStage.setScene(scene);

        //do sth when the dialog is closed
        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Do sth when user click the X close icon of the dialog");

            }
        });
        dialogStage.showAndWait();

    }

    public void calculateCosts() {

        //get the required information
        //check reserve or not
        //overdue or not (penality applied?)
        //insurance for the overdue time
        //get the weeks,days and hours to calculate the rent charge
        //also include pk_rate and set a limit to it
        //calculate the insurance cost according to the time
        //calculate the additional cost considering the type of equipments and their quantities
        //how to set the gasline fees
        //calculate the total cost and set it to show automatically
    }

}
