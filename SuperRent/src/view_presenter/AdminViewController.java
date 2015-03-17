/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_presenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AdminModel;

/**
 * FXML Controller class
 *
 */
public class AdminViewController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab showTableTab;

    @FXML
    private Tab manageAccountTab;

    @FXML
    private VBox tableListBox;

    @FXML
    private VBox tableContentBox;

    private AdminModel adminModel = new AdminModel();

    private ListView tableList = null;

    private TableView tableContent = null;

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
                    if (tableList!=null) {
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
            }
        });

        // select the showTable tab
        tabPane.getSelectionModel().select(manageAccountTab);
//        tabPane.getSelectionModel().select(showTableTab);
        

    }

    public void showTable(String tableName) {
        //remove the content of the last table
        if (tableContent != null) {
            tableContentBox.getChildren().remove(tableContent);
        }
        tableContent = adminModel.getTable(tableName);
        tableContentBox.getChildren().add(tableContent);
    }

}
