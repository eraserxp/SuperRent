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

    private boolean usernameOK_r = false;

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
                phoneValidator, addressValidator,
                usernameValidator, passwdValidator, usernameValidator_r,
                removeInfoLabel
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

        setUpPhoneField();

        setUpAddressField();

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
        //user_types.add("Manager"); //only one manager for all branches
        configureComboBox(userTypeCMB, user_types);

        userTypeCMB.setOnAction((ActionEvent event) -> {
            user_type_add = userTypeCMB.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + user_type_add + ")");
            typeOK = true;
            hide(typeValidator);
            // only enable the branch combobox if user type is clerk
            if (user_type_add.toLowerCase().equals("clerk")) {
                branchCMB.setDisable(false);
                hide(phoneValidator, addressValidator);
                clearText(phoneField, addressField);
                phoneField.setDisable(true);
                addressField.setDisable(true);
            } else {
                hide(branchValidator);
                clearAndDisable(branchCMB);
                clearText(phoneField, addressField);
                phoneField.setDisable(false);
                addressField.setDisable(false);
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

    private void setUpPhoneField() {
        phoneField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // lost focus
            if (!newValue) {
                if (isInputEmpty(phoneField)) {
                    showWarning(phoneValidator, "Phone can't be empty!");
                    phoneOK = false;
                } else if (!isInputPhoneNo(phoneField)) {
                    showWarning(phoneValidator, "Phone number is invalid!");
                    phoneOK = false;
                } else {
                    phoneOK = true;
                }
            }
            // gain focus
            if (newValue) {
                hide(phoneValidator);
            }
        });
        //disable phone field at the beginning
        phoneField.setDisable(true);
    }

    private void setUpAddressField() {
        addressField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // lost focus
            if (!newValue) {
                if (isInputEmpty(addressField)) {
                    showWarning(addressValidator, "Address can't be empty!");
                    addressOK = false;
                } else {
                    addressOK = true;
                }
            }
            // gain focus
            if (newValue) {
                hide(addressValidator);
            }
        });
        //disable address field at the beginning
        addressField.setDisable(true);
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
        String type;
        boolean readyToAdd = false;
        boolean addOK = false;

        //user type must be selected
        if (userTypeCMB.getSelectionModel().isEmpty()) {
            showWarning(typeValidator, "Type must be selected!");
            typeOK = false;
            return;
        } else { // if the user type has been selected
            type = userTypeCMB.getSelectionModel().getSelectedItem().toLowerCase();
            // if the type is clerk
            if (type.equals("clerk")) {
                // check if the branch has been selected
                if (branchCMB.getSelectionModel().isEmpty()) {
                    showWarning(branchValidator, "Branch must be selected!");
                    branchOK = false;
                    return;
                } else {
                    //now the branche has been selected and the user type is clerk
                    //use request focus to let each textfield to be checked 
                    nameField.requestFocus();
                    usernameField_add.requestFocus();
                    repasswdField_add.requestFocus();
                    addButton.requestFocus();
                    readyToAdd = nameOK && usernameOK && passwdOK && typeOK && branchOK;
                    if (readyToAdd) {
                        String username = usernameField_add.getText().trim();
                        String passwd = passwdField_add.getText().trim();
                        String name = nameField.getText().trim();

                        String branch = branchCMB.getSelectionModel().getSelectedItem();
                        String location = branch.split(",")[0].trim();
                        String city = branch.split(",")[1].trim();
                        addOK = adminModel.addClerk(username, passwd, name, type, city, location);
                    }
                }
            }

            if (type.equals("customer")) {
                System.out.println("try to add a customer");
                nameField.requestFocus();
                phoneField.requestFocus();
                addressField.requestFocus();
                usernameField_add.requestFocus();
                repasswdField_add.requestFocus();
                addButton.requestFocus();
                System.out.println("nameOK: " + nameOK);
                System.out.println("usernameOK: " + usernameOK);
                System.out.println("passwdOK: " + passwdOK);
                System.out.println("typeOK: " + typeOK);
                System.out.println("phoneOK: " + phoneOK);
                System.out.println("addressOK: " + addressOK);
                readyToAdd = nameOK && usernameOK && passwdOK && typeOK && phoneOK && addressOK;
                if (readyToAdd) {
                    String username = usernameField_add.getText().trim();
                    String passwd = passwdField_add.getText().trim();
                    String name = nameField.getText().trim();
                    String phone = phoneField.getText().trim();
                    phone = formatPhoneNo(phone);
                    String address = addressField.getText().trim();

                    System.out.println(username);
                    System.out.println(passwd);
                    System.out.println(name);
                    System.out.println(phone);
                    System.out.println(address);

                    addOK = adminModel.addCustomer(username, passwd, name, type, phone, address);
                }
            }

        }

        if (addOK) {
            clearText(nameField, phoneField, addressField, usernameField_add,
                    passwdField_add, repasswdField_add);
            clearAndDisable(branchCMB);
            hide(nameValidator, typeValidator, branchValidator, phoneValidator,
                    addressValidator, usernameValidator, passwdValidator);
            showSuccessMessage(addInfoLabel, "An user has been added.");
        }

    }

    private void setUpUsernameField_remove() {
        usernameField_remove.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // check the input when the focus on this text field is lost
            if (!newValue) {
                //validate the username
                if (isInputEmpty(usernameField_remove)) {
                    showWarning(usernameValidator_r, "Username can't be empty!");
                    usernameOK_r = false;
                } else if (!adminModel.isUsernameExisted(usernameField_remove.getText().trim())) {
                    showWarning(usernameValidator_r, "Username doesn't exist!");
                    usernameOK = false;
                } else {
                    usernameOK_r = true;
                }
            }

            //when user is entering something into the field, remove any previous validation message
            if (newValue) {
                hide(usernameValidator_r);
            }
        });
    }

    public void handleRemoveButton() {
        usernameField_remove.requestFocus();
        boolean removeOK = false;
        if (usernameOK_r) {
            removeOK = adminModel.removeUser(usernameField_remove.getText().trim());
        }

        if (removeOK) {
            clearText(usernameField_remove);
            showSuccessMessage(removeInfoLabel, "An user has been removed.");
        }
    }
}
