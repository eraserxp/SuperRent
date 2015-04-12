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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AppContext;
import model.ClerkModel;

/**
 * FXML Controller class
 *
 *
 */
public class ClerkOverviewController extends AbstractController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //ClerkViewControllers
    @FXML
    private Button logoutButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userTypeLabel;

    //the three tabs in the view
    @FXML
    private Tab SearchRentTab;
    @FXML
    private Tab ReturnTab;
    
    @FXML
    private Tab queryTab;
    //three corresponeding border panes to hold the contents of the three tab view
    @FXML
    private BorderPane SearchRentContents;
    @FXML
    private BorderPane ReturnContents;

    @FXML
    private BorderPane queryContents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        usernameLabel.setText(AppContext.getInstance().getUsername());
        usernameLabel.setTextFill(Color.GREEN);
        userTypeLabel.setText(AppContext.getInstance().getUserType());
        userTypeLabel.setTextFill(Color.GREEN);

        //the following try-catch block swith from different tabs to others
        try {
            //when the check reservatoin tab is selected, show the contents of the tab

            Parent root = FXMLLoader.load(getClass().getResource("ReserveRentView.fxml"));
            SearchRentContents.setCenter(root);

            SearchRentTab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (SearchRentTab.isSelected()) {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("ReserveRentView.fxml"));
                            SearchRentContents.setCenter(root);
                        } catch (IOException ex) {
                            Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            ReturnTab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (ReturnTab.isSelected()) {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("ReturnView.fxml"));
                            ReturnContents.setCenter(root);
                        } catch (IOException ex) {
                            Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

//        show the state that the tab is selected
//        ClerkTabPane.getSelectionModel().select(CheckReservationTab);
            queryTab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (queryTab.isSelected()) {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("QueryVehicles.fxml"));
                            queryContents.setCenter(root);
                        } catch (IOException ex) {
                            Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(ClerkOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }//the end of the try-catch of the view switch//the end of the try-catch of the view switch

    }   //the end of the initialize 

    //ClerkViewController
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
