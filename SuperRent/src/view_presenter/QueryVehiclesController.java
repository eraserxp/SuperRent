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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hide(inputValidationLabel);
    }    
    
}
