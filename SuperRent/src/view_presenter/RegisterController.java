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
public class RegisterController implements Initializable {

    @FXML
    private Button register_button;
    
    @FXML
    private Button login_button;
    
    @FXML
    private TextField username_field;

    @FXML
    private TextField password_field;
    
    @FXML
    private TextField repassword_field;
    
    @FXML
    private TextField name_field;
    
    @FXML
    private TextField phone_field;
    
    @FXML
    private TextField address_field;
    
    @FXML
    private void handleloginButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        app_stage.setScene(scene);
        app_stage.show();
             
    } 
    
    @FXML
    private void handleregisterButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        
                    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
