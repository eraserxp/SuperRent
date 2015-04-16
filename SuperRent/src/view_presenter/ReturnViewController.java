/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//things to do
//penalty for lost equipments
//roadstar
//add 1000 point
//add 1500 point
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private Label ReturnedEquipment1Label;
    @FXML
    private Label ReturnedEquipment2Label;
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
    private Button ReturnButton;
//    @FXML
//    private Button CheckButton;

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
    ArrayList<String> equipmentslist = new ArrayList<>();
    ArrayList<String> equipments = new ArrayList<>();
    ArrayList<Integer> quantities = new ArrayList<>();
    LocalDate fromDate, toDate;

    LocalDate returnDate;
    Integer rentidint, returnTimeInt, odometer;

    String rentid, city, location, customerusername, TankFull, PlateNumString;
    //payment method and total cost may not be available here
    String Payment_Method = "Cash";
    String totalcost = "";
    Integer TankFullint = 0;
    Integer rentEquip1Num = -1, rentEquip2Num = -1, returnEquip1Num = -1, returnEquip2Num = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        hide(UsernameLabel, PlatenoLabel, PickupLocationLabel, PickupTimeLabel, ReturnLocationLabel, ReturnTimeLabel, Equipment1, Number1, Equipment2, Number2, ReturnedEquipment1Label, ReturnedEquipment2Label);
        Equip1Combox.setVisible(false);
        Equip2Combox.setVisible(false);
        RoadStar.setDisable(true);
        Redeem1000P.setDisable(true);
        Redeem1500P.setDisable(true);

        setUpReturnTimeCombox();
        setUpTankFullCombox();
        setUpEquipmentCombox();

        setupVehicleNumField();
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

        PlateNumString = PlateNoTextField.getText();
        Integer Numequipment1 = null, Numequipment2 = null;

        if (!Equip1Combox.getSelectionModel().isEmpty()) {
            Numequipment1 = Integer.parseInt(Equip1Combox.getSelectionModel().getSelectedItem().toString());
            returnEquip1Num = Numequipment1;
//            returnEquip2Num = null;
        } else if (!Equip2Combox.getSelectionModel().isEmpty()) {
            Numequipment2 = Integer.parseInt(Equip2Combox.getSelectionModel().getSelectedItem().toString());
            returnEquip2Num = Numequipment2;
        }
//        else {
//            returnEquip1Num = null;
//            returnEquip2Num = null;
//        }

//        LocalDate returnDate =ReturnDatePicker.getValue();
        returnDate = ReturnDatePicker.getValue();

        toDate = returnDate;
        String returnTimeString = ReturnTimeCombox.getSelectionModel().getSelectedItem().toString();
        returnTimeInt = Integer.parseInt(returnTimeString.split(":")[0]);
        //Integer returnTime = Integer.parseInt(returnTimeString.split(":")[0]);
        //String OdometerReading = OdometerTextField.getText();
        TankFull = TankFullCombox.getSelectionModel().getSelectedItem().toString();
        if (TankFull.equals("Yes")) {
            TankFullint = 1;

        } else {
            TankFullint = 0;
        }
        //String Equipment1Selection = Equip1Combox.getSelectionModel().getSelectedItem().toString();
        //String Equipment2Selection = Equip2Combox.getSelectionModel().getSelectedItem().toString();

        ArrayList<String> rentList = new ArrayList<>();
        rentList = clerkModel.getRentDetails(PlateNumString.trim());
        //System.out.println(rentList);
        customerusername = rentList.get(0);
        UsernameLabel.setText(customerusername);
        PlatenoLabel.setText(PlateNumString);
        String pickuplocation = rentList.get(2) + ", " + rentList.get(1);
        PickupLocationLabel.setText(pickuplocation);

        LocalDate pickupDate = LocalDate.parse(rentList.get(3).trim());

        String pickuptimestring = rentList.get(4);
        Integer pickuptimeint = Integer.parseInt(pickuptimestring.split(":")[0]);

        String pickupTime = " at " + rentList.get(4) + ":00" + " in " + rentList.get(3);
        PickupTimeLabel.setText(pickupTime);

        //Clerk name
        String username = AppContext.getInstance().getUsername();

        ArrayList<String> clerkbranch = new ArrayList<>();
        clerkbranch = clerkModel.getClerkDetails(username);
        city = clerkbranch.get(0);
        location = clerkbranch.get(1);
        String returnlocation = clerkbranch.get(1) + ", " + clerkbranch.get(0);
        ReturnLocationLabel.setText(returnlocation);

        String returnTime = " at " + returnTimeString + " in " + returnDate;
        ReturnTimeLabel.setText(returnTime);

        rentid = rentList.get(5);
        rentidint = Integer.parseInt(rentid);
        equipmentslist = new ArrayList<>();
        equipmentslist = clerkModel.getEquipmentDetails(rentid);

        equipments = new ArrayList<>();

        quantities = new ArrayList<>();

        show(UsernameLabel, PlatenoLabel, PickupLocationLabel, PickupTimeLabel, ReturnLocationLabel, ReturnTimeLabel);

        if (equipmentslist.size() == 2) {
            rentEquip1Num = Integer.parseInt(equipmentslist.get(1));
            Equipment1.setText(equipmentslist.get(0));
            //ReturnedEquipment1Label.setText(equipmentslist.get(0));
            Number1.setText("-" + equipmentslist.get(1));
            equipments.add(equipmentslist.get(0));
            quantities.add(Integer.parseInt(equipmentslist.get(1).trim()));
            show(Equipment1, Number1);
        } else if (equipmentslist.size() == 4) {
            rentEquip1Num = Integer.parseInt(equipmentslist.get(1));
            rentEquip2Num = Integer.parseInt(equipmentslist.get(3));
            Equipment1.setText(equipmentslist.get(0));
            Number1.setText("-" + equipmentslist.get(1));
            //ReturnedEquipment1Label.setText(equipmentslist.get(0));
            Equipment2.setText(equipmentslist.get(2));
            //ReturnedEquipment1Label.setText(equipmentslist.get(0));
            Number2.setText("-" + equipmentslist.get(3));
            equipments.add(equipmentslist.get(0));
            equipments.add(equipmentslist.get(2));
            quantities.add(Integer.parseInt(equipmentslist.get(1).trim()));
            quantities.add(Integer.parseInt(equipmentslist.get(3).trim()));
            show(Equipment1, Number1, Equipment2, Number2);
        } else {
            //System.out.println("No equipments");
            Equipment1.setText("No equipments rented");
            hide(Number1);
            show(Equipment1);
            equipments = null;
            quantities = null;
        }

        int redeemedPoints = 0;
        boolean isRoadStar = false;
        odometer = Integer.parseInt(OdometerTextField.getText());
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

//        passDataToNext();
        String vehicleType = clerkModel.getVehicleType(PlateNumString);

        summaryGP = userModel.calculateCost(vehicleType, equipments, quantities,
                pickupDate, pickuptimeint, returnDate, returnTimeInt, isRoadStar,
                redeemedPoints, odometer, PlateNumString, true, Numequipment1, Numequipment2);

        //get the total cost from the appcontext
//        totalcost = AppContext.getInstance().getTempData("amount");
//        Integer temp = Integer.parseInt(totalcost);
//        temp = temp/100;
//        totalcost = temp.toString();
        totalcost = AppContext.getInstance().getTempData("amount");
        //System.out.println(totalcost);
        if (summaryGP != null) {
            summaryVBox.getChildren().add(summaryGP);
            System.out.println("Sucessful query of cost summary");
        } else {
            System.out.println("Unsucessful query of cost summary");
        }

    }

    public void handleConfirmButton() {
        ConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {

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
                    if (equipmentslist.size() == 4) {
                        if (Equip1Combox.getValue() == null) {
                            popUpError("The equipment 1 column is empty!");
                            return;
                        }

                        if (Equip2Combox.getValue() == null) {
                            popUpError("The equipment 2 column is empty!");
                            return;
                        }
                    } else if (equipmentslist.size() == 2) {
                        if (Equip1Combox.getValue() == null) {
                            popUpError("The equipment 1 column is empty!");
                            return;
                        }
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
        ReturnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Boolean checkV = false;
                try {
                    //summaryGP.getChildren().clear();
                    showPaymentDialog();

                    String status = AppContext.getInstance().getTempData("status");
                    Payment_Method = AppContext.getInstance().getTempData("Paymentmethod");
                    if (status.equals("success")) {

                        checkV = clerkModel.createVreturn(rentidint, returnDate, returnTimeInt, city, location, TankFullint, odometer, totalcost, Payment_Method);
                        if (checkV == true) {
                            popUpMessage("The vehicle has been sucessfully returned!");
                        } else {
                            popUpError("The vehicle has not been rented yet");
                        }
                        //do te updates
                        //update the quantity of the equipment
                        if (returnEquip1Num != -1 && rentEquip1Num != -1 && returnEquip1Num <= rentEquip1Num) {
                            //do sth
                            clerkModel.updateEquipNum(equipmentslist.get(0), city, location, returnEquip1Num);
                        } else if (returnEquip2Num != -1 && rentEquip2Num != -1 && returnEquip2Num <= rentEquip2Num) {
                            //do sth
                            clerkModel.updateEquipNum(equipmentslist.get(0), city, location, returnEquip1Num);
                            clerkModel.updateEquipNum(equipmentslist.get(2), city, location, returnEquip2Num);
                        }
                        //update the odometer
                        //System.out.println(odometer.toString());
                        //System.out.println(PlateNumString);
                        clerkModel.updateOdometer(odometer.toString(), PlateNumString);

                        //update the points
                        clerkModel.updatePoint(Integer.parseInt(totalcost), customerusername);

                        //update the tankfull
                        //we dont have a table with the tankfull status
                    } else {
                        popUpMessage("Payment is not sucessful!");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public void showPaymentDialog() throws IOException {
        //tell the payment page, the request is from "return page"
        AppContext.getInstance().setTempData("requestFrom", "returnPage");

        AnchorPane loader = (AnchorPane) FXMLLoader.load(ReturnDialogViewController.class.getResource("PaymentCCView.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Payment");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader);
        dialogStage.setScene(scene);

        //do sth when the dialog is closed
//        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                System.out.println("Do sth when user click the X close icon of the dialog");
//
//            }
//        });
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
                if (!userModel.isRoadStar(username)) {
                    popUpError(username + " is not a road star!");
                    RoadStar.setSelected(false);
                } else {
                    try {
                        showSummary();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                try {

                    showSummary();
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

                if (daysBetween(fromDate, toDate) < 1) {
                    popUpError("Time duration must be larger than 1 day to redeem!");
                    Redeem1000P.setSelected(false);
                    return;
                }

                PlateNumString = PlateNoTextField.getText();
                String vehicleType;
                try {
                    vehicleType = clerkModel.getVehicleType(PlateNumString);
                    if (!userModel.isLowRankVehicle(vehicleType)) {
                        popUpError("You can't redeem 1000 points with type: " + vehicleType);
                        Redeem1000P.setSelected(false);
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    showSummary();
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {

                    showSummary();
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
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

                if (daysBetween(fromDate, toDate) < 1) {
                    popUpError("Time duration must be larger than 1 day to redeem!");
                    Redeem1500P.setSelected(false);
                    return;
                }

                PlateNumString  = PlateNoTextField.getText();
                String vehicleType;
                try {
                    vehicleType = clerkModel.getVehicleType(PlateNumString);
                    if (!userModel.isHighRankVehicle(vehicleType)) {
                        popUpError("You can't redeem 1500 points with type: " + vehicleType);
                        Redeem1500P.setSelected(false);
                        return;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    showSummary();
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {

                    showSummary();
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private void setupVehicleNumField() {
        PlateNoTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                Equip1Combox.getSelectionModel().clearSelection();
                Equip2Combox.getSelectionModel().clearSelection();

                PlateNumString = PlateNoTextField.getText();

                ArrayList<String> rentList = new ArrayList<>();
                try {
                    rentList = clerkModel.getRentDetails(PlateNumString.trim());
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (rentList != null) {
                    String rentid = rentList.get(5);
                    fromDate = LocalDate.parse(rentList.get(8).trim());

                    equipmentslist = new ArrayList<>();
                    try {
                        equipmentslist = clerkModel.getEquipmentDetails(rentid);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    equipments = new ArrayList<>();
                    quantities = new ArrayList<>();

                    if (equipmentslist.size() == 2) {
                        ReturnedEquipment1Label.setText(equipmentslist.get(0));
                        equipments.add(equipmentslist.get(0));
                        quantities.add(Integer.parseInt(equipmentslist.get(1).trim()));
                        Equip1Combox.setVisible(true);
                        show(ReturnedEquipment1Label);
                        hide(ReturnedEquipment2Label);
//                        Equip2Combox.getSelectionModel().clearSelection();
                        Equip2Combox.setVisible(false);

                    } else if (equipmentslist.size() == 4) {
                        ReturnedEquipment1Label.setText(equipmentslist.get(0));
                        ReturnedEquipment2Label.setText(equipmentslist.get(2));
                        Equip1Combox.setVisible(true);
                        Equip2Combox.setVisible(true);
                        equipments.add(equipmentslist.get(0));
                        equipments.add(equipmentslist.get(2));
                        quantities.add(Integer.parseInt(equipmentslist.get(1).trim()));
                        quantities.add(Integer.parseInt(equipmentslist.get(3).trim()));
                        rentEquip2Num = -1;
                        show(ReturnedEquipment1Label, ReturnedEquipment2Label);
                    } else {
                        System.out.println("No equipments");
                        ReturnedEquipment1Label.setText("No equipment!");
                        show(ReturnedEquipment1Label);
                        Equip1Combox.setVisible(false);
                        Equip2Combox.setVisible(false);
                        Equip1Combox.getSelectionModel().clearSelection();
                        Equip2Combox.getSelectionModel().clearSelection();
                        equipments = null;
                        quantities = null;
                        rentEquip1Num = -1;
                        rentEquip2Num = -1;
                    }

                } else {
                    popUpError("Please input a valid vehicle plate number!");
                    clearText(PlateNoTextField);
                    Equip1Combox.setVisible(false);
                    Equip2Combox.setVisible(false);
                    hide(ReturnedEquipment1Label, ReturnedEquipment2Label);
                }

            }
        });
    }

//    public void handleEquipmentAction() {
//        
//
//        CheckButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//
//                String PlateNumString = PlateNoTextField.getText();
//
//                ArrayList<String> rentList = new ArrayList<>();
//                try {
//                    rentList = clerkModel.getRentDetails(PlateNumString.trim());
//                } catch (SQLException ex) {
//                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                if (rentList != null) {
//                    String rentid = rentList.get(5);
//                    fromDate = LocalDate.parse(rentList.get(8).trim());
//
//                    equipmentslist = new ArrayList<>();
//                    try {
//                        equipmentslist = clerkModel.getEquipmentDetails(rentid);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                    equipments = new ArrayList<>();
//                    quantities = new ArrayList<>();
//
//                    if (equipmentslist.size() == 2) {
//                        ReturnedEquipment1Label.setText(equipmentslist.get(0));
//                        equipments.add(equipmentslist.get(0));
//                        quantities.add(Integer.parseInt(equipmentslist.get(1).trim()));
//                        Equip1Combox.setVisible(true);
//                        show(ReturnedEquipment1Label);
//                        hide(ReturnedEquipment2Label);
//                        Equip2Combox.setVisible(false);
//                    } else if (equipmentslist.size() == 4) {
//                        ReturnedEquipment1Label.setText(equipmentslist.get(0));
//                        ReturnedEquipment2Label.setText(equipmentslist.get(2));
//                        Equip1Combox.setVisible(true);
//                        Equip2Combox.setVisible(true);
//                        equipments.add(equipmentslist.get(0));
//                        equipments.add(equipmentslist.get(2));
//                        quantities.add(Integer.parseInt(equipmentslist.get(1).trim()));
//                        quantities.add(Integer.parseInt(equipmentslist.get(3).trim()));
//                        show(ReturnedEquipment1Label, ReturnedEquipment2Label);
//                    } else {
//                        System.out.println("No equipments");
//                        ReturnedEquipment1Label.setText("No equipment!");
//                        show(ReturnedEquipment1Label);
//                        Equip1Combox.setVisible(false);
//                        Equip2Combox.setVisible(false);
//                        equipments = null;
//                        quantities = null;
//                    }
//
//                } else {
//                    popUpError("Please input a valid vehicle plate number!");
//                    Equip1Combox.setVisible(false);
//                    Equip2Combox.setVisible(false);
//                    hide(ReturnedEquipment1Label, ReturnedEquipment2Label);
//                }
//
//            }
//
//        });
//
//    }
//
//    private void passDataToNext() {
//        String equipment1 = null, equipment2 = null;
//        if (Equip1Combox.getValue() != null) {
//            equipment1 = Equip1Combox.getValue().toString();
//        }
//        if (Equip2Combox.getValue() != null) {
//            equipment2 = Equip2Combox.getValue().toString();
//        }
//        AppContext.getInstance().setTempData("equipment1", equipment1);
//        AppContext.getInstance().setTempData("equipment2", equipment2);
//
//    }
}
