/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
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
    private Button showButton;
    
    @FXML
    private Button soldButton;
    
    @FXML
    private TextField handleAddButtonAction;

    @FXML
    private ComboBox<String> locationCMB;

    @FXML
    private Label addingValidator;

    @FXML
    private VBox vehicleForSaleVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
    private ManagerModel managerModel = new ManagerModel();
    private UserModel userModel = new UserModel();

    //=========
    
    private String vehicleCategory;
    private String vehicleType;
    private boolean vehicleTypeIsOk = false;
    private String plateNumber;
    private boolean plateIsOk = false;
    private String brand;
    private boolean brandIsOk = false;
    final private ToggleGroup group = new ToggleGroup();
    private String city, location;
    private String username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        username=AppContext.getInstance().getUsername();
        
        System.out.print(username);
        setUpCarTruckRB();
        setUpLocationCMB();
        startingDateDateBoxSetUp();
        setUpLocationCMB();
        setUpCarTruckRB();

    }

    @FXML
    private void handleShowButtonAction(ActionEvent event) throws IOException {
        if (tableContent != null) {
            vehicleForSaleVbox.getChildren().remove(tableContent);
        }
        adminModel.refeshDatabaseConnection();
        tableContent = adminModel.getTable("vehicleforsale");
        vehicleForSaleVbox.getChildren().add(tableContent);
    }

    public void handleAddButton() {
        if (locationCMB.getSelectionModel().isEmpty()) {
            showWarning(addingValidator, "Location is empty!");
            addingValidator.setTextFill(Color.RED);
            return;
        }
        

        if (typeCombobox.getSelectionModel().isEmpty()) {
            showWarning(addingValidator, "Vehicle type is empty!");
            addingValidator.setTextFill(Color.RED);
            return;
        }
        
        LocalDate startDate = startingDateDateBox.getValue();
        if (startDate == null) {
            showWarning(addingValidator, "Date is empty!");
            addingValidator.setTextFill(Color.RED);
            return;
        }
       

        if (isInputEmpty(brandTextField)) {
            showWarning(addingValidator, "Brand is empty!");
            addingValidator.setTextFill(Color.RED);
            brandTextField.requestFocus();
            return;
        }
        else
        {
            brand=brandTextField.getText();
            brandIsOk=true;
          
        
        }

        if (isInputEmpty(plateNumTextField)) {
            showWarning(addingValidator, "Plate Number is empty!");
            addingValidator.setTextFill(Color.RED);
            plateNumTextField.requestFocus();
            return;
        }
        
       plateNumber=plateNumTextField.getText();
       if( managerModel.checkPlateNumber(plateNumber))
        { showWarning(addingValidator, "Plate Number Exist!");
            addingValidator.setTextFill(Color.RED);
            plateNumTextField.requestFocus();
            return;
        }  
            
         else{ 
            
           
        plateIsOk=true;
        
        
        }

        //========================================================================
        boolean addOK = false;

        addOK = managerModel.addVehicle(username,plateNumber, startingDateDateBox.getValue(),
                vehicleCategory, vehicleType, brand);

        
        if (addOK == true) {
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
                    //System.out.println(vehicleCategory);
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
                                userModel.getVehicleTypeAtBranch(city, location, vehicleCategory));
                    }

                }
            }
        });

    }

    public void startingDateDateBoxSetUp() {
        startingDateDateBox.setDisable(false);
        /*
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        //System.out.println("pickUpUstartingDateDateBoxpdate");
                        super.updateItem(item, empty);
                        if (item.compareTo(LocalDate.now()) < 0) {
                            setDisable(true);
                        }
                    }
                };
            }
        };
    */
        //actionHandler
        startingDateDateBox.setOnAction((ActionEvent event) -> {
            addButton.setDisable(false);
            // returnDate.setValue(null);

        });

       // startingDateDateBox.setDayCellFactory(dayCellFactory);

    }
    
    
    private void handleVehicleType(){
         typeCombobox.setOnAction((ActionEvent event) -> {
           vehicleType= typeCombobox.getSelectionModel().getSelectedItem();
            
                      
                  });
                 }

    private void setUpLocationCMB() {

        configureComboBox(locationCMB, userModel.getAllBranches());
        locationCMB.setOnAction((ActionEvent event) -> {
            String branch = locationCMB.getSelectionModel().getSelectedItem();
            location = branch.split(",")[0].trim();
            city = branch.split(",")[1].trim();
            configureComboBox(typeCombobox,
                    userModel.getVehicleTypeAtBranch(city, location, vehicleCategory));
            handleVehicleType();
        });
    }
    
    
    

}
