/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.AdminModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class FindOldVehicleController implements Initializable {

    
        @FXML
    private VBox oldVehicleVbox; // in show table tab
    private TableView tableContent = null;
    private AdminModel adminModel = new AdminModel();
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
            oldVehicleVbox.getChildren().remove(tableContent);
        }
        adminModel.refeshDatabaseConnection();
        tableContent = adminModel.getTable("vehicleforrent");
        oldVehicleVbox.getChildren().add(tableContent);
    }
    
    
}
