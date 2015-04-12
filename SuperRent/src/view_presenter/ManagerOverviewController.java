/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AppContext;
import model.ManagerModel;

/**
 * FXML Controller class
 *
 *
 */
public class ManagerOverviewController extends AbstractController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label userTypeLabel;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab manageVehicleTab;
    
     @FXML
    private Tab reportsTab;

    @FXML
    private Tab findOldVehicleTab;


    @FXML
    private BorderPane manageVehiclePane;

    @FXML
    private BorderPane fineOldVehiclePane;
    
    @FXML
    private BorderPane reportsPane;

    @FXML
    private Button logoutButton;

    @FXML
    private ComboBox<String> typeCombobox;
    @FXML
    private TextField weeklyRateTextField;

    @FXML
    private TextField dailyRateTextField;
    @FXML
    private TextField hourlyRateTextField;

    @FXML
    private TextField mileLimitTextField;
    @FXML
    private TextField PkRateTextField;
    @FXML
    private TextField weeklyInsuranceTextField;
    @FXML
    private TextField dailyInsuranceTextField;
    @FXML
    private TextField hourlyInsuranceTextField;

    @FXML
    private Button updateButton;

    @FXML
    private Label validationLabel;

    private ManagerModel managerModel = new ManagerModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usernameLabel.setText(AppContext.getInstance().getUsername());
        usernameLabel.setTextFill(Color.GREEN);
        userTypeLabel.setText(AppContext.getInstance().getUserType());
        userTypeLabel.setTextFill(Color.GREEN);

        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
                                // Process event here...
                                // find out which tab is selected and load the corresponding fxml
                                Parent root;
                                try {
                                    if (newTab == manageVehicleTab) {
                                        root = FXMLLoader.load(getClass().getResource("ManageVehicle.fxml"));
                                        manageVehiclePane.setCenter(root);
                                    } else if (newTab == findOldVehicleTab) {
                                        root = FXMLLoader.load(getClass().getResource("FindOldVehicle.fxml"));
                                        fineOldVehiclePane.setCenter(root);
                                    
                                    } else if (newTab == reportsTab) {
                                        root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
                                        reportsPane.setCenter(root);
                                    }
                                    
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(AdminOverviewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                });

        tabPane.getSelectionModel().select(findOldVehicleTab);

        setUpTypeCM();

        // Listen for brandTextFiled text changes
        weeklyRateTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!isInputEmpty(weeklyRateTextField)) {
                    if (!isInputInteger(weeklyRateTextField)) {
                        showWarning(validationLabel, "The Weekly Rate is Wrong!");
                        validationLabel.setTextFill(Color.RED);
                        weeklyRateTextField.requestFocus();
                        return;
                    } else {
                        showWarning(validationLabel, "");
                    }
                }
            }
        });

        // Listen for brandTextFiled text changes
        dailyRateTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!isInputEmpty(dailyRateTextField)) {
                    if (!isInputInteger(dailyRateTextField)) {
                        showWarning(validationLabel, "The Daily Rate is Wrong!");
                        validationLabel.setTextFill(Color.RED);
                        dailyRateTextField.requestFocus();
                        return;
                    } else {
                        showWarning(validationLabel, "");

                    }
                }
            }
        });

        // Listen for brandTextFiled text changes
        hourlyRateTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!isInputEmpty(hourlyRateTextField)) {
                    if (!isInputInteger(hourlyRateTextField)) {
                        showWarning(validationLabel, "The Hourly Rate is Wrong!");
                        validationLabel.setTextFill(Color.RED);
                        hourlyRateTextField.requestFocus();
                        return;
                    } else {
                        showWarning(validationLabel, "");

                    }
                }
            }
        });
        
           // Listen for brandTextFiled text changes
        PkRateTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
               
           if (!isInputEmpty(PkRateTextField)) {
            if (!isInputInteger(PkRateTextField)) {
                showWarning(validationLabel, "The PK Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                PkRateTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }
        }
        });
        
        
        
            // Listen for brandTextFiled text changes
        weeklyInsuranceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
               
         
          if (!isInputEmpty(weeklyInsuranceTextField)) {
            if (!isInputInteger(weeklyInsuranceTextField)) {
                showWarning(validationLabel, "The Weekly Insurance Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                weeklyInsuranceTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

                    }
                }
            }
        });
        
        
            // Listen for brandTextFiled text changes
        dailyInsuranceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
           if (!isInputEmpty(dailyInsuranceTextField)) {
            if (!isInputInteger(dailyInsuranceTextField)) {
                showWarning(validationLabel, "The Daily Insurance Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                dailyInsuranceTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }
            }
        });
            // Listen for brandTextFiled text changes
        hourlyInsuranceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
           if (!isInputEmpty(hourlyInsuranceTextField)) {
            if (!isInputInteger(hourlyInsuranceTextField)) {
                showWarning(validationLabel, "The Hourly Insurance Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                hourlyInsuranceTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }
            }
        });
           // Listen for brandTextFiled text changes
        mileLimitTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
           if (!isInputEmpty(mileLimitTextField)) {
            if (!isInputInteger(mileLimitTextField)) {
                showWarning(validationLabel, "The Mile Limit is Wrong!");
                validationLabel.setTextFill(Color.RED);
                mileLimitTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }
            }
        });
        
        
        
        
        
        
        
        
        
        
        
       

    }

    private void setUpTypeCM() {
        configureComboBox(typeCombobox,
                managerModel.getVehicleType());

        typeCombobox.setOnAction((ActionEvent event) -> {

            validationLabel.setVisible(false);

        });

    }

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        // get the stage for the application
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        Parent next_page_parent = null;

        next_page_parent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene next_page_scene = new Scene(next_page_parent);

        app_stage.setScene(next_page_scene);
        app_stage.show();

    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException, SQLException {

        if (typeCombobox.getSelectionModel().isEmpty()) {
            showWarning(validationLabel, "Please select the Type!");
            validationLabel.setTextFill(Color.RED);
            return;
        } else {

            showWarning(validationLabel, "");

        }

        if (!isInputEmpty(weeklyRateTextField)) {
            if (!isInputInteger(weeklyRateTextField)) {
                showWarning(validationLabel, "The Weekly Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                weeklyRateTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");
            }
        }

        if (!isInputEmpty(dailyRateTextField)) {
            if (!isInputInteger(dailyRateTextField)) {
                showWarning(validationLabel, "The Daily Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                dailyRateTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }

        if (!isInputEmpty(hourlyRateTextField)) {
            if (!isInputInteger(hourlyRateTextField)) {
                showWarning(validationLabel, "The Hourly Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                hourlyRateTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }

        if (!isInputEmpty(PkRateTextField)) {
            if (!isInputInteger(PkRateTextField)) {
                showWarning(validationLabel, "The PK Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                PkRateTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }

        if (!isInputEmpty(weeklyInsuranceTextField)) {
            if (!isInputInteger(weeklyInsuranceTextField)) {
                showWarning(validationLabel, "The Weekly Insurance Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                weeklyInsuranceTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }

        if (!isInputEmpty(dailyInsuranceTextField)) {
            if (!isInputInteger(dailyInsuranceTextField)) {
                showWarning(validationLabel, "The Daily Insurance Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                dailyInsuranceTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }
        if (!isInputEmpty(hourlyInsuranceTextField)) {
            if (!isInputInteger(hourlyInsuranceTextField)) {
                showWarning(validationLabel, "The Hourly Insurance Rate is Wrong!");
                validationLabel.setTextFill(Color.RED);
                hourlyInsuranceTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }

        if (!isInputEmpty(mileLimitTextField)) {
            if (!isInputInteger(mileLimitTextField)) {
                showWarning(validationLabel, "The Mile Limit is Wrong!");
                validationLabel.setTextFill(Color.RED);
                mileLimitTextField.requestFocus();
                return;
            } else {
                showWarning(validationLabel, "");

            }
        }

        if (isInputEmpty(mileLimitTextField) && isInputEmpty(hourlyInsuranceTextField) && isInputEmpty(dailyInsuranceTextField) && isInputEmpty(weeklyInsuranceTextField) && isInputEmpty(PkRateTextField) && isInputEmpty(hourlyRateTextField) && isInputEmpty(dailyRateTextField) && isInputEmpty(weeklyRateTextField)) {

            showWarning(validationLabel, "Please Enter The Amount!");
            validationLabel.setTextFill(Color.RED);

            return;

        } else {
            showWarning(validationLabel, "");

        }

        String vehicleType = typeCombobox.getSelectionModel().getSelectedItem();
        String weeklyRate = weeklyRateTextField.getText();
        String dailylyRate = dailyRateTextField.getText();
        String hourlylyRate = hourlyRateTextField.getText();
        String PkRate = PkRateTextField.getText();
        String weeklyInsurance = weeklyInsuranceTextField.getText();
        String dailyInsurance = dailyInsuranceTextField.getText();
        String hourlyInsurance = hourlyInsuranceTextField.getText();
        String mileLimit = mileLimitTextField.getText();

        boolean updateOk = false;

        updateOk = managerModel.updateVehivleRates(vehicleType, weeklyRate, dailylyRate, hourlylyRate, PkRate, weeklyInsurance, dailyInsurance, hourlyInsurance, mileLimit);
        if (updateOk) {
            showWarning(validationLabel, "Updated!");
            validationLabel.setTextFill(Color.GREEN);

        } else {
            showWarning(validationLabel, "Not Updated!");
            validationLabel.setTextFill(Color.RED);

        }

    }
}
