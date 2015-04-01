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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AppContext;
import model.ClerkModel;
import model.CustomerModel;
import model.UserModel;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class ReserveRentController extends AbstractController implements Initializable {

    @FXML
    private ComboBox<String> locationCMB;

    @FXML
    private RadioButton carRB;

    final private ToggleGroup group = new ToggleGroup();

    private String vehicleCategory;

    @FXML
    private RadioButton truckRB;

    @FXML
    private ComboBox<String> vehicleTypeCMB;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private ComboBox<String> fromHourCMB;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private ComboBox<String> toHourCMB;

    @FXML
    private Button searchButton;

    @FXML
    private VBox summaryVBox;

    private UserModel userModel;

    private String city, location;

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
        int fromHour = Integer.parseInt( fromHourString.split(":")[0] );
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
        int toHour = Integer.parseInt( toHourString.split(":")[0] );
        System.out.println("to hour = " + toHour);

        //check the from time is earlier than to time
        if ( fromDate.compareTo(toDate)>0 
                || (fromDate.compareTo(toDate)==0 && fromHour>=toHour)
                ) {
            popUpError("The 'From time' is not earlier than the 'To time'!");
            return;
        }
        
        int redeemedPoints = 0;
        int odometer = 0;
        GridPane summaryPane = userModel.calculateCost(vehicleType, null, null,
                fromDate, fromHour, toDate, toHour, true,
                redeemedPoints, odometer);
        summaryVBox.getChildren().add(summaryPane);
    }

}