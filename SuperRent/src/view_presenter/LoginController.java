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
import model.AppContext;
import model.LoginModel;

/**
 *
 *
 */
public class LoginController implements Initializable {

    String user_type;
    
 
    @FXML
    private Button register_button;
    
    @FXML
    private Button login_button;
    
    @FXML
    private Label label;

    @FXML
    private Label invalid_label;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;
    
     String username,password,type;
     
     private LoginModel loginModel = new LoginModel();
     
     boolean valid;
    
    @FXML
    private void handleloginButtonAction(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent next_page_parent = null;
        
              
        username=usernameField.getText();
        password=passwordField.getText();
        
        //System.out.println("Username"+username);
        //System.out.println("Password"+password);
        
        valid=loginModel.isValidCredentials(username,password);
        System.out.println("Valid="+valid);
        
        
                    
       
              
        
        if (valid==true) 
        {
            type=loginModel.getUserCredentials(username,password);          
            user_type=type.toUpperCase();
            // switch to different pages according to the type of the user
            switch (user_type) {
                case "CUSTOMER":
                  next_page_parent = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
                    break;
                case "CLERK":
                    // set the enxt page to be the page for clerk
                    break;
                case "MANAGER":
                    // set the enxt page to be the page for manager
                    next_page_parent = FXMLLoader.load(getClass().getResource("ManagerView.fxml"));
                    break;
                case "ADMIN":
                    // set the enxt page to be the page for Aadministrator
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
//                    AdminViewController controller = loader.getController();
//                    controller.setStage(app_stage);
//                    next_page_parent = loader.load();
                    
                    AppContext.getInstance().setUsername(username);
                    AppContext.getInstance().setUserType(user_type);
                    next_page_parent = FXMLLoader.load(getClass().getResource("AdminOverview.fxml"));
                    break;
            }

            Scene next_page_scene = new Scene(next_page_parent);

            app_stage.setScene(next_page_scene);
            app_stage.show();
        } 
        
        else
        {
            Parent root = FXMLLoader.load(getClass().getResource("Invalid.fxml"));
            Scene scene = new Scene(root);
            app_stage.setScene(scene);
            app_stage.show();
        }
    }

    @FXML
    private void handleregisterButtonAction(ActionEvent event) throws IOException {
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
