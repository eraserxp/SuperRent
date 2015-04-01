/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.AdminModel;
import model.ManagerModel;
import model.UserModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class ManageVehicleController extends AbstractController implements Initializable {

    @FXML
    private ComboBox typeCombobox;
    @FXML
    private TextField plateNumTextField;
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
    private VBox vehicleForSaleVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
    private ManagerModel managerModel = new ManagerModel();
    private UserModel userModel = new UserModel();

    //=========
    private String carCategory;
    private String carType;
    private String plateNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpCategoryRadioButtons();
        startingDateDateBoxSetUp();

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

    private void setUpTypeCombobox() {

        ArrayList<String> VehicleType = userModel.getVehicleTypeAtBranch("Vancouver", "2660 Wesbrook Mall", carType);
        configureComboBox(typeCombobox, VehicleType);

    }

    public void handleAddButton() {
        boolean addOK = false;

        addOK = managerModel.addVehicle(carCategory, carType, startingDateDateBox.getValue(), plateNumber);

    }

    public void setUpCategoryRadioButtons() {

        final ToggleGroup group = new ToggleGroup();

        truckRadioButton.setToggleGroup(group);
        truckRadioButton.setUserData("Truck");

        carRadioButton.setToggleGroup(group);
        carRadioButton.setUserData("Car");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    //System.out.println(group.getSelectedToggle().getUserData().toString());

                    carCategory = group.getSelectedToggle().getUserData().toString();
                    setUpTypeCombobox();
                    typeCombobox.setDisable(false);
                }
            }
        });

    }

    public void startingDateDateBoxSetUp() {
        startingDateDateBox.setDisable(false);
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        System.out.println("pickUpUstartingDateDateBoxpdate");
                        super.updateItem(item, empty);
                        if (item.compareTo(LocalDate.now()) < 0) {
                            setDisable(true);
                        }
                    }
                };
            }
        };

        //actionHandler
        startingDateDateBox.setOnAction((ActionEvent event) -> {
            addButton.setDisable(false);
            // returnDate.setValue(null);

        });

        startingDateDateBox.setDayCellFactory(dayCellFactory);

    }

}
