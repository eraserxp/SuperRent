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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import model.ManagerModel;
import model.UserModel;

/**
 * FXML Controller class
 *
 * @author NakMac
 */
public class ReportsController extends AbstractController implements Initializable {

    @FXML
    private VBox oldVehicleVbox; // in show table tab
    private TableView tableContent;

    @FXML
    private Label priceValidationLabel;

    @FXML
    private Button printButton;

    @FXML
    private RadioButton carRadioButton;

    @FXML
    private RadioButton truckRadioButton;

    @FXML
    private CheckBox sortByCategoryCB;
    @FXML
    private CheckBox sortByLocationCB;

    @FXML
    private ComboBox<String> locationCMB;

    @FXML
    private Label truckLabel;

    @FXML
    private Label carLabel;

    @FXML
    private Label totalNumberLabel;

    @FXML
    private Label truckAmountLabel;
    @FXML
    private Label carAmountLabel;
    @FXML
    private Label vancouverAmountLabel;
    @FXML
    private Label torontoAmountLabel;
    @FXML
    private Label totalAmountLabel;

    @FXML
    private Label subVancouverLabel;

    @FXML
    private Label subTorontoLabel;

    private String vehicleCategory;

    private String vehicleForRent;

    private ManagerModel managerModel;
    private UserModel userModel;

    final private ToggleGroup group = new ToggleGroup();
    private String city;
    private String location;

    boolean dailyRentalSelected = false;
    boolean dailyReturnSelected = false;
    boolean locationCBSlected = false;
    private int[] counts = new int[8];

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableContent = null;
        city = "";
        location = "";
        userModel = new UserModel();
        managerModel = new ManagerModel();

        setUpLocationCMB();

    }

    @FXML
    private void handleShowRentalButtonAction() throws IOException, Exception {

        dailyReturnSelected = false;

        if (tableContent != null) {
            oldVehicleVbox.getChildren().remove(tableContent);
        }

        tableContent = managerModel.getRentalReports(location);
        oldVehicleVbox.getChildren().add(tableContent);

        printButton.setDisable(false);

        counts = managerModel.countRentVehicles(location);
        System.out.print("\n location Rent:" + location);

        truckLabel.setText(String.valueOf(counts[0] + counts[2]));
        carLabel.setText(String.valueOf(counts[1] + counts[3]));
        subVancouverLabel.setText(String.valueOf(counts[0] + counts[1]));
        subTorontoLabel.setText(String.valueOf(counts[2] + counts[3]));

        totalNumberLabel.setText(String.valueOf(counts[0] + counts[1] + counts[2] + counts[3]));

        truckAmountLabel.setText("$ " + String.valueOf(counts[4] + counts[5]));
        carAmountLabel.setText("$ " + String.valueOf(counts[6] + counts[7]));
        vancouverAmountLabel.setText("$ " + String.valueOf(counts[4] + counts[6]));
        torontoAmountLabel.setText("$ " + String.valueOf(counts[5] + counts[7]));
        totalAmountLabel.setText("$ " + String.valueOf(counts[4] + counts[5] + counts[6] + counts[7]));
        dailyRentalSelected = true;

        if (!location.equalsIgnoreCase("")) {
            subVancouverLabel.setText("-");
            vancouverAmountLabel.setText("-");
            subTorontoLabel.setText("-");
            torontoAmountLabel.setText("-");

        }
    }

    private void setUpLocationCMB() {

        configureComboBox(locationCMB, userModel.getAllBranches());
        locationCMB.setOnAction((ActionEvent event) -> {
            if (locationCMB.getSelectionModel().getSelectedItem() != null) {
                String branch = locationCMB.getSelectionModel().getSelectedItem();
                location = branch.split(",")[0].trim();
                city = branch.split(",")[1].trim();
                locationCBSlected = true;

            }
        });
    }

    @FXML
    private void handlePrintButtonAction() throws IOException, SQLException, Exception {
        boolean printDone = false;

        printDone = userModel.exportCSV(tableContent, location, dailyRentalSelected, dailyReturnSelected, counts);

    }

    @FXML
    public void refreshBoxes() {

        locationCBSlected = false;
        locationCMB.getSelectionModel().clearSelection();
        locationCMB.valueProperty().set(null);
        location = "";
        city = "";

    }

    @FXML
    private void handleShowReturnButtonAction() throws IOException, Exception {

        dailyRentalSelected = false;

        if (tableContent != null) {
            oldVehicleVbox.getChildren().remove(tableContent);
        }

        tableContent = managerModel.getRetrunReports(location);
        oldVehicleVbox.getChildren().add(tableContent);

        printButton.setDisable(false);
        dailyReturnSelected = true;

        counts = managerModel.countReturnVehicles(location);
        System.out.print("\n location:" + location);

        truckLabel.setText(String.valueOf(counts[0] + counts[2]));
        carLabel.setText(String.valueOf(counts[1] + counts[3]));
        subVancouverLabel.setText(String.valueOf(counts[0] + counts[1]));
        subTorontoLabel.setText(String.valueOf(counts[2] + counts[3]));
        totalNumberLabel.setText(String.valueOf(counts[0] + counts[1] + counts[2] + counts[3]));

        truckAmountLabel.setText("$ " + String.valueOf(counts[4] + counts[5]));
        carAmountLabel.setText("$ " + String.valueOf(counts[6] + counts[7]));
        vancouverAmountLabel.setText("$ " + String.valueOf(counts[4] + counts[6]));
        torontoAmountLabel.setText("$ " + String.valueOf(counts[5] + counts[7]));

        totalAmountLabel.setText("$ " + String.valueOf(counts[4] + counts[5] + counts[6] + counts[7]));

        if (!location.equalsIgnoreCase("")) {
            subVancouverLabel.setText("-");
            vancouverAmountLabel.setText("-");
            subTorontoLabel.setText("-");
            torontoAmountLabel.setText("-");

        }

    }

}
