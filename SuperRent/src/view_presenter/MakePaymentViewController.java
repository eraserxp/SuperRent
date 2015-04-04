/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */
public class MakePaymentViewController implements Initializable {

    
    
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
       public void handleCancelButton() {

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            }
        });

    }

    public void handleConfirmButton() {

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //get the input details
                //get details from the rent table
                //delete the data in rent
                //insert the data into vreturn
                //controller.tempbutton.setDisable(false);
                //Close the window
                
                
                
                
                
                
                
                
                
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();

            }
        });

    }
    
    
}
