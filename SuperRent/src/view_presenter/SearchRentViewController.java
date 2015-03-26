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
        "economy","luxury");
    ObservableList<String> TruckTypeList=FXCollections.observableArrayList (
        "foot12","foot24");
    @FXML
    private ComboBox CategoryCBOX;
    ObservableList<String> CategoryList=FXCollections.observableArrayList (
        "car","truck");
    @FXML
    private ComboBox BrandCBOX;
    ObservableList<String> CarBrandList=FXCollections.observableArrayList (
        "BMW","Honda");
    ObservableList<String> TruckBrandList=FXCollections.observableArrayList (
        "BMW","Honda");

    @FXML   
    private Button searchButton;
    @FXML   
    private Button reserveButton;
    @FXML   
    private Button rentButton;
    
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
        
        handleSearchButton();
        
        handleReserveButton();
        
        handleRentButton();

        
        
    }
    
@FXML   
private void handleReserveButton() {

    reserveButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override 
    public void handle(ActionEvent e) {
        //to do  
        //get Selected Item
//        Person currentPerson = (Person) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
//        //remove selected item from the table list
//        data.remove(currentPerson);
        
        
        
        System.out.println("testing");
        
    }
    });
    
 
    
}
 
@FXML
private void handleRentButton() {

    rentButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override 
    public void handle(ActionEvent e) {
        //to do  
        
        
        
        
        System.out.println("testing");
    }
    });
    
    
    
    
}    
    
@FXML    
private void handleSearchButton(){
    
    reserveButton.setVisible(false);
    rentButton.setVisible(false);
    searchButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override 
    public void handle(ActionEvent e) {
        //to do
        String vehicletype = null;
        String category = null;
        String brand = null;
        String confirmationNo = null; 
        
        if(VehicleTypeCBOX.getSelectionModel().getSelectedItem()!=null){
        vehicletype = VehicleTypeCBOX.getSelectionModel().getSelectedItem().toString();        
        }
        if(CategoryCBOX.getSelectionModel().getSelectedItem()!=null){
        category = CategoryCBOX.getSelectionModel().getSelectedItem().toString();        
        }
        if(BrandCBOX.getSelectionModel().getSelectedItem()!=null){
        brand = BrandCBOX.getSelectionModel().getSelectedItem().toString();        
        }
        if(confirmationField.getText()!=null){
        confirmationNo = confirmationField.getText();    
        }

        
    try {

        //add the info to the table view
        //don't forget the estimation cost
        if (tableView != null) {
            tableViewBox.getChildren().remove(tableView);
        }
        //refesh the database first so that the latest info will be displayed
        clerkModel.refeshDatabaseConnection();
        tableView = clerkModel.setupTableview(confirmationNo,vehicletype,category,brand);
        tableViewBox.getChildren().add(tableView);
//        reserveButton.setDisable(false);
//        rentButton.setDisable(false);
        reserveButton.setVisible(true);
        rentButton.setVisible(true);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchRentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    });
        
        
    }
    
    


    
    
    
}
