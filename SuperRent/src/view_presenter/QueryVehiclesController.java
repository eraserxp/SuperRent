/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

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
    private ComboBox branchCMB;

    @FXML
    private ComboBox vehicleTypeCMB;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Label inputValidationLabel;

    @FXML
    private Button submitButton;

    @FXML
    private BorderPane searchResultBP;

    final private ToggleGroup searchTypeGroup = new ToggleGroup();
    
    private String searchType;

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
                } else if (searchType.equals("Vehicles for sale"))
                    disableNodes(fromDatePicker, toDatePicker);
            }
        });
    }

}
