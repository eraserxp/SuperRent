/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.MysqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.util.Callback;

/**
 *
 * @author eraserxp
 */
public class UserModel {

    protected Connection con = null;
    protected MysqlConnection mysqlConnInstance = null;

    public UserModel() {
        mysqlConnInstance = MysqlConnection.getInstance();
        con = MysqlConnection.getInstance().getConnection();
    }

    /**
     * refresh the database connection so that the info
     */
    public void refeshDatabaseConnection() {
        con = mysqlConnInstance.refreshConnection();
    }

    /**
     * show a table -- as a test
     */
    public TableView getTable(String tableName) {
        ObservableList<ObservableList> data;

        TableView tableview = new TableView();

        data = FXCollections.observableArrayList();
        try {

            //select everything from the given table
            String SQL = "SELECT * from " + tableName;

            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            /**
             *
             * add table column dynamically
             *
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                        return new SimpleStringProperty(param.getValue().get(j).toString());

                    }

                });

                tableview.getColumns().addAll(col);
                // get rid of the extra column displayed in a Table View by default
                // tableview.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
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
                System.out.println("Column size = " + row.size());
                // add each row into the data
                data.add(row);

            }

            System.out.println("Row size = " + data.size());
            //add all rows into the tableview
            tableview.setItems(data);
            //close the result set
            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return tableview;
    }

    /**
     * Check if the given username is already existed in the database
     *
     * @param username
     * @return
     */
    public boolean isUsernameExisted(String username) {
        String SQL = "select * from user where username = '" + username + "'";
        boolean result = false;
        try {
            if (queryDatabase(SQL).next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    protected ResultSet queryDatabase(String SQL) {
        ResultSet rs = null;
        try {
            rs = con.createStatement().executeQuery(SQL);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    protected boolean updateDatabase(String SQL) {
        try {
            con.createStatement().executeUpdate(SQL);
            con.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * execute multiple sql in a batch
     * @param SQLs
     * @return 
     */
    protected boolean updateDatabaseBatch(String ... SQLs) {
        try {
            Statement statement = con.createStatement();
            for (String s: SQLs) {
                statement.addBatch(s);
            }
            statement.executeBatch();
            con.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

//    protected boolean updateDatabase_noCommit(String SQL) {
//        try {
//            con.createStatement().executeUpdate(SQL);
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }

    protected String addQuotation(String s) {
        return " '" + s + "' ";
    }

    public boolean addUser(String username, String passwd, String name, String type) {
        String SQL = "insert into user values (" + addQuotation(username) + ","
                + addQuotation(passwd) + "," + addQuotation(name)
                + "," + addQuotation(type) + ")";
        return updateDatabase(SQL);
    }

//    public boolean addUser_noCommit(String username, String passwd, String name, String type) {
//        String SQL = "insert into user values (" + addQuotation(username) + ","
//                + addQuotation(passwd) + "," + addQuotation(name)
//                + "," + addQuotation(type) + ")";
//        return updateDatabase_noCommit(SQL);
//    }

    public ArrayList<String> getAllBranches() {
        String SQL = "select city, location from branch";
        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> branchList = new ArrayList<>();
        try {
            while (rs.next()) {
                String branch;
                branch = rs.getString("location") + ", " + rs.getString("city");
                branchList.add(branch);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return branchList;
    }
}
