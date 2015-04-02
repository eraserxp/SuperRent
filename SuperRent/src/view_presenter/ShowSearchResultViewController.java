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
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ClerkModel;
import model.VehicleSelection;

/**
 * FXML Controller class
 *
 * @author eraserxp
 */
public class ShowSearchResultViewController extends AbstractController implements Initializable {

//    private Stage searchResultStage;
    
    @FXML
    private Button selectButton;
    
    @FXML
    private BorderPane borderPane;
    
    private ClerkModel clerkModel = new ClerkModel();
    
    private VehicleSelection selectedVehicle = new VehicleSelection();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TableView searchResult = clerkModel.getSelectedVehicles();
        borderPane.setCenter(searchResult);
    }  
    
//    public void setStage(Stage stage) {
//        searchResultStage = stage;
//    }
    
    public void handleSelect() {
        getPreviousController().update(null);
        getStage().close();
    }
    
    
}
