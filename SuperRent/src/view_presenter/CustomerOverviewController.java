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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;




public class CustomerOverviewController  extends AbstractController implements Initializable {
    
     @FXML
    private TabPane tabPane;
    
    
     @FXML
    private Tab makeReseravtion_tab;

    @FXML
    private Tab changePassword_tab;
    
    
    @FXML
    private BorderPane searchVehiclePane;
    
    
/*
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

*/



    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
                tabPane.getSelectionModel().select(makeReseravtion_tab);

        
        makeReseravtion_tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (makeReseravtion_tab.isSelected()) {
                    try {
                      
                        Parent root = FXMLLoader.load(getClass().getResource("ReservationView.fxml"));
                         searchVehiclePane.setCenter(root);
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        


        
        
        //LocationCombobox.setItems(LocationList);
        /*VehicleTypeCombobox.setItems(VehicleList);
        PickUpTimeCombobox.setItems(TimeList);
        ReturnTimeCombobox.setItems(TimeList);
        TimeCombobox1.setItems(List);
        TimeCombobox2.setItems(List);
        
        */
        
        
        
       
        
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
