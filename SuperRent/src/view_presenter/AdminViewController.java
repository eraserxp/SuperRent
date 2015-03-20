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

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> userTypeCMB;

    @FXML
    private ComboBox<String> branchCMB;

    private String user_type_add;

    @FXML
    private TextField usernameField_add;

    @FXML
    private PasswordField passwdField_add;

    @FXML
    private PasswordField repasswdField_add;

    @FXML
    private Button addButton;

    @FXML
    private Label removeInfoLabel;

    @FXML
    private TextField usernameField_remove;

    @FXML
    private Button removeButton;

    @FXML
    private Label changeInfoLabel;

    @FXML
    private TextField usernameField_change;

    @FXML
    private PasswordField passwdField_change;

    @FXML
    private PasswordField repasswdField_change;

    @FXML
    private Button changeButton;
    /**
     * *********************************************************************
     */

    private AdminModel adminModel = new AdminModel();

    private Stage myStage;

    public void setStage(Stage stage) {
        myStage = stage;
    }

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
//        tabPane.getSelectionModel().select(showTableTab);

        setUpNameField();

        //set up the combobox for user type
        setUpComobox_userType();

        setUpComobox_branch();

        setUpUsernameField_add();

        setUpPasswordField(passwdField_add, repasswdField_add, addInfoLabel, addButton);

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

    private void setUpNameField() {
        nameField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // when user enter some text and leave the password field, check the password
                if (!newValue) {
                    if (isInputEmpty(nameField)) {
                        showWarning(addInfoLabel, "Name can't be empty!");
                        userTypeCMB.setDisable(true);
                    } else {
                        userTypeCMB.setDisable(false);
                    }
                }

                //when user is entering something into the namefield, disable all other control component 
                // and remove the warnings
                if (newValue) {
                    addInfoLabel.setText("");
                    addInfoLabel.setVisible(false);

                    userTypeCMB.setDisable(true);
                    branchCMB.setDisable(true);
                    usernameField_add.setDisable(true);
                    passwdField_add.setDisable(true);
                    repasswdField_add.setDisable(true);
                    addButton.setDisable(true);
                }
            }

        });
    }

    private void setUpManageAccountTab() {

        //set the info Labels to be invisible
        addInfoLabel.setVisible(false);
        removeInfoLabel.setVisible(false);
        changeInfoLabel.setVisible(false);

        //clear the text fields
        usernameField_add.clear();
        passwdField_add.clear();
        repasswdField_add.clear();

        //disable some text fields and buttons
        userTypeCMB.setDisable(true);
        usernameField_add.setDisable(true);
        passwdField_add.setDisable(true);
        repasswdField_add.setDisable(true);
        addButton.setDisable(true);

        usernameField_remove.clear();
        removeButton.setDisable(true);

        usernameField_change.clear();
        passwdField_change.clear();
        repasswdField_change.clear();

        passwdField_change.setDisable(true);
        repasswdField_change.setDisable(true);
        changeButton.setDisable(true);

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

    private void setUpComobox_userType() {
        ArrayList<String> user_types = new ArrayList<>();
        user_types.add("Customer");
        user_types.add("Clerk");
        user_types.add("Manager");
        configureComboBox(userTypeCMB, user_types);

        userTypeCMB.setOnAction((ActionEvent event) -> {
            user_type_add = userTypeCMB.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + user_type_add + ")");
            if (user_type_add.toLowerCase().equals("clerk")) {
                branchCMB.setDisable(false);
            } else {
                branchCMB.valueProperty().set(null);
                branchCMB.setDisable(true);
                usernameField_add.setDisable(false);
            }

        });
    }

    private void setUpComobox_branch() {
        ArrayList<String> branches = adminModel.getAllBranches();
        configureComboBox(branchCMB, branches);

        branchCMB.setOnAction((ActionEvent event) -> {
            String branch = branchCMB.getSelectionModel().getSelectedItem();
            System.out.println("ComboBox Action (selected: " + branch + ")");
            //enable the username field
            usernameField_add.setDisable(false);
        });
        branchCMB.setDisable(true);
    }

    private void setUpUsernameField_add() {
        usernameField_add.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // check the input when the focus on this text field is lost
                if (!newValue) {
                    //validate the username
                    if (usernameField_add.getText() == null || usernameField_add.getText().length() == 0) {
                        showWarning(addInfoLabel, "Username can't be empty!");
                        passwdField_add.setDisable(true);
                        repasswdField_add.setDisable(true);
                        addButton.setDisable(true);
                    } else if (adminModel.isUsernameExisted(usernameField_add.getText())) {
                        showWarning(addInfoLabel, "Username is already existed!");
                        passwdField_add.setDisable(true);
                        repasswdField_add.setDisable(true);
                        addButton.setDisable(true);
                    } else {
                        //if username is OK, enable the passwd field
                        passwdField_add.setDisable(false);
                        repasswdField_add.setDisable(false);
                    }
                }

                //when user is entering something into the field, remove any previous warnings
                if (newValue) {
                    addInfoLabel.setText("");
                    addInfoLabel.setVisible(false);
                }

            }

        });
    }

    private void setUpPasswordField(PasswordField passwordField, PasswordField repasswordField, Label infoLabel, Button button) {

        passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // when user enter some text and leave the password field, check the password
                if (!newValue) {
                    if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                        button.setDisable(false);
                    } else {
                        button.setDisable(true);
                    }
                }

                //when user is entering something into the password field, disable the button 
                // and remove the warnings
                if (newValue) {
                    infoLabel.setText("");
                    infoLabel.setVisible(false);
                    button.setDisable(true);
                }
            }

        });

        repasswordField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (checkTwoPasswdFields(passwordField, repasswordField, infoLabel) == true) {
                        button.setDisable(false);
                    } else {
                        button.setDisable(true);
                    }
                }
                //when user is entering something into the password field, disable the button 
                // and remove the warnings
                if (newValue) {
                    infoLabel.setText("");
                    infoLabel.setVisible(false);
                    button.setDisable(true);
                }

            }

        });

    }

    /**
     * checks if the text in the two password fields is a valid password and
     * prints out the corresponding warning in infoLabel
     *
     * @param passwordField
     * @param repasswordField
     * @param infoLabel
     * @return
     */
    private boolean checkTwoPasswdFields(PasswordField passwordField, PasswordField repasswordField, Label infoLabel) {
        boolean passwordValid = false;
        if (isInputEmpty(passwordField)) {
            showWarning(infoLabel, "Password can't be empty!");
        } else if (isInputTooLong(passwordField, 20)) {
            showWarning(infoLabel, "Password is too long!");
        } else if (!areTwoInputsMatch(passwordField, repasswordField)) {
            showWarning(infoLabel, "Password doesn't match!");
        } else {
            passwordValid = true;
        }
        return passwordValid;
    }

    public void handleAddButton() {
        String username = usernameField_add.getText().trim();
        String passwd = passwdField_add.getText().trim();
        String name = nameField.getText().trim();
        String type = userTypeCMB.getSelectionModel().getSelectedItem().toLowerCase();
        boolean addOK = false;
        
        if (type.equals("clerk")) {
            String branch = branchCMB.getSelectionModel().getSelectedItem();
            String location = branch.split(",")[0].trim();
            String city = branch.split(",")[1].trim();
//            System.out.println(city);
//            System.out.println(location);
            addOK = adminModel.addClerk(username, passwd, name, type, city, location);
        } else {
            addOK = adminModel.addUser(username, passwd, name, type);
        }
        
        if ( addOK ) {
            usernameField_add.clear();
            passwdField_add.clear();
            repasswdField_add.clear();
            nameField.clear();

            usernameField_add.setDisable(true);
            userTypeCMB.setDisable(true);
            passwdField_add.setDisable(true);
            repasswdField_add.setDisable(true);
            addButton.setDisable(true);
        }
    }
}
