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
    private Tab cancelreservetab;
    
    
    @FXML
    private BorderPane makeReservationPane;
    
    @FXML
    private BorderPane membershipPane;
    
     @FXML
    private BorderPane updateProfilePane;
     
      @FXML
    private BorderPane cancelreservePane;
    
    @FXML
    private Label usernameLabel;

    @FXML
    private Label userTypeLabel;

   @FXML
    private Button logoutButton;
   
   String user_type,user_name;
    

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
                                        root = FXMLLoader.load(getClass().getResource("ReserveRentView.fxml"));
                                        makeReservationPane.setCenter(root);
                                    } else if (newTab == membershiptab) {
                                         AppContext.getInstance().setUsername(user_name);
                                         AppContext.getInstance().setUserType(user_type);
                                         //System.out.println("Username in customer"+user_name);
                                        root = FXMLLoader.load(getClass().getResource("Membership.fxml"));
                                        membershipPane.setCenter(root);
                                    } else if (newTab == updateProfiletab) {
                                        AppContext.getInstance().setUsername(user_name);
                                         AppContext.getInstance().setUserType(user_type);
                                        root = FXMLLoader.load(getClass().getResource("UpdateProfileView.fxml"));
                                        updateProfilePane.setCenter(root);
                                    }
                                    else if (newTab == cancelreservetab) {
                                        AppContext.getInstance().setUsername(user_name);
                                         AppContext.getInstance().setUserType(user_type);
                                        root = FXMLLoader.load(getClass().getResource("CancelReserveView.fxml"));
                                        cancelreservePane.setCenter(root);
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(CustomerOverviewController.class.getName()).log(Level.SEVERE, null, ex);
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
