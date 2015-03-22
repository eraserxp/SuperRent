/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AdminModel;
import model.AppContext;

/**
 * FXML Controller class
 *
 */
public class AdminOverviewController extends AbstractController implements Initializable {

    @FXML
    private Label usernameLabel;
    
    @FXML
    private Label userTypeLabel;
    
    @FXML
    private TabPane tabPane;

    /**
     * ** the following control components are for the show tables tab ***
     */
    @FXML
    private Tab showTableTab;

    @FXML
    private BorderPane showTableContent;

    /**
     * *********************************************************************
     */
    /**
     * ** the following control components are for the manageAccount tab ***
     */
    @FXML
    private Tab manageAccountTab;

    @FXML
    private BorderPane manageAccountContent;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usernameLabel.setText(AppContext.getInstance().getUsername());
        usernameLabel.setTextFill(Color.GREEN);
        userTypeLabel.setText(AppContext.getInstance().getUserType());
        userTypeLabel.setTextFill(Color.GREEN);
        
        // when the getTable tab is selected, show the tables
        showTableTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (showTableTab.isSelected()) {
                    try {
                        //setUpShowTablesTab();
                        Parent root = FXMLLoader.load(getClass().getResource("ShowTablesView.fxml"));
                        showTableContent.setCenter(root);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        manageAccountTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (manageAccountTab.isSelected()) {
                    try {
                        //setUpShowTablesTab();
                        Parent root = FXMLLoader.load(getClass().getResource("AddRemoveUserView.fxml"));
                        
                        manageAccountContent.setCenter(root);
                    } catch (IOException ex) {
                        Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // select the showTable tab
        tabPane.getSelectionModel().select(manageAccountTab);
//        tabPane.getSelectionModel().select(showTableTab);
    }

}
