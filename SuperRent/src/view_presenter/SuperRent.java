/*
 * To change this license header, choose License Headers in Project Properties.
 */
package view_presenter;

import database.MysqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.AppContext;

/**
 *
 * @author eraserxp
 */
public class SuperRent extends Application {

    private MysqlConnection database = MysqlConnection.getInstance();
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        AppContext.getInstance().setMainApp(this);
        
        // when the application starts, log into the database first
        if (database.connect("team04", "pr0j3ct!")) {
            System.out.println("Connect to database successfully!");
        }

        /**
         * set up the login page as the first scene and based on the result of
         * login operation, the application will switch to the apropriate scene
         * for customer, clerk, manager, and administrator
         */
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
