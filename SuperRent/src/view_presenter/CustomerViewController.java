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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;




public class CustomerViewController implements Initializable {
    
@FXML
private ComboBox<String> LocationCombobox;
ObservableList<String> LocationList=FXCollections.observableArrayList (
        "Location-1","Location-2","Location-3");


@FXML
private ComboBox<String> VehicleTypeCombobox;
ObservableList<String> VehicleList=FXCollections.observableArrayList (
        "Car","Truck");


@FXML
private ComboBox<String> VehicleModelCombobox;
ObservableList<String> CarModelList=FXCollections.observableArrayList (
        "CarModel-1","CarModel-2");
ObservableList<String> TruckModelList=FXCollections.observableArrayList (
        "TruckModel-1","TruckModel-2");


@FXML
private ComboBox<String> PickUpTimeCombobox;
@FXML
private ComboBox<String> ReturnTimeCombobox;
ObservableList<String> TimeList=FXCollections.observableArrayList (
        "1:00","2:00","3:00","4:00","5:00","6:00","7:00","8:00","9:00","10:00","11:00","12:00");


@FXML
private ComboBox<String> TimeCombobox1;
@FXML
private ComboBox<String> TimeCombobox2;
ObservableList<String> List=FXCollections.observableArrayList (
        "AM","PM");



    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        LocationCombobox.setItems(LocationList);
        VehicleTypeCombobox.setItems(VehicleList);
        PickUpTimeCombobox.setItems(TimeList);
        ReturnTimeCombobox.setItems(TimeList);
        TimeCombobox1.setItems(List);
        TimeCombobox2.setItems(List);
        
        
        
        
        
       
        
        /*
        if("Car".equals(VehicleTypeCombobox.getValue()))
        {
            VehicleModelCombobox.setItems(CarModelList);
        }
        else
        {
            VehicleModelCombobox.setItems(TruckModelList);
      
        }
                */
    }    
    
}
