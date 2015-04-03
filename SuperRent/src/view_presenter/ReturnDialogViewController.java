/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//how to disable the returnbutton in handlecancelbutton
//payment dialog (customer card number input blahblahblah)
//use vehicle number to identify the most recently rented vehicle (with the biggest rentid)
//insert data in the other tables
//set the limit to the pk_rate
//set the window to be unresized
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ClerkModel;

/**
 * FXML Controller class
 *
 * @author dongshengshen
 */

public class ReturnDialogViewController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;
    
    //dialog columns
    @FXML
    private DatePicker returnDate;
    @FXML    
    private ComboBox returnTime;
    @FXML    
    private ComboBox returnCity; 
    @FXML    
    private ComboBox returnLocation; 
    @FXML    
    private ComboBox tank_full;     
    @FXML    
    private TextField odometer; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        handleCancelButton();

        handleConfirmButton();

    }

    public void calculateCosts() {

        //get the required information
        //check reserve or not
        //overdue or not (penality applied?)
        //insurance for the overdue time
        //get the weeks,days and hours to calculate the rent charge
        //also include pk_rate and set a limit to it
        //calculate the insurance cost according to the time
        //calculate the additional cost considering the type of equipments and their quantities
        //how to set the gasline fees
        //calculate the total cost and set it to show automatically
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
