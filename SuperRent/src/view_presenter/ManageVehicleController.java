/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.AdminModel;
import model.ManagerModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class ManageVehicleController implements Initializable {
    
    @FXML 
    private TextField typeTextField;
    private TextField plateNumTextField;
    private RadioButton carRadioButton;
    private RadioButton truckRadioButton;
    private Date startingDateDateBox;
    private Button addButton;
    private Button showButton;
    private Button soldButton;
   
    
    @FXML
    private VBox vehicleForSaleVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
    private ManagerModel managerModel=new ManagerModel();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    
   
}
