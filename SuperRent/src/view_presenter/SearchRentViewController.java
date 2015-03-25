/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class SearchRentViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
        //check reservation tab
    @FXML
    private TextField confirmationField;
    
    @FXML
    private ComboBox VehicleTypeCBOX;
    ObservableList<String> CarTypeList=FXCollections.observableArrayList (
        "Economy","Luxury");
    ObservableList<String> TruckTypeList=FXCollections.observableArrayList (
        "12-foot","24-foot");
    @FXML
    private ComboBox CategoryCBOX;
    ObservableList<String> CategoryList=FXCollections.observableArrayList (
        "Car","Truck");
    @FXML
    private ComboBox BrandCBOX;
    ObservableList<String> CarBrandList=FXCollections.observableArrayList (
        "BMW","Honda");
    ObservableList<String> TruckBrandList=FXCollections.observableArrayList (
        "BMW","Honda");
    @FXML
    private ComboBox LocationCBOX;
    ObservableList<String> LocationList=FXCollections.observableArrayList (
        "Vancouver","Toronto");
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        //set the value of the combo boxes
        VehicleTypeCBOX.setItems(CarTypeList);
        
        
        
        
        
        
        
    }    
    
}
