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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AppContext;

/**
 * FXML Controller class
 *
 *
 */
public class ManagerViewController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label userTypeLabel;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab manageVehicleTab;

    @FXML
    private Tab findOldVehicleTab;
    
    @FXML
    private Tab setPriceTab;

    @FXML
    private BorderPane manageVehiclePane;

    @FXML
    private BorderPane fineOldVehiclePane;

    @FXML
    private Button logoutButton;
    
    
     @FXML
    private TextField typeTextField;
     @FXML
    private TextField weeklyRateTextField;
     
     @FXML
    private TextField dailyRateTextField;
     @FXML
    private TextField hourlyRateTextField;
     
     
     @FXML
    private TextField PkRateTextField;
     @FXML
    private TextField weeklyInsuranceTextField;
     @FXML
    private TextField dailyInsuranceTextField;
     @FXML
    private TextField hourlyInsuranceTextFieldv;
     
     
     @FXML
    private Button updateButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabPane.getSelectionModel().select(manageVehicleTab);
        usernameLabel.setText(AppContext.getInstance().getUsername());
        usernameLabel.setTextFill(Color.GREEN);
        userTypeLabel.setText(AppContext.getInstance().getUserType());
        userTypeLabel.setTextFill(Color.GREEN);

        manageVehicleTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (manageVehicleTab.isSelected()) {
                    try {

                        Parent root = FXMLLoader.load(getClass().getResource("ManageVehicle.fxml"));
                        manageVehiclePane.setCenter(root);
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        findOldVehicleTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (findOldVehicleTab.isSelected()) {
                    try {

                        Parent root = FXMLLoader.load(getClass().getResource("FindOldVehicle.fxml"));
                        fineOldVehiclePane.setCenter(root);
                    } catch (IOException ex) {
                        Logger.getLogger(CustomerOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }
    
    
    
    
     @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent next_page_parent = null;

        next_page_parent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene next_page_scene = new Scene(next_page_parent);

        app_stage.setScene(next_page_scene);
        app_stage.show();

    }

}
