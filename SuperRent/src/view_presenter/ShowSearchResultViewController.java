/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AppContext;
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

    private VehicleSelection selectedVehicle; // = new VehicleSelection();

    private TableView<VehicleSelection> searchResult;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String city = AppContext.getInstance().getTempData("city");
        String location = AppContext.getInstance().getTempData("location");
        String vehicleType = AppContext.getInstance().getTempData("vehicleType");
        String fromDate = AppContext.getInstance().getTempData("fromDate");
        String toDate = AppContext.getInstance().getTempData("toDate");
        //once get the data, clear them immediately
        AppContext.getInstance().emptyTempData();
        
        AppContext.getInstance().setTempData("vehicleSelected", "false");
        searchResult = clerkModel.getAvailableVehicles(city, location, vehicleType,
                fromDate, toDate);
        borderPane.setCenter(searchResult);
    }

//    public void setStage(Stage stage) {
//        searchResultStage = stage;
//    }
    public void handleSelect() {
        
        if (!searchResult.getSelectionModel().isEmpty()) {
            VehicleSelection vs = searchResult.getSelectionModel().getSelectedItem();
            // tell the previous page that a vehicle has been selected
            AppContext.getInstance().setTempData("vehicleSelected", "true");
            getPreviousController().update(vs);
        }
        getStage().close();
    }

}
