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
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.AdminModel;
import model.AppContext;
import model.ManagerModel;
import model.UserModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class ManageVehicleController extends AbstractController implements Initializable {

    @FXML
    private ComboBox<String> typeCombobox;
   

    @FXML
    private TextField plateNumTextField;

    @FXML
    private TextField brandTextField;

    @FXML
    private RadioButton carRadioButton;

    @FXML
    private RadioButton truckRadioButton;

    @FXML
    private DatePicker startingDateDateBox;

    @FXML
    private Button addButton;

  

    @FXML
    private Button soldButton;

   
    @FXML
    private ComboBox<String> locationCMB;

    @FXML
    private Label addingValidator;

    @FXML
    private Label soldLabel;

    @FXML
    private VBox vehicleForSaleVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
    private ManagerModel managerModel;
    private UserModel userModel;

    private String soldVehicle;

    //=========
    private String vehicleCategory = "";
    private String vehicleType = "";
    private boolean vehicleTypeIsOk = false;
    private String plateNumber = "";
    private boolean plateIsOk = false;
    private String brand = "";
    private boolean brandIsOk = false;
    final private ToggleGroup group = new ToggleGroup();
    private String city = "";
    private String location = "";
    private String username = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username = AppContext.getInstance().getUsername();
        userModel = new UserModel();
        managerModel = new ManagerModel();

        // Listen for brandTextFiled text changes
        brandTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (isInputEmpty(brandTextField)) {
                    showWarning(addingValidator, "Brand is empty!");
                    addingValidator.setTextFill(Color.RED);
                    brandTextField.requestFocus();
                    return;
                } else {
                    brand = brandTextField.getText();
                    showWarning(addingValidator, "");

                }

            }
        });

        // Listen for plateNumber text changes
        plateNumTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (isInputEmpty(plateNumTextField)) {
                    showWarning(addingValidator, "Plate Number is empty!");
                    addingValidator.setTextFill(Color.RED);
                    plateNumTextField.requestFocus();
                    return;
                } else {

                    showWarning(addingValidator, "");

                }

             

            }
        });

        System.out.print(username);
        setUpCarTruckRB();
        setUpLocationCMB();
        startingDateDateBoxSetUp();
        setUpLocationCMB();
        setUpCarTruckRB();

    }

    @FXML
    private void handleShowButtonAction() throws IOException {
        if (tableContent != null) {
            vehicleForSaleVbox.getChildren().remove(tableContent);
        }

        tableContent = adminModel.getTable("vehicleforsale");
        vehicleForSaleVbox.getChildren().add(tableContent);

        tableContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    soldLabel.setVisible(false);
                    soldVehicle = tableContent.getSelectionModel().getSelectedItem().toString();
                    System.out.print(soldVehicle);
                    soldButton.setVisible(true);
                }
            }
        });

    }

    public void handleAddButton() throws SQLException {

        if (locationCMB.getSelectionModel().isEmpty()) {
            showWarning(addingValidator, "Location is empty!");
            addingValidator.setTextFill(Color.RED);
            return;
        } else {

            showWarning(addingValidator, "");

        }

        if (typeCombobox.getSelectionModel().isEmpty()) {
            showWarning(addingValidator, "Vehicle type is empty!");
            addingValidator.setTextFill(Color.RED);
            return;
        } else {

            showWarning(addingValidator, "");

        }

        LocalDate startDate = startingDateDateBox.getValue();
        if (startDate == null) {
            showWarning(addingValidator, "Date is empty!");
            addingValidator.setTextFill(Color.RED);
            return;
        } else {

            showWarning(addingValidator, "");

        }

        if (isInputEmpty(brandTextField)) {
            showWarning(addingValidator, "Brand is empty!");
            addingValidator.setTextFill(Color.RED);
            brandTextField.requestFocus();
            return;
        }

        if (isInputEmpty(plateNumTextField)) {
            showWarning(addingValidator, "Plate Number is empty!");
            addingValidator.setTextFill(Color.RED);
            plateNumTextField.requestFocus();
            return;
        }
           plateNumber = plateNumTextField.getText();
                if (managerModel.checkPlateNumber(plateNumber)) {
                    showWarning(addingValidator, "Plate Number Exist!");
                    addingValidator.setTextFill(Color.RED);
                    plateNumTextField.requestFocus();
                    return;
                } else {

                    showWarning(addingValidator, "");

                }

        //========================================================================
        boolean addRentAndBranch = false;

        addRentAndBranch = managerModel.addVehicleinRentAndInbranch(username, plateNumber, city, location, startingDateDateBox.getValue(), vehicleCategory, vehicleType, brand);
        if (addRentAndBranch) {
            showSuccessMessage(addingValidator, "Vehicle Added!");
            addingValidator.setTextFill(Color.GREEN);
           
        } else {
            showWarning(addingValidator, "Vehicle Not Added!");
            addingValidator.setTextFill(Color.RED);

        }
    }

    private void setUpCarTruckRB() {
        carRadioButton.setToggleGroup(group);
        truckRadioButton.setToggleGroup(group);
        carRadioButton.setSelected(true);
        vehicleCategory = "car";

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
//                    vehicleCategory = group.getSelectedToggle().getUserData().toString();
                    RadioButton rb = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                    vehicleCategory = rb.getText();
                    showWarning(addingValidator, "");
                    vehicleCategory = vehicleCategory.toLowerCase();

                    //if location has been selected, reconfigure vehicleType combobox
                    if (!locationCMB.getSelectionModel().isEmpty()) {
                        String branch = locationCMB.getSelectionModel().getSelectedItem();
                        location = branch.split(",")[0].trim();
                        city = branch.split(",")[1].trim();
                        //enable vehicleTypeCMB
                        typeCombobox.setDisable(false);

                        //configure the vehicleType Combobox
                        configureComboBox(typeCombobox,
                                managerModel.getVehicleTypeAtBranch(city, location, vehicleCategory));

                    }

                }
            }
        });

    }

    public void startingDateDateBoxSetUp() {
        startingDateDateBox.setDisable(false);

        //actionHandler
        startingDateDateBox.setOnAction((ActionEvent event) -> {
            addButton.setDisable(false);
            showWarning(addingValidator, "");

        });

    }

    private void handleVehicleType() {
        typeCombobox.setOnAction((ActionEvent event) -> {
            showWarning(addingValidator, "");
            vehicleType = typeCombobox.getSelectionModel().getSelectedItem();

        });
    }

    private void setUpLocationCMB() {

        configureComboBox(locationCMB, userModel.getAllBranches());
        locationCMB.setOnAction((ActionEvent event) -> {
            
            showWarning(addingValidator, "");
            String branch = locationCMB.getSelectionModel().getSelectedItem();
                        if(locationCMB.getSelectionModel().getSelectedItem()!=null){

            location = branch.split(",")[0].trim();
            city = branch.split(",")[1].trim();

            configureComboBox(typeCombobox,
                    managerModel.getVehicleTypeAtBranch(city, location, vehicleCategory));

            handleVehicleType();
                        }
        });
        
        
      
    }

    @FXML
    private void handleSoldButtonAction() throws IOException, SQLException {

        String plateNumber = soldVehicle.split(",")[0].substring(1);

        String price = soldVehicle.split(",")[1].trim();
        String category = soldVehicle.split(",")[3].trim();
        String type = soldVehicle.split(",")[5].trim();
        String brand = soldVehicle.split(",")[4].trim();
        String odometer = soldVehicle.split(",")[6].split("]")[0].trim();

        if (managerModel.removeFromSaleAndBranchAddtoSold(username, plateNumber, type, category, brand, odometer, price)) {
            showWarning(soldLabel, "Vehicle has been removed!");
            soldLabel.setTextFill(Color.GREEN);
            handleShowButtonAction();
        } else {

            showWarning(soldLabel, "Vehicle not removed!");
            soldLabel.setTextFill(Color.RED);

        }

    }
    
    
    
    
       public void refreshBoxes() {

        carRadioButton.setSelected(false);
        truckRadioButton.setSelected(false);
        vehicleCategory = "";
        locationCMB.getSelectionModel().clearSelection();
        locationCMB.valueProperty().set(null);
        location = "";
        city = "";
        plateNumTextField.setText("");
      brandTextField.setText("");
        
        
        
        
        

    }

}
