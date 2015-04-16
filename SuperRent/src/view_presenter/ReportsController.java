/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private Label boxNum;

    @FXML
    private Label carNum;

    @FXML
    private Label foot12Num;

    @FXML
    private Label foot15Num;
    @FXML
    private Label foot24Num;
    @FXML
    private Label stanNum;
    @FXML
    private Label vanNum;
    @FXML
    private Label comNum;

    @FXML
    private Label ecNum;

    @FXML
    private Label fullNum;

    @FXML
    private Label luxNum;

    @FXML
    private Label midNum;

    @FXML
    private Label premNum;

    @FXML
    private Label suvNum;

    @FXML
    private Label boxAmount;

    @FXML
    private Label carAmount;

    @FXML
    private Label foot12Amount;

    @FXML
    private Label foot15Amount;
    @FXML
    private Label foot24Amount;
    @FXML
    private Label stanAmount;
    @FXML
    private Label vanAmount;
    @FXML
    private Label comAmount;

    @FXML
    private Label ecAmount;

    @FXML
    private Label fullAmount;

    @FXML
    private Label luxAmount;

    @FXML
    private Label midAmount;

    @FXML
    private Label premAmount;

    @FXML
    private Label suvAmount;

    @FXML
    private DatePicker fromDatePicker;

    private LocalDate fromDate;

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
    private int[] counts = new int[60];

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
    @SuppressWarnings("empty-statement")
    private void handleShowRentalButtonAction() throws IOException, Exception {

        dailyReturnSelected = false;

        fromDate = fromDatePicker.getValue();

        if (tableContent != null) {
            oldVehicleVbox.getChildren().remove(tableContent);
        }

        tableContent = managerModel.getRentalReports(location, fromDate);
        oldVehicleVbox.getChildren().add(tableContent);

        printButton.setDisable(false);
        dailyRentalSelected = true;
        counts = managerModel.countRentVehicles(location,fromDate);
        System.out.print("\n location Rent:" + location);

        boxNum.setText(String.valueOf(counts[0] + counts[14]));
        carNum.setText(String.valueOf(counts[1] + counts[15]));
        foot12Num.setText(String.valueOf(counts[2] + counts[16]));
        foot15Num.setText(String.valueOf(counts[3] + counts[17]));
        foot24Num.setText(String.valueOf(counts[4] + counts[18]));
        stanNum.setText(String.valueOf(counts[5] + counts[19]));
        vanNum.setText(String.valueOf(counts[6] + counts[20]));
        comNum.setText(String.valueOf(counts[7] + counts[21]));
        ecNum.setText(String.valueOf(counts[8] + counts[22]));
        fullNum.setText(String.valueOf(counts[9] + counts[23]));
        luxNum.setText(String.valueOf(counts[10] + counts[24]));
        midNum.setText(String.valueOf(counts[11] + counts[25]));
        premNum.setText(String.valueOf(counts[12] + counts[26]));
        suvNum.setText(String.valueOf(counts[13] + counts[27]));

        boxAmount.setText("$ " + String.valueOf(counts[28] + counts[42]));
        carAmount.setText("$ " + String.valueOf(counts[29] + counts[43]));
        foot12Amount.setText("$ " + String.valueOf(counts[30] + counts[44]));
        foot15Amount.setText("$ " + String.valueOf(counts[31] + counts[45]));
        foot24Amount.setText("$ " + String.valueOf(counts[32] + counts[46]));
        stanAmount.setText("$ " + String.valueOf(counts[33] + counts[47]));
        vanAmount.setText("$ " + String.valueOf(counts[34] + counts[48]));
        comAmount.setText("$ " + String.valueOf(counts[35] + counts[49]));
        ecAmount.setText("$ " + String.valueOf(counts[36] + counts[50]));
        fullAmount.setText("$ " + String.valueOf(counts[37] + counts[51]));
        luxAmount.setText("$ " + String.valueOf(counts[38] + counts[52]));
        midAmount.setText("$ " + String.valueOf(counts[39] + counts[53]));
        premAmount.setText("$ " + String.valueOf(counts[40] + counts[54]));
        suvAmount.setText("$ " + String.valueOf(counts[41] + counts[55]));

        int vancouverAmount = 0;
        int vancouverNum = 0;
        int torontaAmount = 0;
        int torontaNum = 0;
        int totalAmount = 0;
        int totalNumber = 0;

        for (int i = 0; i < counts.length; i++) {
            if (i < 14) {
                vancouverNum += counts[i];

            } else if (i < 28) {
                torontaNum += counts[i];

            } else if (i < 42) {
                vancouverAmount += counts[i];

            } else if (i < 56) {
                torontaAmount += counts[i];

            }

        }

        totalAmount = vancouverAmount + torontaAmount;
        totalNumber = vancouverNum + torontaNum;

        subVancouverLabel.setText(String.valueOf(vancouverNum));
        subTorontoLabel.setText(String.valueOf(torontaNum));
        vancouverAmountLabel.setText("$ " + String.valueOf(vancouverAmount));
        torontoAmountLabel.setText("$ " + String.valueOf(torontaAmount));

        totalAmountLabel.setText("$ " + String.valueOf(totalAmount));
        totalNumberLabel.setText(String.valueOf(totalNumber));

        if (!location.equalsIgnoreCase("")) {
            subVancouverLabel.setText("-");
            vancouverAmountLabel.setText("-");
            subTorontoLabel.setText("-");
            torontoAmountLabel.setText("-");

        }

    }

    private void setUpLocationCMB() {
        ArrayList<String> allBranches = userModel.getAllBranches();
        //add unspecified to the front
        allBranches.add(0, "Unspecified");

        configureComboBox(locationCMB, allBranches);
        locationCMB.getSelectionModel().selectFirst();

        locationCMB.setOnAction((ActionEvent event) -> {
            if (locationCMB.getSelectionModel().getSelectedItem() != null) {
                if (!locationCMB.getSelectionModel().getSelectedItem().equalsIgnoreCase("Unspecified")) {
                    String branch = locationCMB.getSelectionModel().getSelectedItem();
                    location = branch.split(",")[0].trim();
                    city = branch.split(",")[1].trim();
                    locationCBSlected = true;

                }
                else
                {
                    locationCBSlected = false;
                    location="";
                    city="";
                
                
                }
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
        fromDate = fromDatePicker.getValue();
        tableContent = managerModel.getRetrunReports(location, fromDate);
        oldVehicleVbox.getChildren().add(tableContent);

        printButton.setDisable(false);
        dailyReturnSelected = true;

        counts = managerModel.countReturnVehicles(location,fromDate);
        System.out.print("\n location:" + location);

        boxNum.setText(String.valueOf(counts[0] + counts[14]));
        carNum.setText(String.valueOf(counts[1] + counts[15]));
        foot12Num.setText(String.valueOf(counts[2] + counts[16]));
        foot15Num.setText(String.valueOf(counts[3] + counts[17]));
        foot24Num.setText(String.valueOf(counts[4] + counts[18]));
        stanNum.setText(String.valueOf(counts[5] + counts[19]));
        vanNum.setText(String.valueOf(counts[6] + counts[20]));
        comNum.setText(String.valueOf(counts[7] + counts[21]));
        ecNum.setText(String.valueOf(counts[8] + counts[22]));
        fullNum.setText(String.valueOf(counts[9] + counts[23]));
        luxNum.setText(String.valueOf(counts[10] + counts[24]));
        midNum.setText(String.valueOf(counts[11] + counts[25]));
        premNum.setText(String.valueOf(counts[12] + counts[26]));
        suvNum.setText(String.valueOf(counts[13] + counts[27]));

        boxAmount.setText("$ " + String.valueOf(counts[28] + counts[42]));
        carAmount.setText("$ " + String.valueOf(counts[29] + counts[43]));
        foot12Amount.setText("$ " + String.valueOf(counts[30] + counts[44]));
        foot15Amount.setText("$ " + String.valueOf(counts[31] + counts[45]));
        foot24Amount.setText("$ " + String.valueOf(counts[32] + counts[46]));
        stanAmount.setText("$ " + String.valueOf(counts[33] + counts[47]));
        vanAmount.setText("$ " + String.valueOf(counts[34] + counts[48]));
        comAmount.setText("$ " + String.valueOf(counts[35] + counts[49]));
        ecAmount.setText("$ " + String.valueOf(counts[36] + counts[50]));
        fullAmount.setText("$ " + String.valueOf(counts[37] + counts[51]));
        luxAmount.setText("$ " + String.valueOf(counts[38] + counts[52]));
        midAmount.setText("$ " + String.valueOf(counts[39] + counts[53]));
        premAmount.setText("$ " + String.valueOf(counts[40] + counts[54]));
        suvAmount.setText("$ " + String.valueOf(counts[41] + counts[55]));

        int vancouverAmount = 0;
        int vancouverNum = 0;
        int torontaAmount = 0;
        int torontaNum = 0;
        int totalAmount = 0;
        int totalNumber = 0;

        for (int i = 0; i < counts.length; i++) {
            if (i < 14) {
                vancouverNum += counts[i];

            } else if (i < 28) {
                torontaNum += counts[i];

            } else if (i < 42) {
                vancouverAmount += counts[i];

            } else if (i < 56) {
                torontaAmount += counts[i];

            }

        }

        totalAmount = vancouverAmount + torontaAmount;
        totalNumber = vancouverNum + torontaNum;

        subVancouverLabel.setText(String.valueOf(vancouverNum));
        subTorontoLabel.setText(String.valueOf(torontaNum));
        vancouverAmountLabel.setText("$ " + String.valueOf(vancouverAmount));
        torontoAmountLabel.setText("$ " + String.valueOf(torontaAmount));

        totalAmountLabel.setText("$ " + String.valueOf(totalAmount));
        totalNumberLabel.setText(String.valueOf(totalNumber));

        if (!location.equalsIgnoreCase("")) {
            subVancouverLabel.setText("-");
            vancouverAmountLabel.setText("-");
            subTorontoLabel.setText("-");
            torontoAmountLabel.setText("-");

        }

    }

}
