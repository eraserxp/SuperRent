/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * The AdminModel class is responsible for accessing/updating data from the
 * database. The AdminViewController use this class to do the operations related
 * to the persistent data
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

    public boolean addClerk(String username, String passwd, String name, String type,
            String branch_city, String branch_location) {
        boolean result = false;
        String addUser = "insert into user values (" + addQuotation(username) + ","
                + addQuotation(passwd) + "," + addQuotation(name)
                + "," + addQuotation(type) + ")";
        String addClerk = "insert into clerk values ( " + addQuotation(username) + ","
                + addQuotation(branch_city) + ","
                + addQuotation(branch_location) + ")";

//        return updateDatabase(addUser) && updateDatabase(addClerk);
        return updateDatabaseBatch(addUser, addClerk);
    }

    public boolean addCustomer(String username, String passwd, String name, String type,
            String phone, String address) {
        boolean result = false;
        String addUser = "insert into user values (" + addQuotation(username) + ","
                + addQuotation(passwd) + "," + addQuotation(name)
                + "," + addQuotation(type) + ")";
        String isRoadStar = "0";
        String isClubMember = "0";
        Integer point = 0;
        String addCustomer = "insert into customer "
                + "( username, phone, address, isRoadStar, isClubMember, point, payment_date ) "
                + " values ( " + addQuotation(username) + ","
                + addQuotation(phone) + "," + addQuotation(address) + ", " 
                + isRoadStar + ", " + isClubMember + ", " + point.toString() + "," 
                + " null "
                + ")";
        System.out.println(addCustomer);
        return updateDatabaseBatch(addUser, addCustomer);
    }

    
    public boolean removeUser(String username) {
        String removeuser = "delete from user where username = " + addQuotation(username);
        return updateDatabase(removeuser);
    }
}
