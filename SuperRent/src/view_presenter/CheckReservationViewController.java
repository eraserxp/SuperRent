/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ClerkModel;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class CheckReservationViewController extends AbstractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField ConfirmationTextField;
    @FXML
    private CheckBox ConfirmationCheckBox;
    
    //Labels to show the reservation details
    @FXML
    private Label ConfirmationNoLabel;
    @FXML
    private Label PickupDate;
    @FXML
    private Label PickupTime;    
    @FXML
    private Label PickupCity;    
    @FXML
    private Label PickupLocation;
    @FXML
    private Label ReturnDate; 
    @FXML
    private Label ReturnTime; 
    @FXML
    private Label EstimationCostLabel; 
    @FXML
    private Label VidLabel; 
    @FXML
    private Label CustomerLabel; 
    @FXML
    private Label EquipmentNameLabel; 
    @FXML
    private Label EquipmentTypeLabel; 
    @FXML
    private Label EquipmentNoLabel; 
                                
    
    private ClerkModel clerkModel = new ClerkModel();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       
    handleConCheckBox();
    
    
    }    
    
    
    public void handleConCheckBox(){
        
        
        hide(ConfirmationNoLabel,PickupDate,PickupTime,PickupCity,PickupLocation,ReturnDate,ReturnTime,EstimationCostLabel,VidLabel,CustomerLabel,EquipmentNameLabel,EquipmentTypeLabel,EquipmentNoLabel);
 
        ConfirmationCheckBox.setSelected(false);
        
        ConfirmationCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
        public void changed(ObservableValue<? extends Boolean> ov,
            Boolean old_val, Boolean new_val) {
 
            hide(ConfirmationNoLabel,PickupDate,PickupTime,PickupCity,PickupLocation,ReturnDate,ReturnTime,EstimationCostLabel,VidLabel,CustomerLabel,EquipmentNameLabel,EquipmentTypeLabel,EquipmentNoLabel);

            Boolean checkvalue;
            String confirmationNo = ConfirmationTextField.getText();
            
            try {
                
                checkvalue = clerkModel.checkConNumber(confirmationNo);
                if(checkvalue = true){
                    ArrayList<String> rDetails = clerkModel.getReservationDetails(confirmationNo);
                    
                    ConfirmationNoLabel.setText(rDetails.get(0));
                    PickupDate.setText(rDetails.get(1));                    
                    PickupTime.setText(rDetails.get(2));                    
                    ReturnDate.setText(rDetails.get(3));
                    ReturnTime.setText(rDetails.get(4));
                    EstimationCostLabel.setText(rDetails.get(5));
                    VidLabel.setText(rDetails.get(6));
                    PickupCity.setText(rDetails.get(7));
                    PickupLocation.setText(rDetails.get(8));
                    CustomerLabel.setText(rDetails.get(9));
                    EquipmentTypeLabel.setText(rDetails.get(10));
                    EquipmentNoLabel.setText(rDetails.get(11));
                    EquipmentNameLabel.setText(rDetails.get(12));
                    
                    if(new_val){
                    show(ConfirmationNoLabel,PickupDate,PickupTime,PickupCity,PickupLocation,ReturnDate,ReturnTime,EstimationCostLabel,VidLabel,CustomerLabel,EquipmentNameLabel,EquipmentTypeLabel,EquipmentNoLabel);
                    }
  
                    
                }else{
                    //let the validator show some warning message
                    
                }
                
                
                
                //System.out.println("lll");
            } catch (SQLException ex) {
                Logger.getLogger(CheckReservationViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
        
        
    }
    
    
    
    
    
    
}
