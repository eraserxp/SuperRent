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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.ClerkModel;

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
    private Button searchButton;
    
    @FXML
    private VBox tableViewBox; // box to hold the table view
    @FXML   
    private TableView tableView = null;
    
    private ClerkModel clerkModel = new ClerkModel();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        //set the value of the combo boxes
        VehicleTypeCBOX.setItems(CarTypeList);
        
        CategoryCBOX.setItems(CategoryList);
        
        BrandCBOX.setItems(CarBrandList);
        
//        LocationCBOX.setItems(LocationList);
        
        handleSearchButton();

        
        
    }
    
    
    
    public void handleSearchButton(){
        
    searchButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override 
    public void handle(ActionEvent e) {
        //to do
        
        String confirmationNo = confirmationField.getText(); 
//        String vehicletype = VehicleTypeCBOX.getSelectionModel().getSelectedItem().toString();
//        String category = CategoryCBOX.getSelectionModel().getSelectedItem().toString();       
//        String brand = BrandCBOX.getSelectionModel().getSelectedItem().toString();        
        
        try {
            boolean checkvalue = clerkModel.checkConNumber(confirmationNo);
            if(checkvalue == true){
                  ArrayList<String> reservation = clerkModel.getReservationDetails(confirmationNo);
//                  ObservableList<String> oListreservation = FXCollections.observableArrayList(reservation);
                  //add the info to the table view
                  //don't forget the estimation cost
//                  searchTableView.setItems(oListreservation);
//                  searchTableView.getColumns().addAll(vidColumn, categoryColumn, vehicleTypeColumn,brandColumn);
        if (tableView != null) {
            tableViewBox.getChildren().remove(tableView);
        }

        //refesh the database first so that the latest info will be displayed
        clerkModel.refeshDatabaseConnection();
        if(confirmationNo!=null){
        tableView = clerkModel.TableviewwithCN(confirmationNo);
        }
        tableViewBox.getChildren().add(tableView);
                  
                  
                  
                  
                  
            }else{
                //change the contents of the validator
                
                
                
            }
            
            
            
            
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchRentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    });
        
        
    }
    
    
    
    
    
    
    
    
}
