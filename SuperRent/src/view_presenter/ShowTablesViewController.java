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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.AdminModel;

/**
 * FXML Controller class
 *
 * @author eraserxp
 */
public class ShowTablesViewController implements Initializable {

    @FXML
    private VBox tableListBox; //in show table tab

    @FXML
    private VBox tableContentBox; // in show table tab

    private ListView tableList = null;

    private TableView tableContent = null;

    private AdminModel adminModel = new AdminModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setUpShowTablesTab();
    }

    private void setUpShowTablesTab() {
        if (tableList != null) {
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

    private void showTable(String tableName) {
        //remove the content of the last table
        if (tableContent != null) {
            tableContentBox.getChildren().remove(tableContent);
        }

        //refesh the database first so that the latest info will be displayed
        adminModel.refeshDatabaseConnection();
        tableContent = adminModel.getTable(tableName);
        tableContentBox.getChildren().add(tableContent);
    }

}
