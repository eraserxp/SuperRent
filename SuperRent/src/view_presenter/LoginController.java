/**
 *
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 *
 */
public class LoginController implements Initializable {

    public enum UserType {

        CUSTOMER, CLERK, MANAGER, ADMINISTRATOR
    }

    private UserType user_type;
    
 
    @FXML
    private Button register;
    
    @FXML
    private Button login;
    
    @FXML
    private Label label;

    @FXML
    private Label invalid_label;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;
    
      
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent next_page_parent = null;
        
        if (isValidCredentials()) {
            // switch to different pages according to the type of the user
            switch (user_type) {
                case CUSTOMER:
                  next_page_parent = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
                    break;
                case CLERK:
                    // set the enxt page to be the page for clerk
                    break;
                case MANAGER:
                    // set the enxt page to be the page for manager
                    break;
                case ADMINISTRATOR:
                    // set the enxt page to be the page for Aadministrator
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
//                    AdminViewController controller = loader.getController();
//                    controller.setStage(app_stage);
//                    next_page_parent = loader.load();
                    next_page_parent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
                    break;
            }

            Scene next_page_scene = new Scene(next_page_parent);

            app_stage.setScene(next_page_scene);
            app_stage.show();
        } else {
            usernameField.clear();
            passwordField.clear();
            invalid_label.setText("Sorry, invalid credentials");
        }
    }

    /**
     * check if the username and password are valid. Return true if it is valid
     * and set the type of the logged user
     *
     * @return
     */
    private boolean isValidCredentials() {
        user_type = UserType.ADMINISTRATOR;
        return true;
    }

    @FXML
    private void handleButtonAction1(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
               
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene scene = new Scene(root);
        app_stage.setScene(scene);
        app_stage.show();
        
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
