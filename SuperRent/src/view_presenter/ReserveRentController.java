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
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.AppContext;
import model.ClerkModel;
import model.CustomerModel;
import model.UserModel;
import model.VehicleSelection;
import util.PngConverter;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class ReserveRentController extends AbstractController implements Initializable {

    @FXML
    private ComboBox<String> locationCMB;

    private String city, location;

    @FXML
    private RadioButton carRB;

    final private ToggleGroup group = new ToggleGroup();

    private String vehicleCategory;

    @FXML
    private RadioButton truckRB;

    @FXML
    private ComboBox<String> vehicleTypeCMB;

    private String vehicleType;

    @FXML
    private Label vehicleTypeLabel;

    @FXML
    private DatePicker fromDatePicker;

    private String fromDateString;

    private LocalDate fromDate;

    @FXML
    private ComboBox<String> fromHourCMB;

    private int fromHour;

    @FXML
    private DatePicker toDatePicker;

    private String toDateString;

    private LocalDate toDate;

    @FXML
    private ComboBox<String> toHourCMB;

    private int toHour;

    @FXML
    private Button checkAvailabilityButton;

    @FXML
    private Label equip1Label;

    @FXML
    private ComboBox<String> equip1CMB;

    @FXML
    private Label equip2Label;

    private int cost;

    private int reservationConfirmNo;

    @FXML
    private ComboBox<String> equip2CMB;

    private ArrayList<String> equipments = new ArrayList<>();
    private ArrayList<Integer> EquipmentQuantities = new ArrayList<>();

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField CNoField; //Confirmation No field

    private String CNo;

    @FXML
    private Label foundByPhoneResult;

    @FXML
    private Label foundByCNoResult;

    @FXML
    private Label branchLabel;

    @FXML
    private Label fromTimeLabel;

    @FXML
    private Label toTimeLabel;

    @FXML
    private Button byPhoneSubmitButton;

    @FXML
    private Button byCNoSubmitButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button printButton;

    @FXML
    private VBox summaryVBox;

    private GridPane summaryGP;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label plateNoLabel;

    @FXML
    private CheckBox roadStarCB;

    @FXML
    private CheckBox redeem1000CB;

    @FXML
    private CheckBox redeem1500CB;

    @FXML
    private Button reserveButton;

    // the following fxml component should be hidden from customer
    @FXML
    private Button rentButton;

    @FXML
    private Button selectButton;

    @FXML
    private AnchorPane customerInfoAP;

    private String username;

    @FXML
    private Label showLicenseLabel;

    @FXML
    private Label licenseLabel;

    private UserModel userModel;

    private VehicleSelection selectedVehicle = new VehicleSelection();
    private String amount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        switch (AppContext.getInstance().getUserType()) {
            case "CUSTOMER":
                userModel = new CustomerModel();
                break;
            case "CLERK":
                userModel = new ClerkModel();
                break;
        }

        setUpCarTruckRB();
        setUpLocationCMB();
        setUpVehicleTypeCMB();
        setUpToHourCMBs();
        setUpEquipLabelField();
        setupPhoneField();
        setupCNoField();
        usernameLabel.setText("");
        vehicleTypeLabel.setText("");
        plateNoLabel.setText("");
        phoneLabel.setText("");
        licenseLabel.setText("");
        foundByPhoneResult.setText("");
        foundByCNoResult.setText("");
        branchLabel.setText("");
        fromTimeLabel.setText("");
        toTimeLabel.setText("");

        disableNodes(roadStarCB, redeem1000CB, redeem1500CB, selectButton);
        setupUsernameLabel();
        setupPlateNoLabel();
        setupRoadStarCB();
        setupRedeem1000CB();
        setupRedeem1500CB();
        setupRentButton();

        // do things differently for customer and employee
        String userType = AppContext.getInstance().getUserType();
        if (userType.equals("CUSTOMER")) {
            hide(rentButton, customerInfoAP,
                    registerButton, selectButton, showLicenseLabel);
            usernameLabel.setText(AppContext.getInstance().getUsername());
            phoneLabel.setText(userModel.getPhone(usernameLabel.getText()));
            show(reserveButton);
        } else {

        }
    }

    private void setUpLocationCMB() {
        configureComboBox(locationCMB, userModel.getAllBranches());
        locationCMB.setOnAction((ActionEvent event) -> {
            String branch = locationCMB.getSelectionModel().getSelectedItem();
            location = branch.split(",")[0].trim();
            city = branch.split(",")[1].trim();
            //enable vehicleTypeCMB
            vehicleTypeCMB.setDisable(false);
            //configure the vehicleType Combobox
            configureComboBox(vehicleTypeCMB,
                    userModel.getVehicleTypeAtBranch(city, location, vehicleCategory));
        });
    }

    private void setUpCarTruckRB() {
        carRB.setToggleGroup(group);
        truckRB.setToggleGroup(group);
        carRB.setSelected(true);
        vehicleCategory = "car";

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
//                    vehicleCategory = group.getSelectedToggle().getUserData().toString();
                    RadioButton rb = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                    vehicleCategory = rb.getText();
                    System.out.println(vehicleCategory);
                    vehicleCategory = vehicleCategory.toLowerCase();
                    //configure equipment labels and combobox
                    ArrayList<String> equipments = userModel.getEquipments(vehicleCategory);
                    equip1Label.setText(equipments.get(0));
                    equip2Label.setText(equipments.get(1));
                    equip1CMB.getSelectionModel().select(0);
                    equip2CMB.getSelectionModel().select(0);

                    //if location has been selected, reconfigure vehicleType combobox
                    if (!locationCMB.getSelectionModel().isEmpty()) {
                        String branch = locationCMB.getSelectionModel().getSelectedItem();
                        location = branch.split(",")[0].trim();
                        city = branch.split(",")[1].trim();
                        //enable vehicleTypeCMB
                        vehicleTypeCMB.setDisable(false);
                        //configure the vehicleType Combobox
                        configureComboBox(vehicleTypeCMB,
                                userModel.getVehicleTypeAtBranch(city, location, vehicleCategory));

                    }

                }
            }
        });

    }

    private void setUpVehicleTypeCMB() {
        //disable it initially
        disableNodes(vehicleTypeCMB);
    }

    private void setUpToHourCMBs() {
        configureHourCMB(fromHourCMB);
        fromHourCMB.getSelectionModel().select(0);
        configureHourCMB(toHourCMB);
        toHourCMB.getSelectionModel().select(0);
    }

    public void handleCheckAvailability() {

        if (locationCMB.getSelectionModel().isEmpty()) {
            popUpError("location is empty!");
            return;
        }
        String branch = locationCMB.getSelectionModel().getSelectedItem();
        branchLabel.setText(branch);
        city = branch.split(",")[1].trim();
        location = branch.split(",")[0].trim();
        if (vehicleTypeCMB.getSelectionModel().isEmpty()) {
            popUpError("vehicle type is empty!");
            return;
        }
        String vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();
        vehicleTypeLabel.setText(vehicleType);

        LocalDate fromDate = fromDatePicker.getValue();
        if (fromDate == null) {
            popUpError("From date is empty!");
            return;
        }
        fromDateString = fromDate.toString();

        if (fromHourCMB.getSelectionModel().isEmpty()) {
            popUpError("From hour is empty!");
            return;
        }

        String fromHourString = fromHourCMB.getSelectionModel().getSelectedItem();
        int fromHour = Integer.parseInt(fromHourString.split(":")[0]);
        System.out.println("from hour = " + fromHour);

        LocalDate toDate = toDatePicker.getValue();
        if (toDate == null) {
            popUpError("To date is empty!");
            return;
        }
        toDateString = toDate.toString();

        if (toHourCMB.getSelectionModel().isEmpty()) {
            popUpError("To hour is empty!");
            return;
        }
        String toHourString = toHourCMB.getSelectionModel().getSelectedItem();
        int toHour = Integer.parseInt(toHourString.split(":")[0]);
        System.out.println("to hour = " + toHour);

        //check the from time is earlier than to time
        if (fromDate.compareTo(toDate) > 0
                || (fromDate.compareTo(toDate) == 0 && fromHour >= toHour)) {
            popUpError("The 'From time' is not earlier than the 'To time'!");
            return;
        }

        fromTimeLabel.setText(fromDate.toString() + ", " + fromHourString);
        toTimeLabel.setText(toDate.toString() + ", " + toHourString);

        if (userModel.isVehicleTypeAvailable(city, location, vehicleType, fromDateString, toDateString)) {
            getDataFromSearchInput();
            phoneField.setText("");
            CNoField.setText("");
            showSummary();
        } else {
            popUpError("No vehicle of type: " + vehicleType + " is available from "
                    + fromDateString + " to " + toDateString + "!");
            return;
        }

    }

    private void setupPhoneField() {
        phoneField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameLabel.setText("");
                phoneLabel.setText("");
                foundByPhoneResult.setText("");
//                unSelect(roadStarCB, redeem1000CB, redeem1500CB);
//                clearLabels(foundByCNoResult, usernameLabel, phoneLabel, vehicleTypeLabel, plateNoLabel,
//                        fromTimeLabel, toTimeLabel, branchLabel);
//                clearSummary();
            }
        });
    }

    private void setupCNoField() {
        CNoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                //enable the reserve button
                enableNodes(reserveButton);
                unSelect(roadStarCB, redeem1000CB, redeem1500CB);
                clearLabels(foundByCNoResult, usernameLabel, phoneLabel, vehicleTypeLabel, plateNoLabel,
                        fromTimeLabel, toTimeLabel, branchLabel, licenseLabel);
                clearSummary();
            }
        });
    }

    private void clearSummary() {
        if (summaryGP != null) {
            summaryVBox.getChildren().remove(summaryGP);
        }
    }

    private void setupUsernameLabel() {
        usernameLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                System.out.println("Label Text Changed");
                if (t1 != null && t1.trim() != "") { //if there is a username
                    //clear and enable the checkbox
                    roadStarCB.setDisable(false);
                    roadStarCB.setSelected(false);
                    redeem1000CB.setDisable(false);
                    redeem1000CB.setSelected(false);
                    redeem1500CB.setDisable(false);
                    redeem1500CB.setSelected(false);
                    selectButton.setDisable(false);
                } else { // if there isn't a username
                    roadStarCB.setSelected(false);
                    roadStarCB.setDisable(true);
                    redeem1000CB.setSelected(false);
                    redeem1000CB.setDisable(true);
                    redeem1500CB.setSelected(false);
                    redeem1500CB.setDisable(true);
                    selectButton.setDisable(true);
                }
            }
        });

    }

    private void setupPlateNoLabel() {
        //allow the rent to proceed only if plate no is not empty
        plateNoLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                System.out.println("Plate No. Changed");
                if (t1 != null && !"".equals(t1.trim())) { //if there is a username
                    //enable rent
                    rentButton.setDisable(false);
                } else { // if there isn't a username
                    rentButton.setDisable(true);
                }
            }
        });
    }

    private void setupRoadStarCB() {
        roadStarCB.selectedProperty().addListener((ov, oldv, newv) -> {

            showSummary();
        });
    }

    private void setupRedeem1000CB() {
        redeem1000CB.selectedProperty().addListener((ov, oldv, newv) -> {
            if (newv) {
                String username = usernameLabel.getText().trim();
                if (!userModel.isMembership(username)) {
                    popUpError(username + " is not a Club member!");
                    redeem1000CB.setSelected(false);
                    return;
                }
                if (userModel.getPoints(username) < 1000) {
                    popUpError(username + " doesn't have enough points!");
                    redeem1000CB.setSelected(false);
                    return;
                }
                if (daysBetween(fromDate, toDate) < 1) {
                    popUpError("Time duration must be larger than 1 day to redeem!");
                    redeem1000CB.setSelected(false);
                    return;
                }
                String vt = vehicleTypeLabel.getText(); //selectedVehicle.getVehicleType();
                if (!userModel.isLowRankVehicle(vt)) {
                    popUpError("You can't redeem 1000 points with type: " + vt);
                    redeem1000CB.setSelected(false);
                    return;
                }
            }
            showSummary();
        });
    }

    private void setupRedeem1500CB() {
        redeem1500CB.selectedProperty().addListener((ov, oldv, newv) -> {
            if (newv) {
                String username = usernameLabel.getText().trim();
                if (!userModel.isMembership(username)) {
                    popUpError(username + " is not a Club member!");
                    redeem1500CB.setSelected(false);
                    return;
                }
                if (userModel.getPoints(username) < 1000) {
                    popUpError(username + " doesn't have enough points!");
                    redeem1500CB.setSelected(false);
                    return;
                }
                if (daysBetween(fromDate, toDate) < 1) {
                    popUpError("Time duration must be larger than 1 day to redeem!");
                    redeem1500CB.setSelected(false);
                    return;
                }
                String vt = vehicleTypeLabel.getText(); //selectedVehicle.getVehicleType();
                if (!userModel.isHighRankVehicle(vt)) {
                    popUpError("You can't redeem 1500 points with type: " + vt);
                    redeem1500CB.setSelected(false);
                    return;
                }
            }
            showSummary();
        });
    }

    public void handleSubmitByPhone() {
        if (!isInputPhoneNo(phoneField)) {
            showWarning(foundByPhoneResult, "Not found");
            return;
        }
        String phone = phoneField.getText().trim();
        //phone = formatPhoneNo(phone);
        String username = userModel.getCustomerByPhone(phone);

        if (username == null) {
            showWarning(foundByPhoneResult, "Not found");
            return;
        } else {
            showSuccessMessage(foundByPhoneResult, "Found");
            System.out.println("found");
            usernameLabel.setText(username);
            AppContext.getInstance().setTempData("customer_username", username);
            System.out.println(username);
            phoneLabel.setText(phone);
            showReservation(phone);
            String CNo = AppContext.getInstance().getTempData("confirmNo");
            if (CNo != null && !CNo.equals("None")) {
                CNoField.setText(CNo);
                handleSubmitByCNo();
            }
        }

    }

    public void handleSubmitByCNo() {
        if (isInputEmpty(CNoField)) {
            showWarning(foundByCNoResult, "Not found");
            return;
        }

        getDataFromCNo();
        if (foundByCNoResult.getText().trim().equals("Found")) {
            showSummary();
        }
        roadStarCB.setSelected(false);
        redeem1000CB.setSelected(false);
        redeem1500CB.setSelected(false);

    }

    private void prepareData() {
        if (!foundByCNoResult.getText().equals("")) {
            getDataFromCNo();
        } else {
            getDataFromSearchInput();
        }
    }

    private void showSearchResult() {
        prepareData();
        passDataToNext();
        setupNextPage(this, "ShowSearchResultView.fxml", "Search results");

    }

    public void setupRentButton() {
        //disable the button at the beginning
        rentButton.setDisable(true);
    }

    public void handleSelectButton() {
        showSearchResult();
        if (AppContext.getInstance().getTempData("vehicleSelected").equals("true")) {
            //update plate no
            plateNoLabel.setText(selectedVehicle.getVlicense());
            if (AppContext.getInstance().getTempData("driver-license") != null) {
                licenseLabel.setText(AppContext.getInstance().getTempData("driver-license"));
            }
            //AppContext.getInstance().emptyTempData();
            //enable rent button
            rentButton.setDisable(false);
        } else {
            //if no vehicle is selected, clear the plate no label
            plateNoLabel.setText("");
            disableNodes(rentButton);
        }
    }

    private void setUpEquipLabelField() {
        String category = "car";
        ArrayList<String> equipments = userModel.getEquipments(category);
        equip1Label.setText(equipments.get(0));
        equip2Label.setText(equipments.get(1));

        ArrayList<String> quantityList = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            quantityList.add(Integer.toString(i));
        }
        configureComboBox(equip1CMB, quantityList);
        configureComboBox(equip2CMB, quantityList);
        equip1CMB.getSelectionModel().select(0);
        equip2CMB.getSelectionModel().select(0);
        // if the quantity has been changed, redo the summary part
        equip1CMB.setOnAction((ActionEvent event) -> {
            if (!"".equals(vehicleTypeLabel.getText().trim())) {
                getDataFromSearchInput();
                showSummary();
            }
        });

        equip2CMB.setOnAction((ActionEvent event) -> {
            if (!"".equals(vehicleTypeLabel.getText().trim())) {
                getDataFromSearchInput();
                showSummary();
            }
        });
    }

    private void passDataToNext() {
//        String branch = locationCMB.getSelectionModel().getSelectedItem();
//        location = branch.split(",")[0].trim();
//        city = branch.split(",")[1].trim();
//        vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();
//        fromDateString = fromDatePicker.getValue().toString();
//        toDateString = toDatePicker.getValue().toString();

        AppContext.getInstance().setTempData("city", city);
        AppContext.getInstance().setTempData("location", location);
        AppContext.getInstance().setTempData("vehicleType", vehicleType);
        AppContext.getInstance().setTempData("fromDate", fromDateString);
        AppContext.getInstance().setTempData("toDate", toDateString);
    }

    //get the rental information from the search input
    private void getDataFromSearchInput() {
        fromDate = fromDatePicker.getValue();
        toDate = toDatePicker.getValue();
        fromHour = Integer.parseInt(fromHourCMB.getSelectionModel().getSelectedItem().split(":")[0]);
        toHour = Integer.parseInt(toHourCMB.getSelectionModel().getSelectedItem().split(":")[0]);
        vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();

        //get the equipments
        equipments.clear();
        EquipmentQuantities.clear();
        String equip1 = equip1Label.getText();
        int equp1Quantity = Integer.parseInt(equip1CMB.getSelectionModel().getSelectedItem());
        if (equp1Quantity > 0) {
            equipments.add(equip1);
            EquipmentQuantities.add(equp1Quantity);
        }

        String equip2 = equip2Label.getText();
        int equp2Quantity = Integer.parseInt(equip2CMB.getSelectionModel().getSelectedItem());
        if (equp2Quantity > 0) {
            equipments.add(equip2);
            EquipmentQuantities.add(equp2Quantity);
        }
    }

    //get the rental information from the reservation confirmation no
    private void getDataFromCNo() {
        String CNo = CNoField.getText().trim();

        HashMap<String, String> reservationDetails;

        reservationDetails = userModel.getReservationDetails(CNo);

        HashMap<String, String> reservedEquipments;
        reservedEquipments = userModel.getEquipmentsByCNo(CNo);

        if (reservationDetails.isEmpty()) {
            showWarning(foundByCNoResult, "Not found");
            return;
        } else {
            //disable the reserve button
            disableNodes(reserveButton);
            showSuccessMessage(foundByCNoResult, "Found");
            System.out.println("found");
            usernameLabel.setText(reservationDetails.get("customer_username"));
            phoneLabel.setText(userModel.getPhone(usernameLabel.getText()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            fromDate = LocalDate.parse(reservationDetails.get("pickup_date"), formatter);
            toDate = LocalDate.parse(reservationDetails.get("return_date"), formatter);

            fromHour = Integer.parseInt(reservationDetails.get("pickup_time"));
            toHour = Integer.parseInt(reservationDetails.get("return_time"));
            String fromHourString = fromHour + ":00";
            String toHourString = toHour + ":00";
            fromTimeLabel.setText(fromDate.toString() + ", " + fromHourString);
            toTimeLabel.setText(toDate.toString() + ", " + toHourString);
            city = reservationDetails.get("branch_city");
            vehicleType = reservationDetails.get("vehicleType");
            vehicleTypeLabel.setText(vehicleType);
            location = reservationDetails.get("branch_location");
            branchLabel.setText(location + ", " + city);
            usernameLabel.setText(reservationDetails.get("customer_username"));
            // fill in the reserved equipments information
            equipments.clear();
            EquipmentQuantities.clear();
            //iterate over the hashmap
            for (Map.Entry<String, String> entry : reservedEquipments.entrySet()) {
                String equipName = entry.getKey();
                String quantity = entry.getValue();
                equipments.add(equipName);
                EquipmentQuantities.add(Integer.parseInt(quantity));
            }

        }
    }

    private void showSummary() {
        System.out.println("show summary");
        int redeemedPoints = 0;
        int odometer = 0;

        boolean isRoadStar = false;
        if (!roadStarCB.isDisabled() && roadStarCB.isSelected()) {
            isRoadStar = true;
        }

        if (!redeem1000CB.isDisabled() && redeem1000CB.isSelected()) {
            redeemedPoints = 1000;
        }

        if (!redeem1500CB.isDisabled() && redeem1500CB.isSelected()) {
            redeemedPoints = 1500;
        }

        if (summaryGP != null) {
            summaryVBox.getChildren().remove(summaryGP);
        }
        boolean isForReturn = false;
        summaryGP = userModel.calculateCost(vehicleType, equipments, EquipmentQuantities,
                fromDate, fromHour, toDate, toHour, isRoadStar,
                redeemedPoints, odometer, null, false,null,null);
        summaryVBox.getChildren().add(summaryGP);
        amount = AppContext.getInstance().getTempData("amount");
        System.out.println("show summary: amount " + amount);

    }

    @Override
    public void update(Object o) {
        //convert the object to something that this controller will recognize
        selectedVehicle = (VehicleSelection) o;
        System.out.println(selectedVehicle.getVlicense());
        System.out.println(selectedVehicle.getCategory());
        System.out.println(selectedVehicle.getVehicleType());
        System.out.println(selectedVehicle.getBrand());
        System.out.println(selectedVehicle.getOdometer());

        showSummary();
        carRB.requestFocus();
    }

    public void handleRegistration() {
        setupNextPage(this, "Register.fxml", "Register customer");
    }

    private void prepareDataForReserve() {
        fromDate = stringToLocalDate(fromTimeLabel.getText().split(",")[0]);
        String fromHourString = fromTimeLabel.getText().split(",")[1];
        fromHourString = fromHourString.trim().split(":")[0];
        fromHour = Integer.parseInt(fromHourString);

        toDate = stringToLocalDate(toTimeLabel.getText().split(",")[0]);
        String toHourString = toTimeLabel.getText().split(",")[1];
        toHourString = toHourString.trim().split(":")[0];
        toHour = Integer.parseInt(toHourString);
        
        city = branchLabel.getText().split(",")[1].trim();
        location = branchLabel.getText().split(",")[0].trim();
        
        username = usernameLabel.getText().trim();
        vehicleType = vehicleTypeLabel.getText().trim();
        
    }

    public void handleReserve() {
        prepareDataForReserve();
        cost = 0;
        int confirmNo = userModel.createReservation(fromDate, fromHour,
                toDate, toHour, city, location,
                username, "pending", vehicleType);
        if (confirmNo == -1) {
            popUpError("Failed to make reservation!");
        } else {
            userModel.createEquipReservation(confirmNo, equipments, EquipmentQuantities);
            popUpMessage("Reservation is successful! The confirmation number is "
                    + confirmNo + ". Please make a note of it!");
            unSelect(roadStarCB, redeem1000CB, redeem1500CB);
            disableNodes(roadStarCB, redeem1000CB, redeem1500CB);
            clearLabels(foundByCNoResult, usernameLabel, phoneLabel, vehicleTypeLabel, plateNoLabel,
                    fromTimeLabel, toTimeLabel, branchLabel, licenseLabel);
            clearSummary();
        }
    }

    public void handleRent() {
        prepareData();
        System.out.println("handleRent: amount = " + amount);
        //tell the payment view, the request is from the rent page
        AppContext.getInstance().setTempData("requestFrom", "rentPage");
        setupNextPage(this, "PaymentCCView.fxml", "Payment");
        //get data from the popup page
        String driver_license = licenseLabel.getText();
        String vlicense = plateNoLabel.getText().trim();
        String card_type = AppContext.getInstance().getTempData("card_type");
        String card_no = AppContext.getInstance().getTempData("card_no");
        String expiry_date = AppContext.getInstance().getTempData("expiry_date");
        

        String username = usernameLabel.getText().trim();
        int is_reserve = 0;
        if (!CNoField.getText().trim().equals("")) {
            is_reserve = 1;
            reservationConfirmNo = Integer.parseInt(CNoField.getText().trim());
        }
        if (AppContext.getInstance().getTempData("status").equals("failure")) {
            popUpError("The payment is failed!");
            return;            
        }
        int rent_id = userModel.createRent(is_reserve, driver_license,
                vlicense, city, location,
                username, card_type, card_no,
                expiry_date, fromDate, fromHour,
                toDate, toHour, reservationConfirmNo);
        if (rent_id == -1 ) {
            popUpError("Failed to make the rent!");
        } else {
            userModel.createEquipRent(rent_id, equipments, EquipmentQuantities);
            popUpMessage("Rent is successful! The rent id is "
                    + rent_id + ". Please make a note of it!");
            unSelect(roadStarCB, redeem1000CB, redeem1500CB);
            disableNodes(roadStarCB, redeem1000CB, redeem1500CB, rentButton);
            clearLabels(foundByCNoResult, usernameLabel, phoneLabel, vehicleTypeLabel, plateNoLabel,
                    fromTimeLabel, toTimeLabel, branchLabel, licenseLabel);
            clearSummary();
            enableNodes(reserveButton);

        }
    }

    public void handlePrint() {
        Stage primaryStage = AppContext.getInstance().getPrimaryStage();
        PngConverter pngConverter = new PngConverter(primaryStage);
        pngConverter.saveAsPng(summaryVBox);
    }

    private void showReservation(String phone) {
//        final Popup popup = new Popup();
//        popup.setX(400);
//        popup.setY(300);
//        TableView reservationTable = userModel.getReservationsByPhone(phone, city, location);
//        popup.getContent().addAll(reservationTable);
//        popup.show(AppContext.getInstance().getPrimaryStage());
        AppContext.getInstance().setTempData("phone", phone);
        setupNextPage(this, "ShowReservation.fxml", "Show Reservations");
    }
}
