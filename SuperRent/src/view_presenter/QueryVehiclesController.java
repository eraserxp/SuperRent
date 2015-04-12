/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AppContext;
import model.ClerkModel;
import util.PngConverter;

/**
 * FXML Controller class
 *
 * @author eraserxp
 */
public class QueryVehiclesController extends AbstractController implements Initializable {

    @FXML
    private RadioButton availableVehicleRB;

    @FXML
    private RadioButton overdueVehicleRB;

    @FXML
    private RadioButton forSaleVehicleRB;

    @FXML
    private ComboBox<String> branchCMB;

    private String city;

    private String location;

    @FXML
    private ComboBox<String> vehicleTypeCMB;

    private String vehicleType;

    @FXML
    private DatePicker fromDatePicker;

    private LocalDate fromDate;

    private LocalDate toDate;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Label inputValidationLabel;

    @FXML
    private Button submitButton;

    @FXML
    private BorderPane searchResultBP;

    private TableView searchResult = null;

    @FXML
    private Button saveButton;

    final private ToggleGroup searchTypeGroup = new ToggleGroup();

    private String searchType;

    private ClerkModel clerkModel = new ClerkModel();

//    private SearchType searchType;
//
//    public enum SearchType {
//
//        AVAILABLE,
//        OVERDUE,
//        FORSALE
//    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hide(inputValidationLabel);
        setupSearchTypeRB();
        setupBranchCMB();
        setupVehicleTypeCMB();
        setupDatePickers();
        // don't know how to save, hide the save button
        hide(saveButton);
    }

    private void setupSearchTypeRB() {
        availableVehicleRB.setToggleGroup(searchTypeGroup);
        overdueVehicleRB.setToggleGroup(searchTypeGroup);
        forSaleVehicleRB.setToggleGroup(searchTypeGroup);
        searchTypeGroup.selectedToggleProperty().addListener((ob, oldv, newv) -> {
            if (searchTypeGroup.getSelectedToggle() != null) {
                RadioButton rb = (RadioButton) newv.getToggleGroup().getSelectedToggle();
                searchType = rb.getText();

                if (searchType.equals("Available vehicles")) {
                    enableNodes(fromDatePicker, toDatePicker);
                } else if (searchType.equals("Overdue vehicles")) {
                    disableNodes(fromDatePicker, toDatePicker);
                } else if (searchType.equals("Vehicles for sale")) {
                    disableNodes(fromDatePicker, toDatePicker);
                }

                //hide the input validation information
                hide(inputValidationLabel);
            }
        });

        availableVehicleRB.setSelected(true);
    }

    private void setupBranchCMB() {
        ArrayList<String> allBranches = clerkModel.getAllBranches();
        //add unspecified to the front
        allBranches.add(0, "unspecified");
        configureComboBox(branchCMB, allBranches);
        branchCMB.getSelectionModel().selectFirst();
        branchCMB.setOnAction((ActionEvent event) -> {
            hide(inputValidationLabel);
        });
    }

    private void setupVehicleTypeCMB() {
        ArrayList<String> allVehicleTypes = clerkModel.getAllVehicleTypes();
        allVehicleTypes.add(0, "unspecified");
        configureComboBox(vehicleTypeCMB, allVehicleTypes);
        vehicleTypeCMB.getSelectionModel().selectFirst();
        vehicleTypeCMB.setOnAction((ActionEvent event) -> {
            hide(inputValidationLabel);
        });
    }

    private void setupDatePickers() {
        fromDatePicker.valueProperty().addListener((obs, oldv, newv) -> {
            hide(inputValidationLabel);
        });

        toDatePicker.valueProperty().addListener((obs, oldv, newv) -> {
            hide(inputValidationLabel);
        });
    }

    public void handleSearch() {
        String branch = branchCMB.getSelectionModel().getSelectedItem();
        vehicleType = vehicleTypeCMB.getSelectionModel().getSelectedItem();
        
        if (searchType.equals("Available vehicles")) {
            fromDate = fromDatePicker.getValue();
            if (fromDate == null) {
                showWarning(inputValidationLabel, "From date is empty.");
                return;
            }

            toDate = toDatePicker.getValue();
            if (toDate == null) {
                showWarning(inputValidationLabel, "To date is empty.");
                return;
            }

            if (branch.equals("unspecified") && vehicleType.equals("unspecified")) {
                searchResult = clerkModel.showAvailableVehicles(fromDate, toDate);

            }

            if (!branch.equals("unspecified") && vehicleType.equals("unspecified")) {
                city = branch.split(",")[1].trim();
                location = branch.split(",")[0].trim();
                searchResult = clerkModel.showAvailableVehicles(city, location, fromDate, toDate);
            }

            if (branch.equals("unspecified") && !vehicleType.equals("unspecified")) {
                searchResult = clerkModel.showAvailableVehicles(vehicleType, fromDate, toDate);

            }

            if (!branch.equals("unspecified") && !vehicleType.equals("unspecified")) {
                city = branch.split(",")[1].trim();
                location = branch.split(",")[0].trim();
                searchResult = clerkModel.showAvailableVehicles(city, location, vehicleType, fromDate, toDate);
            }

        }

        //get the input
        if (branch.equals("unspecified")) {
            city = null;
            location = null;

        } else {
            city = branch.split(",")[1].trim();
            location = branch.split(",")[0].trim();
        }

        if (vehicleType.equals("unspecified")) {
            vehicleType = null;
        }

        // search for overdue vehicles
        if (searchType.equals("Overdue vehicles")) {
            searchResult = clerkModel.showOverdueVehicles(city, location, vehicleType);
        }

        // search for vehicle for sale
        if (searchType.equals("Vehicles for sale")) {
            searchResult = clerkModel.showVehiclesForSale(city, location, vehicleType);
        }

        // show the search result
        searchResultBP.setCenter(searchResult);
    }

    public void handleSave() {
        if (searchResult != null) {
            Stage primaryStage = AppContext.getInstance().getPrimaryStage();
            PngConverter pngConverter = new PngConverter(primaryStage);
            pngConverter.saveAsPng(searchResult);
        }
    }

}
