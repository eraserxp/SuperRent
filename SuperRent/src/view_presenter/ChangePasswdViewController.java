/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.AdminModel;
import model.ValidationResult;

/**
 * FXML Controller class
 *
 *
 */
public class ChangePasswdViewController extends AbstractController implements Initializable {

    @FXML
    private Label changeInfoLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameValidatorLabel;

    private boolean usernameOK = false;

    @FXML
    private PasswordField passwdField;

    @FXML
    private PasswordField repasswdField;

    @FXML
    private Label passwdValidatorLabel;

    private ValidationResult passwdValidation = new ValidationResult();

    @FXML
    private Button changeButton;

    private AdminModel adminModel = new AdminModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUp();
    }

    private void setUp() {
        hide(changeInfoLabel, usernameValidatorLabel, passwdValidatorLabel);
        clearText(usernameField, passwdField, repasswdField);
        setUpUsernameField();
        setUpPasswordField(passwdField, repasswdField, passwdValidatorLabel, passwdValidation);
    }

    private void setUpUsernameField() {
        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // check the input when the focus on this text field is lost
            if (!newValue) {
                //validate the username
                if (isInputEmpty(usernameField)) {
                    showWarning(usernameValidatorLabel, "Username can't be empty!");
                    usernameOK = false;
                } else if (!adminModel.isUsernameExisted(usernameField.getText().trim())) {
                    showWarning(usernameValidatorLabel, "Username doesn't exist!");
                    usernameOK = false;
                } else {
                    usernameOK = true;
                }
            }

            //when user is entering something into the field, remove any previous validation message
            if (newValue) {
                hide(usernameValidatorLabel);
                usernameOK = false;
            }
        });
    }

    
    public void handleChangeButton() {
        System.out.println("inside handle change button ");
        usernameField.requestFocus();
        passwdField.requestFocus();
        repasswdField.requestFocus();
        changeButton.requestFocus();
        String username = "";

        boolean readyToChange = usernameOK && passwdValidation.get();
        System.out.println("usernameOK = " + usernameOK);
        System.out.println("passwordOK = " + passwdValidation.get());
        boolean changeOK = false;
        if (readyToChange) {
            username = usernameField.getText().trim();
            String passwd = repasswdField.getText().trim();
            changeOK = adminModel.changePasswd(username, passwd);
        }

        if (changeOK) {
            showSuccessMessage(changeInfoLabel, "The password for " + username 
                               + " has been updated.");
            hide(usernameValidatorLabel, passwdValidatorLabel);
            clearText(usernameField, passwdField, repasswdField);
        }
    }
}
