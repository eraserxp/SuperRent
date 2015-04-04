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
    private Button searchButton;

    @FXML
    private Label equip1Label;

    @FXML
    private ComboBox<String> equip1CMB;

    @FXML
    private Label equip2Label;

    @FXML
    private ComboBox<String> equip2CMB;

    @FXML
    private TextField phoneField;

    @FXML
    private Label foundResult;

    @FXML
    private Label fromTimeLabel;

    @FXML
    private Label toTimeLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button registerButton;

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
    private Label confirmCustomerLabel;

    @FXML
    private HBox byPhoneHBox;

    @FXML
    private HBox submitHBox;

    private UserModel userModel;

    private VehicleSelection selectedVehicle = new VehicleSelection();

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
        usernameLabel.setText("");
        plateNoLabel.setText("");
        foundResult.setText("");
        fromTimeLabel.setText("");
        toTimeLabel.setText("");

        disableNodes(roadStarCB, redeem1000CB, redeem1500CB);
        setupUsernameLabel();
        setupRoadStarCB();
        setupRedeem1000CB();
        setupRedeem1500CB();

        // do things differently for customer and employee
        String userType = AppContext.getInstance().getUserType();
        if (userType.equals("CUSTOMER")) {
            hide(rentButton, confirmCustomerLabel, byPhoneHBox, submitHBox, registerButton);
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

    public void handleSearchButton() {

        if (locationCMB.getSelectionModel().isEmpty()) {
            popUpError("location is empty!");
            return;
        }
        String location = locationCMB.getSelectionModel().getSelectedItem();

        if (vehicleTypeCMB.getSelectionModel().isEmpty()) {
            popUpError("vehicle type is empty!");
            return;
        }
        String vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();

        LocalDate fromDate = fromDatePicker.getValue();
        if (fromDate == null) {
            popUpError("From date is empty!");
            return;
        }

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
        showSearchResult();

    }

    private void setupPhoneField() {
        phoneField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameLabel.setText("");
                foundResult.setText("");
            }
        });
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
                } else { // if there isn't a username
                    roadStarCB.setSelected(false);
                    roadStarCB.setDisable(true);
                    redeem1000CB.setSelected(false);
                    redeem1000CB.setDisable(true);
                    redeem1500CB.setSelected(false);
                    redeem1500CB.setDisable(true);
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
                String vt = selectedVehicle.getVehicleType();
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
                String vt = selectedVehicle.getVehicleType();
                if (!userModel.isHighRankVehicle(vt)) {
                    popUpError("You can't redeem 1500 points with type: " + vt);
                    redeem1500CB.setSelected(false);
                    return;
                }
            }
            showSummary();
        });
    }

    public void checkPhoneField() {
        if (!isInputPhoneNo(phoneField)) {
            showWarning(foundResult, "Not found");
        }
        String phone = phoneField.getText().trim();
        phone = formatPhoneNo(phone);
        String username = userModel.getCustomerByPhone(phone);

        if (username == null) {
            showWarning(foundResult, "Not found");
        } else {
            showSuccessMessage(foundResult, "Found");
            System.out.println("found");
            usernameLabel.setText(username);
        }

    }

    private void showSearchResult() {
        passDataToNext();
        setupNextPage(this, "ShowSearchResultView.fxml", "Search results");

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
            if (plateNoLabel.getText().trim() != "") {
                showSummary();
            }
        });

        equip2CMB.setOnAction((ActionEvent event) -> {
            if (plateNoLabel.getText().trim() != "") {
                showSummary();
            }
        });
    }

    private void passDataToNext() {
        String branch = locationCMB.getSelectionModel().getSelectedItem();
        location = branch.split(",")[0].trim();
        city = branch.split(",")[1].trim();
        vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();
        fromDateString = fromDatePicker.getValue().toString();
        toDateString = toDatePicker.getValue().toString();

        AppContext.getInstance().setTempData("city", city);
        AppContext.getInstance().setTempData("location", location);
        AppContext.getInstance().setTempData("vehicleType", vehicleType);
        AppContext.getInstance().setTempData("fromDate", fromDateString);
        AppContext.getInstance().setTempData("toDate", toDateString);
    }

    private void showSummary() {
        System.out.println("show summary");

        plateNoLabel.setText(selectedVehicle.getVlicense());

        int redeemedPoints = 0;
        int odometer = 0;
        fromDate = fromDatePicker.getValue();
        toDate = toDatePicker.getValue();
        fromHour = Integer.parseInt(fromHourCMB.getSelectionModel().getSelectedItem().split(":")[0]);
        toHour = Integer.parseInt(toHourCMB.getSelectionModel().getSelectedItem().split(":")[0]);

        //get the equipments
        ArrayList<String> equipments = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        String equip1 = equip1Label.getText();
        int equp1Quantity = Integer.parseInt(equip1CMB.getSelectionModel().getSelectedItem());
        if (equp1Quantity > 0) {
            equipments.add(equip1);
            quantities.add(equp1Quantity);
        }

        String equip2 = equip2Label.getText();
        int equp2Quantity = Integer.parseInt(equip2CMB.getSelectionModel().getSelectedItem());
        if (equp2Quantity > 0) {
            equipments.add(equip2);
            quantities.add(equp2Quantity);
        }

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
        summaryGP = userModel.calculateCost(vehicleType, equipments, quantities,
                fromDate, fromHour, toDate, toHour, isRoadStar,
                redeemedPoints, odometer, null);
        summaryVBox.getChildren().add(summaryGP);

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

    public void handleReserve() {

    }

    public void handleRent() {

    }
}
