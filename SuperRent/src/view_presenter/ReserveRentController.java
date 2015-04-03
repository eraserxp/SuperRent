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

    private String fromDate;

    @FXML
    private ComboBox<String> fromHourCMB;

    private int fromHour;

    @FXML
    private DatePicker toDatePicker;

    private String toDate;

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
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label foundResult;

    @FXML
    private Button registerButton;

    @FXML
    private VBox summaryVBox;

    private GridPane summaryGP;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label plateNoLabel;

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

        usernameLabel.setText("");
        plateNoLabel.setText("");
        setupUsernameField();
        setupPhoneField();
        foundResult.setText("");
        

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
        configureHourCMB(toHourCMB);
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

        showSearchResult();

    }

    private void setupUsernameField() {
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // check the input when the focus on this text field is lost
            if (!newValue) {
                
                //validate the username
                if (!isInputEmpty(usernameField)) {
                    String username = usernameField.getText().trim();
                    if (!userModel.isCustomerExisted(username)) {
                        showWarning(foundResult, "Not found");
                    } else {

                        showSuccessMessage(foundResult, "Found");
                        usernameLabel.setText(username);
                    }
                }
            }

            //when user is entering something into the field, remove any previous validation message
            if (newValue) {
                usernameField.clear();
                foundResult.setText("");
            }
        });
    }

        private void setupPhoneField() {
        phoneField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // check the input when the focus on this text field is lost
            if (!newValue) {
                
                //validate the username
                if (!isInputEmpty(phoneField)) {
                    
                    if (!isInputPhoneNo(phoneField)) {
                        showWarning(foundResult, "Not found");
                    }
                    String phone = phoneField.getText().trim();
                    phone = formatPhoneNo(phone);
                    String username = userModel.getCustomerByPhone(phone);
                    
                    if (username==null) {
                        showWarning(foundResult, "Not found");
                    } else {
                        showSuccessMessage(foundResult, "Found");
                        usernameLabel.setText(username);
                    }
                }
            }

            //when user is entering something into the field, remove any previous validation message
            if (newValue) {
                phoneField.clear();
                foundResult.setText("");
            }
        });
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
    }

    private void passDataToNext() {
        String branch = locationCMB.getSelectionModel().getSelectedItem();
        location = branch.split(",")[0].trim();
        city = branch.split(",")[1].trim();
        vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();
        fromDate = fromDatePicker.getValue().toString();
        toDate = toDatePicker.getValue().toString();

        AppContext.getInstance().setTempData("city", city);
        AppContext.getInstance().setTempData("location", location);
        AppContext.getInstance().setTempData("vehicleType", vehicleType);
        AppContext.getInstance().setTempData("fromDate", fromDate);
        AppContext.getInstance().setTempData("toDate", toDate);
    }

    private void showSummary() {
        System.out.println("show summary");

        plateNoLabel.setText(selectedVehicle.getVlicense());

        int redeemedPoints = 0;
        int odometer = 0;
        LocalDate startDate = fromDatePicker.getValue();
        LocalDate endDate = toDatePicker.getValue();
        fromHour = Integer.parseInt(fromHourCMB.getSelectionModel().getSelectedItem().split(":")[0]);
        toHour = Integer.parseInt(toHourCMB.getSelectionModel().getSelectedItem().split(":")[0]);
        if (summaryGP != null) {
            summaryVBox.getChildren().remove(summaryGP);
        }
        summaryGP = userModel.calculateCost(vehicleType, null, null,
                startDate, fromHour, endDate, toHour, false,
                redeemedPoints, odometer);
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
}
