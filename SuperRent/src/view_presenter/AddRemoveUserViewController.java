/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.AdminModel;

/**
 * FXML Controller class
 *
 * @author eraserxp
 */
public class AddRemoveUserViewController extends AbstractController implements Initializable {

    @FXML
    private Label addInfoLabel;

    private boolean nameOK = false;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameValidator;

    @FXML
    private ComboBox<String> userTypeCMB;

    @FXML
    private Label typeValidator;

    private boolean typeOK = false;

    @FXML
    private ComboBox<String> branchCMB;

    @FXML
    private Label branchValidator;

    private boolean branchOK = false;

    private String user_type_add;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private Label phoneValidator;
    
    
    private boolean phoneOK = false;
    
    @FXML
    private TextField addressField;
    
    @FXML
    private Label addressValidator;
    
    private boolean addressOK = false;

    @FXML
    private TextField usernameField_add;

    @FXML
    private Label usernameValidator;

    private boolean usernameOK = false;

    @FXML
    private PasswordField passwdField_add;

    @FXML
    private PasswordField repasswdField_add;

    @FXML
    private Label passwdValidator;

    private boolean passwdOK = false;

    @FXML
    private Button addButton;
    
    @FXML
    private Label removeInfoLabel;

    @FXML
    private TextField usernameField_remove;

    @FXML
    private Label usernameValidator_r;

    @FXML
    private Button removeButton;

    /**
     * *********************************************************************
     */
    private AdminModel adminModel = new AdminModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUpManageAccountTab();
    }

    private void setUpManageAccountTab() {

        //set all validator labels to be invisible
        hide(addInfoLabel, nameValidator, typeValidator, branchValidator,
                usernameValidator, passwdValidator, usernameValidator_r
               );

        //clear the text fields
        clearText(nameField, usernameField_add, passwdField_add,
                repasswdField_add, usernameField_remove);
        //disable the three buttons
//      disableNodes(addButton, removeButton, changeButton);
        setUpNameField();

        //set up the combobox for user type
        setUpComobox_userType();

        setUpComobox_branch();

        setUpUsernameField_add();

        setUpPasswordField(passwdField_add, repasswdField_add, passwdValidator);

        setUpUsernameField_remove();

    }

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

    private void setUpComobox_userType() {
        ArrayList<String> user_types = new ArrayList<>();
        user_types.add("Customer");
        user_types.add("Clerk");
        user_types.add("Manager");
        configureComboBox(userTypeCMB, user_types);

        userTypeCMB.setOnAction((ActionEvent event) -> {
            user_type_add = userTypeCMB.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + user_type_add + ")");
            typeOK = true;
            hide(typeValidator);
            // only enable the branch combobox if user type is clerk
            if (user_type_add.toLowerCase().equals("clerk")) {
                branchCMB.setDisable(false);
            } else {
                hide(branchValidator);
                clearAndDisable(branchCMB);
            }

        });
    }

    private void setUpComobox_branch() {
        ArrayList<String> branches = adminModel.getAllBranches();
        configureComboBox(branchCMB, branches);

        branchCMB.setOnAction((ActionEvent event) -> {
            branchOK = true;
            hide(branchValidator);
            String branch = branchCMB.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + branch + ")");
        });
        //disable this combobox initially
        branchCMB.setDisable(true);
    }

    private void setUpUsernameField_add() {
        usernameField_add.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // check the input when the focus on this text field is lost
            if (!newValue) {
                //validate the username
                if (isInputEmpty(usernameField_add)) {
                    showWarning(usernameValidator, "Username can't be empty!");
                    usernameOK = false;
                } else if (adminModel.isUsernameExisted(usernameField_add.getText().trim())) {
                    showWarning(usernameValidator, "Username is existed!");
                    usernameOK = false;
                } else {
                    usernameOK = true;
                }
            }

            //when user is entering something into the field, remove any previous validation message
            if (newValue) {
                hide(usernameValidator);
            }
        });
    }

    private void setUpPasswordField(PasswordField passwordField, PasswordField repasswordField, Label infoLabel) {

        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // when user enter some text and leave the password field, check the password
            if (!newValue) {
                if (checkTwoPasswdFields(passwordField, repasswordField, passwdValidator) == true) {
                    passwdOK = true;
                } else {
                    passwdOK = false;
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
                    passwdOK = true;
                } else {
                    passwdOK = false;
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

    public void handleAddButton() {
        //use request focus to let each textfield to be checked again
        nameField.requestFocus();
        usernameField_add.requestFocus();
        repasswdField_add.requestFocus();
        addButton.requestFocus();
        String type;

        if (userTypeCMB.getSelectionModel().isEmpty()) {
            showWarning(typeValidator, "Type must be selected!");
            typeOK = false;
            return;
        }
//        else {
//            type = userTypeCMB.getSelectionModel().getSelectedItem().toLowerCase();
//            if (type.equals("clerk")) {
//
//                if (branchCMB.getSelectionModel().isEmpty()) {
//                    showWarning(branchValidator, "Branch must be selected!");
//                    branchOK = false;
//                    return;
//                }
//            }
//        }

        if (nameOK && usernameOK && passwdOK && typeOK) {

            String username = usernameField_add.getText().trim();
            String passwd = passwdField_add.getText().trim();
            String name = nameField.getText().trim();

            boolean addOK = false;
            type = userTypeCMB.getSelectionModel().getSelectedItem().toLowerCase();
            if (type.equals("clerk")) {

                if (branchCMB.getSelectionModel().isEmpty()) {
                    showWarning(branchValidator, "Type must be selected!");
                    branchOK = false;
                    return;
                }

                String branch = branchCMB.getSelectionModel().getSelectedItem();
                String location = branch.split(",")[0].trim();
                String city = branch.split(",")[1].trim();
                addOK = adminModel.addClerk(username, passwd, name, type, city, location);
            } else {
                addOK = adminModel.addUser(username, passwd, name, type);
            }

            if (addOK) {
                clearText(nameField, usernameField_add, passwdField_add, repasswdField_add);
                hide(nameValidator, typeValidator, branchValidator, usernameValidator, passwdValidator);
                showSuccessMessage(addInfoLabel, "An user has been added.");
            }
        }
    }

    private void setUpUsernameField_remove() {

    }
}
