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
import static java.util.UUID.fromString;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import model.ManagerModel;
import model.UserModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class FindOldVehicleController extends AbstractController implements Initializable {

    @FXML
    private VBox oldVehicleVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();

    @FXML
    private TextField yearTextField;
    @FXML
    private TextField priceTextField;

    @FXML
    private Label validationLabel;

    @FXML
    private Label priceValidationLabel;

    @FXML
    private Button removeButton;

    @FXML
    private RadioButton carRadioButton;

    @FXML
    private RadioButton truckRadioButton;

    @FXML
    private CheckBox sortByCategoryCB;
    @FXML
    private CheckBox sortByLocationCB;

    @FXML
    private ComboBox<String> locationCMB;

    private String vehicleCategory;

    private String vehicleForRent;

    private ManagerModel managerModel;
    private UserModel userModel;

    final private ToggleGroup group = new ToggleGroup();
    private String city;
    private String location;
    boolean categorySelected = false;
    boolean locationSelected = false;
    boolean yearSelected = false;
    boolean vehicleSelected = false;
    boolean locationCBSlected = false;
    boolean categoryCBSelected = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        city = "";
        location = "";
        userModel = new UserModel();
        managerModel = new ManagerModel();
        setUpCarTruckRB();
        setUpLocationCMB();

        // Listen for brandTextFiled text changes
        yearTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {

                if (!isInputEmpty(yearTextField)) {

                    if (!isInputInteger(yearTextField)) {
                        showWarning(validationLabel, "Incorrect Year Format!");
                        validationLabel.setTextFill(Color.RED);
                        yearTextField.requestFocus();

                        return;
                    } else {
                        showWarning(validationLabel, "");
                    }

                }

            }
        });

        sortByCategoryCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    
                    Boolean old_val, Boolean new_val) {
              
                
                if (sortByCategoryCB.isSelected()) {
                    categoryCBSelected = true;
                } else {
                   categoryCBSelected = false;
                }

            }
        });

        sortByLocationCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {

                if (sortByLocationCB.isSelected()) {
                    locationCBSlected = true;
                } else {
                    locationCBSlected = false;
                }

            }
        });

    }

    @FXML
    private void handleShowButtonAction() throws IOException {

        if (!isInputEmpty(yearTextField)) {

            if (!isInputInteger(yearTextField)) {
                showWarning(validationLabel, "Incorrect Year Format!");
                validationLabel.setTextFill(Color.RED);
                yearTextField.requestFocus();

                return;
            } else {
                showWarning(validationLabel, "");
            }

        }

        if (tableContent != null) {
            oldVehicleVbox.getChildren().remove(tableContent);
        }

        tableContent = managerModel.getVehicles(location, vehicleCategory, yearTextField.getText(), locationCBSlected, categoryCBSelected);
        oldVehicleVbox.getChildren().add(tableContent);
     //refreshBoxes();
        tableContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    System.out.print(priceValidationLabel.getText());
                    priceValidationLabel.setVisible(false);
                    vehicleForRent = tableContent.getSelectionModel().getSelectedItem().toString();
                    System.out.print("\n" + vehicleForRent);
                    removeButton.setDisable(false);
                    vehicleSelected = true;
                    priceValidationLabel.setText("");

                }
            }
        });

        //refressh all the boxes!
        // refreshBoxes();
    }

    private void setUpLocationCMB() {

        configureComboBox(locationCMB, userModel.getAllBranches());
        locationCMB.setOnAction((ActionEvent event) -> {
            if(locationCMB.getSelectionModel().getSelectedItem()!=null){
            String branch = locationCMB.getSelectionModel().getSelectedItem();
            location = branch.split(",")[0].trim();
            city = branch.split(",")[1].trim();

            sortByLocationCB.setDisable(true);
            }
        });
    }

    private void setUpCarTruckRB() {
        carRadioButton.setToggleGroup(group);
        truckRadioButton.setToggleGroup(group);
        categorySelected = false;
        // carRadioButton.setSelected(true);
        vehicleCategory = "";
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    RadioButton rb = (RadioButton) new_toggle.getToggleGroup().getSelectedToggle();
                    vehicleCategory = rb.getText();

                    vehicleCategory = vehicleCategory.toLowerCase();
                    categorySelected = true;
                   // sortByCategoryCB.setDisable(true);

                }
            }
        });

    }

    @FXML
    private void handleRemoveButtonAction(ActionEvent event) throws IOException, SQLException {
        boolean addOk = false;
        boolean removeOk = false;

        if (!vehicleSelected) {
            showWarning(priceValidationLabel, "Please Select The Vehicle!");
            priceValidationLabel.setTextFill(Color.RED);
            priceTextField.requestFocus();
            return;

        }

        if (isInputEmpty(priceTextField)) {
            showWarning(priceValidationLabel, "Please Input The Price!");
            priceValidationLabel.setTextFill(Color.RED);
            priceTextField.requestFocus();
            return;
        } else if (!isInputInteger(priceTextField)) {
            showWarning(priceValidationLabel, "The Price is Wrong!");
            priceValidationLabel.setTextFill(Color.RED);
            priceTextField.requestFocus();
            return;
        } else {
            showWarning(priceValidationLabel, "");
        }

        String plateNumber = vehicleForRent.split(",")[0].substring(1);
        String price = priceTextField.getText();

        addOk = managerModel.addVehicleForSale(plateNumber, price);
        removeOk = managerModel.removeFromRent(plateNumber);

        //add to sale tabel
        if (addOk && removeOk) {
            showWarning(priceValidationLabel, "Vehicle Removed!");
            priceValidationLabel.setTextFill(Color.GREEN);
            //refresh the table
            handleShowButtonAction();
            //refreshVehicleForRentTable();
            priceTextField.setText("");
            vehicleSelected = false;

        } else {
            showWarning(priceValidationLabel, "Vehicle Not Removed!");
            priceValidationLabel.setTextFill(Color.RED);

        }

    }

    public void refreshVehicleForRentTable() {
        if (tableContent != null) {
            oldVehicleVbox.getChildren().remove(tableContent);
        }
        tableContent = adminModel.getTable("vehicleforrent");
        oldVehicleVbox.getChildren().add(tableContent);
    }
    
 @FXML
    public void refreshBoxes() {

        carRadioButton.setSelected(false);
        truckRadioButton.setSelected(false);
        vehicleCategory = "";
        locationCMB.getSelectionModel().clearSelection();
        locationCMB.valueProperty().set(null);
        location = "";
        city = "";
        yearTextField.setText("");
      
        categorySelected = false;
        locationSelected=false;
        sortByCategoryCB.setDisable(false);
        sortByLocationCB.setDisable(false);
        sortByLocationCB.setSelected(false);
        sortByCategoryCB.setSelected(false);
        
        
        
        
        

    }

}
