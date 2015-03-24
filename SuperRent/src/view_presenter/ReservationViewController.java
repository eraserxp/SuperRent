/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.AdminModel;


/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class ReservationViewController implements Initializable {
   
    @FXML
    private Button searchButton; 
    @FXML
    private Label selectedLable;
    @FXML
    private VBox tableContentBox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
    @FXML
    private BorderPane selectVehiclePane;
   
@FXML
private ComboBox<String> LocationCombobox;
ObservableList<String> LocationList=FXCollections.observableArrayList (
        "Location-1","Location-2","Location-3");


@FXML
private ComboBox<String> VehicleCategoryCombobox;
ObservableList<String> VehicleCategory=FXCollections.observableArrayList (
        "Car","Truck");


@FXML
private ComboBox<String> VehicleTypeCombobox;
ObservableList<String> CarTypeList=FXCollections.observableArrayList (
        "CarType-1","CarType-2");
ObservableList<String> TruckTypeList=FXCollections.observableArrayList (
        "TruckType-1","TruckType-2");

@FXML
private ComboBox<String> VehicleBrandCombobox;
ObservableList<String> CarBrandList=FXCollections.observableArrayList (
        "CarBrand-1","CarBrand-2");
ObservableList<String> TruckBrandList=FXCollections.observableArrayList (
        "TruckBrand-1","TruckBrand-2");





    
    
       
 @FXML
    private void handleSearchButtonAction(ActionEvent event) throws IOException {
    if (tableContent != null) {
            tableContentBox.getChildren().remove(tableContent);
        }
        adminModel.refeshDatabaseConnection();
        tableContent = adminModel.getTable("vehicleforrent");
        tableContentBox.getChildren().add(tableContent);
    }
    
    
     @FXML
    private void handleReserveButtonAction(ActionEvent event) throws IOException {
                    try {
                      
                        Parent root = FXMLLoader.load(getClass().getResource("SelectVehicleView.fxml"));
                         selectVehiclePane.setCenter(root);
                         System.out.print("here");
                    } catch (IOException ex) {
                        Logger.getLogger(ReservationViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        locationComboBoxSetUp();
        VehicleCategoryCombobox.setItems(VehicleCategory);
        VehicleTypeCombobox.setItems(CarTypeList);
        VehicleBrandCombobox.setItems(CarBrandList);
        
     
    }

public void locationComboBoxSetUp(){
    LocationCombobox.setItems(LocationList);
  LocationCombobox.setOnAction((ActionEvent event) -> {
            String sth = LocationCombobox.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + sth + ")");
            

        });
}

}

  
