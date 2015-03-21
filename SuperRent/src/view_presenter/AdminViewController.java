/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AdminModel;

/**
 * FXML Controller class
 *
 */
public class AdminViewController extends AbstractController implements Initializable {

    @FXML
    private TabPane tabPane;

    /**
     * ** the following control components are for the show tables tab ***
     */
    @FXML
    private Tab showTableTab;

    @FXML
    private VBox tableListBox; //in show table tab

    @FXML
    private VBox tableContentBox; // in show table tab

    private ListView tableList = null;

    private TableView tableContent = null;
    /**
     * *********************************************************************
     */

    /**
     * ** the following control components are for the manageAccount tab ***
     */
    @FXML
    private Tab manageAccountTab;

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
    private TextField usernameField_remove;

    @FXML
    private Label usernameValidator_r;

    @FXML
    private Button removeButton;

    @FXML
    private TextField usernameField_change;

    @FXML
    private Label usernameValidator_c;

    @FXML
    private PasswordField passwdField_change;

    @FXML
    private PasswordField repasswdField_change;

    @FXML
    private Label passwdValidator_c;

    @FXML
    private Button changeButton;
    /**
     * *********************************************************************
     */

    private AdminModel adminModel = new AdminModel();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // when the getTable tab is selected, show the tables
        showTableTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (showTableTab.isSelected()) {
                    setUpShowTablesTab();
                }
            }
        });

        manageAccountTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (manageAccountTab.isSelected()) {
                    setUpManageAccountTab();
                }
            }
        });

        // select the showTable tab
        tabPane.getSelectionModel().select(manageAccountTab);
    }

    private void setUpShowTablesTab() {
        if (tableList != null) {
            tableListBox.getChildren().remove(tableList);
        }
        //refesh the database first so that the latest info will be displayed
        adminModel.refeshDatabaseConnection();
        tableList = adminModel.getTableNames();
        tableListBox.getChildren().add(tableList);

        //add listener to each table name
        tableList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                // show the content of the corresponding table
                showTable(newValue);
            }

        }
        );
    }

    private void showTable(String tableName) {
        //remove the content of the last table
        if (tableContent != null) {
            tableContentBox.getChildren().remove(tableContent);
        }

        //refesh the database first so that the latest info will be displayed
        adminModel.refeshDatabaseConnection();
        tableContent = adminModel.getTable(tableName);
        tableContentBox.getChildren().add(tableContent);
    }

    private void setUpManageAccountTab() {

        //set all validator labels to be invisible
        hide(addInfoLabel, nameValidator, typeValidator, branchValidator,
                usernameValidator, passwdValidator, usernameValidator_r,
                usernameValidator_c, passwdValidator_c);

        //clear the text fields
        clearText(nameField, usernameField_add, passwdField_add,
                repasswdField_add, usernameField_remove, usernameField_change,
                passwdField_change, repasswdField_change);

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
