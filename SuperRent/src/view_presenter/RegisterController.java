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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 *
 */
public class RegisterController extends AbstractController implements Initializable {

    @FXML
    private Button register_button;
    
    @FXML
    private Button login_button;
    
    @FXML
    private TextField username_field;

    @FXML
    private PasswordField password_field;
    
    @FXML
    private PasswordField repassword_field;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField addressField;
    
     @FXML
    private Label nameValidator;
     
    private boolean nameOK = false;
    
      @FXML
    private Label addressValidator;
     
    private boolean addOK = false;
    
     @FXML
    private Label phoneValidator;
     
    private boolean phoneOK = false;
    
     @FXML
    private Label passwordValidator;
     
    private boolean passOK = false;
    
    private void setUpNameField() {
        nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(nameField)) {
                    showWarning(nameValidator, "Name can't be empty!");
                    nameOK = false;
                } else {
                    nameOK = true;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(nameValidator);
            }
        });
    }
    
    private void setUpAddressField() {
        addressField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(addressField)) {
                    showWarning(addressValidator, "Address can't be empty!");
                    addOK = false;
                } else {
                    addOK = true;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(addressValidator);
            }
        });
    }
    
    private void setUpPhoneField() {
        addressField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (isInputEmpty(phoneField)) {
                    showWarning(phoneValidator, "Phone can't be empty!");
                    phoneOK = false;
                } else {
                    if(isInputLength(phoneField,10))
                    phoneOK = true;
                    else 
                    phoneOK=false;
                }
            }

            //when user is entering something into the namefield, remove the validation information
            if (newValue) {
                hide(phoneValidator);
            }
        });
    }
    private void setUpPasswordField(PasswordField passwordField, PasswordField repasswordField, Label infoLabel) {

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, passwordValidator) == true) {
                    passOK = true;
                } else {
                    passOK = false;
                }
            }

            //when user is entering something into the password field, clearAndDisable the button
            // and remove the warnings
            if (newValue) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
            }
        });

        repasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                    passOK = true;
                } else {
                    passOK = false;
                }
            }
            //when user is entering something into the password field, clearAndDisable the button
            // and remove the warnings
            if (newValue) {
                infoLabel.setText("");
                infoLabel.setVisible(false);
            }
        });

    }
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
