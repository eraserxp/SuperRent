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
public class ReturnDialogViewController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleCancelButton() {

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //System.out.println("Hello");
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            }
        });

    }

    public void handleConfirmButton() {

    }

}
