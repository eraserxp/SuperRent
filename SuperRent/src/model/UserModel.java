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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.layout.GridPane;
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
     *
     * @param SQLs
     * @return
     */
    protected boolean updateDatabaseBatch(String... SQLs) {
        try {
            Statement statement = con.createStatement();
            for (String s : SQLs) {
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
    
    public ArrayList<String> getVehicleTypeAtBranch(String city, String location, String carOrTruck) {
        String SQL = "select distinct typeName from vehicletype VT, vehicleforrent VF, vehicleinbranch VB"
                + " where VF.vlicense=VB.vlicense and VF.vehicletype=VT.typeName "
                + " and VB.location=" + addQuotation(location)  
                + " and VB.city=" + addQuotation(city) 
                + " and VF.category=" + addQuotation(carOrTruck) 
                + " order by typeName";
                ResultSet rs = queryDatabase(SQL);
        System.out.println(SQL);
        ArrayList<String> typeList = new ArrayList<>();
        try {
            while (rs.next()) {
                String type;
                type = rs.getString("typeName") ;
                typeList.add(type);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeList;
    }

    public boolean changePasswd(String username, String passwd) {
        String changePasswd = "update user set password = " + addQuotation(passwd)
                + " where username = " + addQuotation(username);
        return updateDatabase(changePasswd);
    }

    private HashMap<String, Integer> getVehicleRate(String vehicleType) {
        String getAllRates = "select w_rate, d_rate, h_rate, pk_rate"
                + " from vehicletype where typename=" + addQuotation(vehicleType);
        ResultSet rs = queryDatabase(getAllRates);
        HashMap<String, Integer> rates = new HashMap<>();
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                String columnName = rs.getMetaData().getColumnName(i + 1);
                rates.put(columnName, rs.getInt(columnName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rates;
    }

    private HashMap<String, Integer> getEquipmentRate(String vehicleType) {
        String getAllRates = "select d_rate, h_rate "
                + " from equipment where equipName=" + addQuotation(vehicleType);
        ResultSet rs = queryDatabase(getAllRates);
        HashMap<String, Integer> rates = new HashMap<>();
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                String columnName = rs.getMetaData().getColumnName(i + 1);
                rates.put(columnName, rs.getInt(columnName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rates;
    }

    private HashMap<String, Integer> getInsuranceCost(String vehicleType) {
        String getAllRates = "select w_insurance, d_insurance, h_insurance"
                + " from vehicletype where typeName=" + addQuotation(vehicleType);
        ResultSet rs = queryDatabase(getAllRates);
        HashMap<String, Integer> insurances = new HashMap<>();
        try {
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                String columnName = rs.getMetaData().getColumnName(i + 1);
                insurances.put(columnName, rs.getInt(columnName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insurances;
    }

    /**
     *
     * calculate the cost for a given return, rent or reservation
     */
    public GridPane calculateCost(String vehicleType, ArrayList<String> equipList,
            ArrayList<Integer> equipQuantityList,
            LocalDate fromDate, int fromHour,
            LocalDate toDate, int toHour, boolean isRoadStar, int redeemedPoints,
            int odometer) {
        GridPane gridPane = new GridPane();
//        Instant instant = Instant.ofEpochMilli(fromDate.getTime());
//        LocalDateTime startDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
//        instant = Instant.ofEpochMilli(toDate.getTime());
//        LocalDateTime endDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        int all_days = (int) ChronoUnit.DAYS.between(fromDate, toDate);
        int week = (int) all_days/7;
        int days = all_days%7;
        int hours = toHour - fromHour;
        
        //get all the rate and cost from database
        HashMap<String, Integer> vehicleRates = getVehicleRate(vehicleType);
        HashMap<String, Integer> vehicleInsurances = getInsuranceCost(vehicleType);
        HashMap<String, Integer> equipmentRates = getEquipmentRate(vehicleType);

        ArrayList<String> columnHeaders = new ArrayList<>(
                Arrays.asList("    ", "Time", "Renting fee", "Insurance")
        );
        //add the first row
        int cols = columnHeaders.size();
        int rowIndex = 0;
        for (int colIndex=0; colIndex<cols; colIndex++) {
            gridPane.add(new Label(columnHeaders.get(colIndex)), colIndex, rowIndex);
        }

        return gridPane;
    }

}
