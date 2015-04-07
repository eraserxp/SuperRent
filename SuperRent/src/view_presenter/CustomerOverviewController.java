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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AppContext;




public class CustomerOverviewController  extends AbstractController implements Initializable {
    
     @FXML
    private TabPane tabPane;
    
    
    @FXML
    private Tab makeReseravtiontab;

    @FXML
    private Tab updateProfiletab;
    
     @FXML
    private Tab membershiptab;
    
    
    @FXML
    private BorderPane makeReservationPane;
    
    @FXML
    private BorderPane membershipPane;
    
     @FXML
    private BorderPane updateProfilePane;
    
    @FXML
    private Label usernameLabel;

    @FXML
    private Label userTypeLabel;

   @FXML
    private Button logoutButton;
   
   String user_type,user_name;
    
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
        
        usernameLabel.setText(AppContext.getInstance().getUsername());
        usernameLabel.setTextFill(Color.GREEN);
        userTypeLabel.setText(AppContext.getInstance().getUserType());
        userTypeLabel.setTextFill(Color.GREEN);
        
        user_type=AppContext.getInstance().getUserType();
        user_name=AppContext.getInstance().getUsername();
           
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
                                // Process event here...
                                // find out which tab is selected and load the corresponding fxml
                                Parent root;
                                try {
                                    if (newTab == makeReseravtiontab ) {
                                        root = FXMLLoader.load(getClass().getResource("AddRemoveUserView.fxml"));
                                        makeReservationPane.setCenter(root);
                                    } else if (newTab == membershiptab) {
                                         AppContext.getInstance().setUserType(user_type);
                                         AppContext.getInstance().setUsername(user_name);
                                        root = FXMLLoader.load(getClass().getResource("MembershipView.fxml"));
                                        membershipPane.setCenter(root);
                                    } else if (newTab == updateProfiletab) {
                                        root = FXMLLoader.load(getClass().getResource("ChangePasswdView.fxml"));
                                        updateProfilePane.setCenter(root);
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                });
        tabPane.getSelectionModel().select(updateProfiletab);
       
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
