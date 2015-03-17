/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * The AdminModel class is responsible for accessing/updating data from the database.
 * The AdminViewController use this class to do the operations related to the
 * persistent data
 */
public class AdminModel extends UserModel {

    public AdminModel() {
        super();
    }

    public ListView getTableNames() {
        try {

            DatabaseMetaData dbmd = con.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            ObservableList<String> table_names = FXCollections.observableArrayList();
            
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
                table_names.add(rs.getString("TABLE_NAME"));
            }
            
            ListView<String> listView = new ListView<>();
            listView.setItems(table_names);
            return listView;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
