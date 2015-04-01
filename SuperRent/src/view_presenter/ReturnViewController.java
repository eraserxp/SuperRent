/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ClerkModel;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class ReturnViewController extends AbstractController implements Initializable {

    @FXML
    private TextField VehicleTextField;
    @FXML
    private CheckBox ShowRentCheckBox;

    //Labels to show the reservation details
    @FXML
    private Label VehicleLicenseNumberLabel;
    @FXML
    private Label DriverLicenseLabel;
    @FXML
    private Label PickupDate;
    @FXML
    private Label PickupTime;
    @FXML
    private Label PickupLocation;
    @FXML
    private Label PickupCity;
    @FXML
    private Label CustomerUsernameLabel;
    @FXML
    private Label EquipmentNameLabel;
    @FXML
    private Label EquipmentNoLabel;
    @FXML
    private Label Validator;    
    //returnbutton
    @FXML
    private Button ReturnButton;

    @FXML
    AnchorPane anchorpane;

    private ClerkModel clerkModel = new ClerkModel();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Disable the button and hide the labels
        ShowRentCheckBox.setDisable(true);
        ReturnButton.setVisible(false);
        hide(Validator,DriverLicenseLabel, VehicleLicenseNumberLabel, PickupDate, PickupTime, PickupCity, PickupLocation, CustomerUsernameLabel, EquipmentNameLabel, EquipmentNoLabel);
        //the check box should not be checked
        ShowRentCheckBox.setSelected(false);
        
        setUpVehicleTextField();
        
        handleConCheckBox();
        
        handleReturnBox();

    }

    
    public void setUpVehicleTextField(){
        
                VehicleTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // lost focus
                    String checkString = VehicleTextField.getText();
                    ArrayList<String> checkValue = new ArrayList<>();
                    try {
                        checkValue = clerkModel.getRentDetails(checkString);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            if (!newValue) {
                if (checkString.isEmpty()) {
                    showWarning(Validator, "Vehicle License Number can't be empty!");
                    VehicleTextField.setDisable(false);
                    ShowRentCheckBox.setDisable(true);
                } else if (checkValue==null) {
                    showWarning(Validator, "Vehicle License Number is invalid!");
                    VehicleTextField.setDisable(false);
                    ShowRentCheckBox.setDisable(true);
                } else {
                    showWarning(Validator, "Valid!");
                    VehicleTextField.setDisable(true);
                    ShowRentCheckBox.setDisable(false);
                }
            }else{
                hide(Validator);
                ShowRentCheckBox.setDisable(true);
            }
        });
        
        
    }
    
        public void handleConCheckBox() {

        ShowRentCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean old_val, Boolean new_val) {
                
                hide(DriverLicenseLabel, VehicleLicenseNumberLabel, PickupDate, PickupTime, PickupCity, PickupLocation, CustomerUsernameLabel, EquipmentNameLabel, EquipmentNoLabel);

                String VehicleNo = VehicleTextField.getText();
                
                try {

                    ArrayList<String> rDetails = clerkModel.getRentDetails(VehicleNo);
                    if (rDetails != null) {
                        
                        VehicleLicenseNumberLabel.setText(rDetails.get(0));
                        DriverLicenseLabel.setText(rDetails.get(1));
                        PickupDate.setText(rDetails.get(2));
                        PickupTime.setText(rDetails.get(3));
                        PickupCity.setText(rDetails.get(4));
                        PickupLocation.setText(rDetails.get(5));
                        CustomerUsernameLabel.setText(rDetails.get(6));
                        EquipmentNameLabel.setText(rDetails.get(7));
                        EquipmentNoLabel.setText(rDetails.get(8));
                        ReturnButton.setVisible(true);

                        if (new_val) {
                            show(DriverLicenseLabel, VehicleLicenseNumberLabel, PickupDate, PickupTime, PickupCity, PickupLocation, CustomerUsernameLabel, EquipmentNameLabel, EquipmentNoLabel);
                        }else{
                            //change the validator content
                            ShowRentCheckBox.setDisable(true);
                            VehicleTextField.clear();
                            VehicleTextField.setDisable(false);
                            ReturnButton.setVisible(false);
                        }
                    }

                    //System.out.println("lll");
                } catch (SQLException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    //handle the actions of the return button
    public void handleReturnBox() {
        ReturnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                System.out.println("Hello");

                try {
                    showReturnDialog();
                } catch (IOException ex) {
                    Logger.getLogger(ReturnViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public void showReturnDialog() throws IOException {
        AnchorPane loader = (AnchorPane) FXMLLoader.load(ReturnDialogViewController.class.getResource("ReturnDialogView.fxml"));

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Return Information");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(loader);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

    }

}
