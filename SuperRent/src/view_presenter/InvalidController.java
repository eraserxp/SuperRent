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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
/**
 *
 *
 */
public class InvalidController implements Initializable {

    @FXML
    private Button okay_button;
    
        
    @FXML
    private void handleokayButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent next_page_parent = null;
        
          
        next_page_parent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene next_page_scene = new Scene(next_page_parent);

            app_stage.setScene(next_page_scene);
            app_stage.show();       
           
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    }

    

