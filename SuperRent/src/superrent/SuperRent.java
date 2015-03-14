/*
 * To change this license header, choose License Headers in Project Properties.
 */
package superrent;

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

/**
 *
 * @author eraserxp
 */
public class SuperRent extends Application {

    private MysqlConnection database = MysqlConnection.getInstance();

    @Override
    public void start(Stage stage) throws Exception {
        if (database.connect("eraserxp", "a38927075")) {
            System.out.println("Connect to database successfully!");
        }
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        TableView root = showTable("branch");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * show a table -- as a test
     */
    public TableView showTable(String tableName) {
        ObservableList<ObservableList> data;

        TableView tableview = new TableView();
        // get the connection from the database
        Connection c = database.getConnection();

        data = FXCollections.observableArrayList();
        try {

            //select everything from the given table
            String SQL = "SELECT * from " + tableName;

            //execute the sql statement and obtain the result
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**
             * ********************************
             *
             * TABLE COLUMN ADDED DYNAMICALLY *
             *
             *********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {

                        return new SimpleStringProperty(param.getValue().get(j).toString());

                    }

                });

                tableview.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

            /**
             * add the data to ObservableList for rendering purpose
             */
            while (rs.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                // for each row, we add every columns
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));

                }

                System.out.println("Row [1] added " + row);
                System.out.println("Column size = " +  row.size());
                // add each row into the data
                data.add(row);

            }

            System.out.println("Row size = " + data.size());
            //add all rows into the tableview
            tableview.setItems(data);
 

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }
        
        return tableview;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
