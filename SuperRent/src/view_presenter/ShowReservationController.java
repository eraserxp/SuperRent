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
import javafx.scene.control.Label;
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
public class ShowReservationController extends AbstractController implements Initializable {

//    private Stage searchResultStage;
    @FXML
    private Button selectButton;

    @FXML
    private BorderPane borderPane;
    
    @FXML
    private Label foundCustomerLabel;

    private ClerkModel clerkModel = new ClerkModel();

    private TableView searchResult;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        String city = AppContext.getInstance().getTempData("city");
//        String location = AppContext.getInstance().getTempData("location");
        String phone = AppContext.getInstance().getTempData("phone");
        String username = AppContext.getInstance().getTempData("customer_username");
        //once get the data, clear them immediately
        AppContext.getInstance().emptyTempData();

        searchResult = clerkModel.getReservationsByPhone(phone);
        
        String foundCustomer = "Customer with username: " + username + " has been found.";
        foundCustomerLabel.setText(foundCustomer);
        borderPane.setCenter(searchResult);
//        Label info = new Label();
//        info.setText("Reservation list");
//        borderPane.setTop(info);
    }

//    public void setStage(Stage stage) {
//        searchResultStage = stage;
//    }
    public void handleSelect() {

        if (!searchResult.getSelectionModel().isEmpty()) {
            String reservation = searchResult.getSelectionModel().getSelectedItem().toString();
            //get confirmNo from the above list
            System.out.println(reservation);
            String confirmNo = reservation.split(",")[0];
            confirmNo = confirmNo.replace('[', ' ').trim();
            // tell the previous page that a reservation has been selected
            AppContext.getInstance().setTempData("confirmNo", confirmNo);
        } else {
            AppContext.getInstance().setTempData("confirmNo", "None");
        }
        getStage().close();
    }

}
