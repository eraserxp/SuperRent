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
import java.time.format.DateTimeFormatter;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.AppContext;
import model.ClerkModel;
import model.UserModel;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

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
    private Label Equipment1Label;
    @FXML
    private Label Equipment2Label;
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

    @FXML
    private CheckBox RoadStar;
    @FXML
    private CheckBox Redeem1000P;
    @FXML
    private CheckBox Redeem1500P;

    //cost calculation grid pane
    private GridPane summaryGP;

    @FXML
    private VBox summaryVBox;

    //labels to show the cost
    //check thos in rent but not in return
    //date should be used
    private ClerkModel clerkModel = new ClerkModel();
    private UserModel userModel = new UserModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hide(UsernameLabel, PlatenoLabel, PickupLocationLabel, PickupTimeLabel, ReturnLocationLabel, ReturnTimeLabel, Equipment1, Number1, Equipment2, Number2);
        RoadStar.setDisable(true);
        Redeem1000P.setDisable(true);
        Redeem1500P.setDisable(true);

        setUpReturnTimeCombox();
        setUpTankFullCombox();
        setUpEquipmentCombox();

        handleConfirmButton();
        handleCheckOutButton();

        setupRoadStarCB();
        setupRedeem1000CB();
        setupRedeem1500CB();

    }

    //handle the actions of the confirm button
    //use to check all input are valid
    //then, show the rent and return details and all kinds of costs with the valid input
    private void showSummary() throws SQLException {

        String PlateNumString = PlateNoTextField.getText();
        LocalDate returnDate = ReturnDatePicker.getValue();
        String returnTimeString = ReturnTimeCombox.getSelectionModel().getSelectedItem().toString();
        Integer returnTimeInt = Integer.parseInt(returnTimeString);
        //Integer returnTime = Integer.parseInt(returnTimeString.split(":")[0]);
        //String OdometerReading = OdometerTextField.getText();
        //String TankFull = TankFullCombox.getSelectionModel().getSelectedItem().toString();
        //String Equipment1Selection = Equip1Combox.getSelectionModel().getSelectedItem().toString();
        //String Equipment2Selection = Equip2Combox.getSelectionModel().getSelectedItem().toString();

        ArrayList<String> rentList = new ArrayList<>();
        rentList = clerkModel.getRentDetails(PlateNumString);
        //System.out.println(rentList);
        String customerusername = rentList.get(0);
        UsernameLabel.setText(customerusername);
        PlatenoLabel.setText(PlateNumString);
        String pickuplocation = rentList.get(2) + ", " + rentList.get(1);
        PickupLocationLabel.setText(pickuplocation);

        LocalDate pickupDate = LocalDate.parse("rentList.get(3)");

        Integer pickuptimeint = Integer.parseInt(rentList.get(4));
        String pickupTime = " at " + rentList.get(4) + ":00" + " in " + rentList.get(3);
        PickupTimeLabel.setText(pickupTime);

        //Clerk name
        String username = AppContext.getInstance().getUsername();

        ArrayList<String> clerkbranch = new ArrayList<>();
        clerkbranch = clerkModel.getClerkDetails(username);
        String returnlocation = clerkbranch.get(1) + ", " + clerkbranch.get(0);
        ReturnLocationLabel.setText(returnlocation);

        String returnTime = " at " + returnTimeString + " in " + returnDate;
        ReturnTimeLabel.setText(returnTime);

        String rentid = rentList.get(5);
        ArrayList<String> equipmentslist = new ArrayList<>();
        equipmentslist = clerkModel.getEquipmentDetails(rentid);

        ArrayList<String> equipments = new ArrayList<>();
        equipments.add(equipmentslist.get(0));
        equipments.add(equipmentslist.get(2));
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(Integer.parseInt(equipmentslist.get(1)));
        quantities.add(Integer.parseInt(equipmentslist.get(2)));

        show(UsernameLabel, PlatenoLabel, PickupLocationLabel, PickupTimeLabel, ReturnLocationLabel, ReturnTimeLabel);

        if (equipmentslist.size() == 2) {
            Equipment1.setText(equipmentslist.get(0));
            Number1.setText(equipmentslist.get(1));
            show(Equipment1, Number1);
        } else {
            Equipment1.setText(equipmentslist.get(0));
            Number1.setText("-" + equipmentslist.get(1));
            Equipment2.setText(equipmentslist.get(2));
            Number2.setText("-" + equipmentslist.get(3));
            show(Equipment1, Number1, Equipment2, Number2);
        }

        int redeemedPoints = 0;
        boolean isRoadStar = false;
        int odometer = Integer.parseInt(OdometerTextField.getText());
        if (!RoadStar.isDisabled() && RoadStar.isSelected()) {
            isRoadStar = true;
        }

        if (!Redeem1000P.isDisabled() && Redeem1000P.isSelected()) {
            redeemedPoints = 1000;
        }

        if (!Redeem1500P.isDisabled() && Redeem1500P.isSelected()) {
            redeemedPoints = 1500;
        }

        if (summaryGP != null) {
            summaryVBox.getChildren().remove(summaryGP);
        }

        String vehicleType = clerkModel.getVehicleType(PlateNumString);

        summaryGP = userModel.calculateCost(vehicleType, equipments, quantities,
                pickupDate, pickuptimeint, returnDate, returnTimeInt, isRoadStar,
                redeemedPoints, odometer, PlateNumString);
        summaryVBox.getChildren().add(summaryGP);

        //System.out.println(equipmentslist);
        //[child_safety_seat, 1, ski_rack, 1]
    }

    public void handleConfirmButton() {
        ConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    //String checkString = PlateNoTextField.getText();
                    //LocalDate returnDate = ReturnDatePicker.getValue();
                    //Integer returnTime = ReturnTimeCombox.getSelectionModel().getSelectedIndex();

                    if (PlateNoTextField.getText().isEmpty()) {
                        popUpError("PlateNo is empty!");
                        return;
                    }

                    if (ReturnDatePicker.getValue() == null) {
                        popUpError("The return date is empty!");
                        return;
                    }

                    if (ReturnTimeCombox.getValue() == null) {
                        popUpError("The return time is empty!");
                        return;
                    }

                    if (OdometerTextField.getText().isEmpty()) {
                        popUpError("The odometer is empty!");
                        return;
                    }

                    if (TankFullCombox.getValue() == null) {
                        popUpError("The tank_full column is empty!");
                        return;
                    }

                    //use if to show the names of equipment1 and equipment2
                    if (Equip1Combox.getValue() == null) {
                        popUpError("The equipment 1 column is empty!");
                        return;
                    }

                    if (Equip2Combox.getValue() == null) {
                        popUpError("The equipment 2 column is empty!");
                        return;
                    }

                    //if the input are valid the summary of the rent will be shown
                    //also,the checkboxes will be shown
                    RoadStar.setDisable(false);
                    Redeem1000P.setDisable(false);
                    Redeem1500P.setDisable(false);

                    showSummary();
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

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

    private void setUpReturnTimeCombox() {
        //where to find the 
        configureHourCMB(ReturnTimeCombox);
        ReturnTimeCombox.getSelectionModel().select(0);
    }

    private void setUpTankFullCombox() {
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Yes");
        choices.add("No");
        configureComboBox(TankFullCombox, choices);

    }

    private void setUpEquipmentCombox() {
        ArrayList<String> quantityList = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            quantityList.add(Integer.toString(i));
        }
        configureComboBox(Equip1Combox, quantityList);
        configureComboBox(Equip2Combox, quantityList);
    }

    //these three methods are used to set up the checkboxes
    private void setupRoadStarCB() {
        RoadStar.selectedProperty().addListener((ov, oldv, newv) -> {
            if (newv) {
                String username = UsernameLabel.getText().trim();
                try {
                    if (!userModel.isRoadStar(username)) {
                        popUpError(username + " is not a road star!");
                        RoadStar.setSelected(false);
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
    }

    private void setupRedeem1000CB() {
        Redeem1000P.selectedProperty().addListener((ov, oldv, newv) -> {
            if (newv) {
                String username = UsernameLabel.getText().trim();
                if (!userModel.isMembership(username)) {
                    popUpError(username + " is not a Club member!");
                    Redeem1000P.setSelected(false);
                    return;
                }
                if (userModel.getPoints(username) < 1000) {
                    popUpError(username + " doesn't have enough points!");
                    Redeem1000P.setSelected(false);
                    return;
                }

            }

        });
    }

    private void setupRedeem1500CB() {
        Redeem1500P.selectedProperty().addListener((ov, oldv, newv) -> {
            if (newv) {
                String username = UsernameLabel.getText().trim();
                if (!userModel.isMembership(username)) {
                    popUpError(username + " is not a Club member!");
                    Redeem1500P.setSelected(false);
                    return;
                }
                if (userModel.getPoints(username) < 1000) {
                    popUpError(username + " doesn't have enough points!");
                    Redeem1500P.setSelected(false);
                    return;
                }
            }

        });
    }

}
