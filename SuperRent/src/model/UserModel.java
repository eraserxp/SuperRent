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
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author eraserxp
 */
public class UserModel {

    private ArrayList<String> lowRankList = new ArrayList<>(
            Arrays.asList("economy", "compact", "midsize", "standard", "full-size", "premium")
    );

    private ArrayList<String> highRankList = new ArrayList<>(
            Arrays.asList("luxury", "suv", "van", "24foot", "15foot", "12foot",
                    "boxtrucks", "cargovans")
    );

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

    public ArrayList< ArrayList<String>> getMatrixForSQL(String SQL) {
        ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
        try {

            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);
            ArrayList<String> headerRow = new ArrayList<>();
            /**
             *
             * add table column dynamically
             *
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                headerRow.add(rs.getMetaData().getColumnName(i + 1));
            }
            matrix.add(headerRow);

            /**
             * add the data to ObservableList for rendering purpose
             */
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                // for each row, we add every columns
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));

                }

                System.out.println("Row [1] added " + row);
                System.out.println("Column size = " + row.size());
                // add each row into the data
                matrix.add(row);

            }

            //close the result set
            rs.close();

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return matrix;

    }

    public TableView getTableViewForSQL(String SQL) {
        ObservableList<ObservableList> data;

        TableView tableview = new TableView();

        data = FXCollections.observableArrayList();
        try {

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
                    String value = rs.getString(i);
                    if (!rs.wasNull()) {
                        row.add(value);
                    } else {
                        row.add("NULL");
                    }

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
     * show a table -- as a test
     */
    public TableView getTable(String tableName) {
        //select everything from the given table
        String SQL = "SELECT * from " + tableName;
        return getTableViewForSQL(SQL);
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

    public boolean isCustomerExisted(String username) {
        String SQL = "select * from customer where username = '" + username + "'";
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

    protected String addQuotation(String s) {
        return " '" + s + "' ";
    }

    //check whether a store has the vehicle type that is available in a certain time period
    public boolean isVehicleTypeAvailable(String city, String location,
            String vehicleType, String fromDate, String toDate) {
        int vCount = 0;
        int rCount = 0;
        // find out the vehicle of the given type that are now in the given branch
        String countNotRented = "select count(distinct VR.vlicense)"
                + " from vehicleforrent VR, vehicleinbranch VB "
                + " where VR.isAvailable=1 and VR.vlicense = VB.vlicense "
                + " and VB.city = " + addQuotation(city)
                + " and VB.location =" + addQuotation(location)
                + " and VR.vehicleType = " + addQuotation(vehicleType);
        System.out.println(countNotRented);
        // find out the number of vehicle that has been reserved in the time period
        String countReserved = "select count(*) from reservation R "
                + " where R.branch_city = " + addQuotation(city)
                + " and R.branch_location = " + addQuotation(location)
                + " and R.vehicleType = " + addQuotation(vehicleType)
                + " and R.status = 'pending' "
                + " and ( "
                + " (R.pickup_date between " + addQuotation(fromDate)
                + " and " + addQuotation(toDate) + ") or "
                + " (R.return_date between " + addQuotation(fromDate)
                + " and " + addQuotation(toDate) + ") "
                + " )";
        System.out.println(countReserved);
        ResultSet rs = queryDatabase(countNotRented);
        ResultSet rs2 = queryDatabase(countReserved);

        try {
            if (rs.next()) {
                vCount = rs.getInt(1);
            } else {
                return false;
            }

            if (rs2.next()) {
                rCount = rs2.getInt(1);
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (vCount - rCount) > 0;
    }

    public boolean addUser(String username, String passwd, String name, String type) {
        String SQL = "insert into user values (" + addQuotation(username) + ","
                + addQuotation(passwd) + "," + addQuotation(name)
                + "," + addQuotation(type) + ")";
        return updateDatabase(SQL);
    }

    public int getOdometer(String vlicense) {
        int odometer = -1;
        String SQL = "select odometer from vehicleforrent where vlicense = "
                + addQuotation(vlicense);
        ResultSet rs = queryDatabase(SQL);
        try {
            if (rs.next()) {
                odometer = rs.getInt("odometer");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return odometer;
    }

    public String getCustomerByPhone(String phone) {
        String SQL = "select username from customer where phone = "
                + addQuotation(phone);
        ResultSet rs = queryDatabase(SQL);
        String username = null;
        try {
            while (rs.next()) {
                username = rs.getString("username");

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }

    public String getCustomerPhone(String username) {
        String SQL = "select phone from customer where username = "
                + addQuotation(username);
        ResultSet rs = queryDatabase(SQL);
        String phone = null;
        try {
            while (rs.next()) {
                username = rs.getString("phone");

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phone;
    }

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
                type = rs.getString("typeName");
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
        System.out.println(getAllRates);
        ResultSet rs = queryDatabase(getAllRates);
        HashMap<String, Integer> rates = new HashMap<String, Integer>();
        try {
            if (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    String columnName = rs.getMetaData().getColumnName(i + 1);
                    System.out.println(columnName);
                    rates.put(columnName, rs.getInt(columnName));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rates;
    }

    private int getDailyMileLimit(String vehicleType) {
        String getLimit = "select milelimit from vehicletype where typename = "
                + addQuotation(vehicleType);
        int limit = 0;
        ResultSet rs = queryDatabase(getLimit);
        try {
            if (rs.next()) {
                limit = rs.getInt("milelimit");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return limit;
    }

    private int getOldOdometer(String vlicense) {
        String getLimit = "select odometer from vehicleforrent where vlicense = "
                + addQuotation(vlicense);
        int oldOdometer = 0;
        ResultSet rs = queryDatabase(getLimit);
        try {
            if (rs.next()) {
                oldOdometer = rs.getInt("odometer");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oldOdometer;
    }

    private HashMap<String, Integer> getEquipmentRate(String equipName) {
        String getAllRates = "select d_rate, h_rate "
                + " from equipment where equipName=" + addQuotation(equipName);
        ResultSet rs = queryDatabase(getAllRates);
        HashMap<String, Integer> rates = new HashMap<>();
        try {
            if (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    String columnName = rs.getMetaData().getColumnName(i + 1);
                    rates.put(columnName, rs.getInt(columnName));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rates;
    }

    private HashMap<String, String> getOneRowAsHashMap(String SQL) {
        ResultSet rs = queryDatabase(SQL);
        HashMap<String, String> result = new HashMap<>();
        try {
            if (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    String columnName = rs.getMetaData().getColumnName(i + 1);
                    result.put(columnName, rs.getString(columnName));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private HashMap<String, Integer> getInsuranceCost(String vehicleType) {
        String getAllRates = "select w_insurance, d_insurance, h_insurance"
                + " from vehicletype where typeName=" + addQuotation(vehicleType);
        ResultSet rs = queryDatabase(getAllRates);
        HashMap<String, Integer> insurances = new HashMap<>();
        try {
            if (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    String columnName = rs.getMetaData().getColumnName(i + 1);
                    insurances.put(columnName, rs.getInt(columnName));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insurances;
    }

    public ArrayList<String> getEquipments(String category) {
        ArrayList<String> equipments = new ArrayList<String>();
        String getEquipments = "select distinct equipName from equipment where "
                + " type = " + addQuotation(category);
        ResultSet rs = queryDatabase(getEquipments);
        try {
            while (rs.next()) {
                String equipName;
                equipName = rs.getString("equipName");
                equipments.add(equipName);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return equipments;
    }

    public String getUserType(String username) {
        String type = null;
        String getType = "select type from user where username = "
                + addQuotation(username);
        ResultSet rs = queryDatabase(getType);
        try {
            if (rs.next()) {
                type = rs.getString("type");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
    }

    public GridPane generateReportAsGridPane(ArrayList<ArrayList<String>> reportContent) {
        GridPane gridPane = new GridPane();
        int rows = reportContent.size();
        int cols = reportContent.get(0).size();
        double percentWidth = 100.0 / (double) cols;
        for (int i = 0; i < cols; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(percentWidth);
            col.setHalignment(HPos.RIGHT);
            gridPane.getColumnConstraints().add(col);
        }

        for (int i = 0; i < rows; i++) {
            ArrayList<String> row = reportContent.get(i);
            for (int j = 0; j < cols; j++) {
                String text = row.get(j);
                gridPane.add(new Label(text), j, i);
            }
        }

        return gridPane;
    }

    public boolean isLowRankVehicle(String vehicleType) {
        return lowRankList.contains(vehicleType.toLowerCase());
    }

    public boolean isHighRankVehicle(String vehicleType) {
        return highRankList.contains(vehicleType.toLowerCase());
    }

    public boolean isMembership(String username) {
        int isMember = 0;
        String sql = "select isClubMember from customer where username = "
                + addQuotation(username);
        ResultSet rs = queryDatabase(sql);
        try {
            if (rs.next()) {
                isMember = rs.getInt("isClubMember");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isMember == 1) {
            return true;
        }
        return false;
    }

    public boolean isRoadStar(String username) {
        int isRoadStar = 0;
        String sql = "select isRoadStar from customer where username = "
                + addQuotation(username);
        ResultSet rs = queryDatabase(sql);
        try {
            if (rs.next()) {
                isRoadStar = rs.getInt("isRoadStar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isRoadStar == 1) {
            return true;
        }
        return false;

    }

    public int getPoints(String username) {
        int point = 0;
        String sql = "select point from customer where username = "
                + addQuotation(username);
        ResultSet rs = queryDatabase(sql);
        try {
            if (rs.next()) {
                point = rs.getInt("point");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return point;
    }

    public HashMap<String, String> getEquipmentsByCNo(String CNo) {
        HashMap<String, String> result = new HashMap<>();
        String sql = "select equipName, quantity from reserve_addon "
                + "where confirmNo = " + CNo;
        ResultSet rs = queryDatabase(sql);
        try {
            while (rs.next()) {
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    String equipName = rs.getString("equipName");
                    String quantity = rs.getString("quantity");
                    result.put(equipName, quantity);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     *
     * calculate the cost for a given return, rent or reservation
     */
    public GridPane calculateCost(String vehicleType, ArrayList<String> equipList,
            ArrayList<Integer> equipQuantityList,
            LocalDate fromDate, int fromHour,
            LocalDate toDate, int toHour, boolean isRoadStar, int redeemedPoints,
            int odometer, String vlicense) throws SQLException {
        GridPane gridPane = new GridPane();
        int totalCost = 0;

        int all_days = (int) ChronoUnit.DAYS.between(fromDate, toDate);
        int total_day = all_days;
        int hours = toHour - fromHour;
        if (hours < 0) {
            hours += 24;
            all_days -= 1;
        }
        int weeks = (int) all_days / 7;
        int days = all_days % 7;

        //get all the rate and cost from database
        HashMap<String, Integer> vehicleRates = getVehicleRate(vehicleType);
        HashMap<String, Integer> vehicleInsurances = getInsuranceCost(vehicleType);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        col2.setHalignment(HPos.RIGHT);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        col3.setHalignment(HPos.RIGHT);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(20);
        col4.setHalignment(HPos.RIGHT);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setPercentWidth(20);
        col5.setHalignment(HPos.RIGHT);
        gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

        ArrayList<String> columnHeaders = new ArrayList<>(
                Arrays.asList("Type", "Time", "Renting fee", "Insurance", "subtotal")
        );
        //add the first row
        int cols = columnHeaders.size();
        int rowIndex = 0;
        for (int colIndex = 0; colIndex < cols; colIndex++) {
            gridPane.add(new Label(columnHeaders.get(colIndex)), colIndex, rowIndex);
        }
        rowIndex++;

        //add an empty line
        for (int colIndex = 0; colIndex < cols; colIndex++) {
            gridPane.add(new Label("--------------"), colIndex, rowIndex);
        }
        rowIndex++;

        gridPane.add(new Label(vehicleType), 0, rowIndex);

        if (weeks > 0) {
            gridPane.add(new Label(weeks + " week(s)"), 1, rowIndex);
            gridPane.add(new Label(weeks + " x " + vehicleRates.get("w_rate") / 100 + ".00"),
                    2, rowIndex);
            int w_rent = vehicleRates.get("w_rate") * weeks;
            totalCost += w_rent;
            gridPane.add(new Label(weeks + " x " + vehicleInsurances.get("w_insurance") / 100 + ".00"),
                    3, rowIndex);
            int w_cost = vehicleInsurances.get("w_insurance") * weeks;
            totalCost += w_cost;
            gridPane.add(new Label((w_rent + w_cost) / 100 + ".00"),
                    4, rowIndex);
            rowIndex++;
        }

        if (days > 0) {
            gridPane.add(new Label(days + " day(s)"), 1, rowIndex);
            gridPane.add(new Label(days + " x " + vehicleRates.get("d_rate") / 100 + ".00"),
                    2, rowIndex);
            int d_rent = vehicleRates.get("d_rate") * days;
            totalCost += d_rent;
            gridPane.add(new Label(days + " x " + vehicleInsurances.get("d_insurance") / 100 + ".00"),
                    3, rowIndex);
            int d_cost = vehicleInsurances.get("d_insurance") * days;
            totalCost += d_cost;
            gridPane.add(new Label((d_rent + d_cost) / 100 + ".00"),
                    4, rowIndex);
            rowIndex++;
        }

        if (hours > 0) {
            gridPane.add(new Label(hours + " hour(s)"), 1, rowIndex);
            gridPane.add(new Label(hours + " x " + vehicleRates.get("h_rate") / 100 + ".00"),
                    2, rowIndex);
            int h_rent = vehicleRates.get("h_rate") * hours;
            totalCost += h_rent;
            gridPane.add(new Label(hours + " x " + vehicleInsurances.get("h_insurance") / 100 + ".00"),
                    3, rowIndex);
            int h_cost = vehicleInsurances.get("h_insurance") * hours;
            totalCost += h_cost;
            gridPane.add(new Label((h_rent + h_cost) / 100 + ".00"),
                    4, rowIndex);
            rowIndex++;
        }

        //add an empty line       
        for (int colIndex = 0; colIndex < cols; colIndex++) {
            gridPane.add(new Label("  "), colIndex, rowIndex);
        }
        rowIndex++;

        //consider the roadstar deduction
        if (isRoadStar) {
            for (int colIndex = 0; colIndex < cols; colIndex++) {
                gridPane.add(new Label("--------------"), colIndex, rowIndex);
            }
            rowIndex++;
            gridPane.add(new Label("Road Star reduction"), 0, rowIndex);
            if (weeks > 0) {
                gridPane.add(new Label(weeks + " week(s)"), 1, rowIndex);

                gridPane.add(new Label(weeks + " x " + vehicleInsurances.get("w_insurance") / 100 + ".00/2"),
                        3, rowIndex);
                int w_cost = vehicleInsurances.get("w_insurance") * weeks / 2;
                totalCost -= w_cost;
                gridPane.add(new Label("-" + w_cost / 100 + ".00"),
                        4, rowIndex);
                rowIndex++;
            }

            if (days > 0) {
                gridPane.add(new Label(days + " day(s)"), 1, rowIndex);

                gridPane.add(new Label(days + " x " + vehicleInsurances.get("d_insurance") / 100 + ".00/2"),
                        3, rowIndex);
                int d_cost = vehicleInsurances.get("d_insurance") * days / 2;
                totalCost -= d_cost;
                gridPane.add(new Label("-" + d_cost / 100 + ".00"),
                        4, rowIndex);
                rowIndex++;
            }

            if (hours > 0) {
                gridPane.add(new Label(hours + " hour(s)"), 1, rowIndex);
                gridPane.add(new Label(hours + " x " + vehicleInsurances.get("h_insurance") / 100 + ".00/2"),
                        3, rowIndex);
                int h_cost = vehicleInsurances.get("h_insurance") * hours / 2;
                totalCost -= h_cost;
                gridPane.add(new Label("-" + h_cost / 100 + ".00"),
                        4, rowIndex);
                rowIndex++;
            }

        }

        //add an empty line       
//        for (int colIndex = 0; colIndex < cols; colIndex++) {
//            gridPane.add(new Label("  "), colIndex, rowIndex);
//        }
//        rowIndex++;
//        for (int colIndex = 0; colIndex < cols; colIndex++) {
//            gridPane.add(new Label("--------------"), colIndex, rowIndex);
//        }
//        rowIndex++;
        int counter = 0;
        if (equipList != null && !equipList.isEmpty()) {
            for (String equipName : equipList) {

                //add an empty line
                for (int colIndex = 0; colIndex < cols; colIndex++) {
                    gridPane.add(new Label("  "), colIndex, rowIndex);
                }
                rowIndex++;
                for (int colIndex = 0; colIndex < cols; colIndex++) {
                    gridPane.add(new Label("--------------"), colIndex, rowIndex);
                }
                rowIndex++;

                HashMap<String, Integer> equipmentRates = getEquipmentRate(equipName);
                int quantity = equipQuantityList.get(counter);
                gridPane.add(new Label(equipName + "(" + quantity + ")"), 0, rowIndex);
                if (all_days > 0) {
                    gridPane.add(new Label(all_days + " day(s)"), 1, rowIndex);
                    gridPane.add(new Label(quantity + " x " + all_days + " x " + equipmentRates.get("d_rate") / 100 + ".00"),
                            2, rowIndex);
                    int d_rent = equipmentRates.get("d_rate") * all_days * quantity;
                    totalCost += d_rent;
                    gridPane.add(new Label(d_rent / 100 + ".00"),
                            4, rowIndex);
                    rowIndex++;
                }

                if (hours > 0) {
                    gridPane.add(new Label(hours + " hour(s)"), 1, rowIndex);
                    gridPane.add(new Label(quantity + " x " + hours + " x " + equipmentRates.get("h_rate") / 100 + ".00"),
                            2, rowIndex);
                    int h_rent = equipmentRates.get("h_rate") * hours * quantity;
                    totalCost += h_rent;
                    gridPane.add(new Label(h_rent / 100 + ".00"),
                            4, rowIndex);
                    rowIndex++;
                }

                counter++;
            }
        }

        //consider the redeemed points
        if (redeemedPoints > 0) {
            for (int colIndex = 0; colIndex < cols; colIndex++) {
                gridPane.add(new Label("--------------"), colIndex, rowIndex);
            }
            rowIndex++;
            gridPane.add(new Label("Redeem " + redeemedPoints + " points"), 0, rowIndex);
            gridPane.add(new Label("equivalent 1 day"), 1, rowIndex);

            gridPane.add(new Label("" + vehicleRates.get("d_rate") / 100 + ".00"),
                    2, rowIndex);
            int d_rate = vehicleRates.get("d_rate");
            totalCost -= d_rate;
            gridPane.add(new Label("-" + d_rate / 100 + ".00"),
                    4, rowIndex);
            rowIndex++;
        }

        // calculate the charge based on odometer
        if (vlicense != null) {
            int outstandingOdometer = odometer - getOdometer(vlicense)
                    - all_days * getDailyMileLimit(vehicleType);
            if (outstandingOdometer > 0) {
                //add an empty line       
                for (int colIndex = 0; colIndex < cols; colIndex++) {
                    gridPane.add(new Label("  "), colIndex, rowIndex);
                }
                rowIndex++;
                for (int colIndex = 0; colIndex < cols; colIndex++) {
                    gridPane.add(new Label("--------------"), colIndex, rowIndex);
                }
                rowIndex++;

                //
                gridPane.add(new Label("Exceed mileage limt"), 0, rowIndex);
                gridPane.add(new Label("by " + outstandingOdometer / 1000 + " km"), 0, rowIndex + 1);
                gridPane.add(new Label(outstandingOdometer + " x " + vehicleRates.get("pk_rate") / 100 + ".00"),
                        2, rowIndex);
                int pk_rent = vehicleRates.get("pk_rate") * outstandingOdometer;
                totalCost += pk_rent;
                gridPane.add(new Label(pk_rent + ".00"),
                        4, rowIndex);
                rowIndex++;
            }
        }
                rowIndex++;
        //lost equipments fees
        //overdue fees     
        ArrayList<String> rentList = new ArrayList<>();
        rentList = getExpDates(vlicense);
        String ExpectedDate = rentList.get(0);
        String ExpectedTime = rentList.get(1);
        LocalDate expdate = LocalDate.parse(ExpectedDate.trim());
        Integer exptime = Integer.parseInt(ExpectedTime.split(":")[0]);
        int over_days = total_day - (int) ChronoUnit.DAYS.between(fromDate, expdate);
        int over_hours = exptime - fromHour;
        if (over_hours < 0) {
            over_hours += 24;
            over_days -= 1;
            //but no overdue fees applied
        }

        for (int colIndex = 0; colIndex < cols; colIndex++) {
            gridPane.add(new Label("--------------"), colIndex, rowIndex-1);
        }
        if (over_days > 0) {
            gridPane.add(new Label("Overdue penalty"), 0, rowIndex);            
            gridPane.add(new Label(over_days + " overdue day(s)"), 1, rowIndex);
            gridPane.add(new Label(over_days + " x " + vehicleRates.get("d_rate") / 100 + ".00" + " x 10%"),
                    2, rowIndex);
            int over_rent = vehicleRates.get("d_rate") * over_days / 10;
            totalCost += over_rent;

            gridPane.add(new Label((over_rent) / 100 + ".00"),
                    4, rowIndex);
            rowIndex++;
        }

        //add the total sum
        rowIndex++;
        for (int colIndex = 0; colIndex < cols; colIndex++) {
            gridPane.add(new Label("--------------"), colIndex, rowIndex);
        }
        rowIndex++;
        gridPane.add(new Label("total: " + totalCost / 100 + ".00"), 4, rowIndex);
        return gridPane;
    }

    public boolean checkConNumber(String ConNumber) throws SQLException {
        String SQL = "SELECT * FROM reservation WHERE confirmation_number=" + ConNumber;
        ResultSet rs = queryDatabase(SQL);
        if (rs.next()) {
            return true;
        } else {
            return false;
        }

    }

    public HashMap<String, String> getReservationDetails(String ConNumber) {
        String SQL = "select * from reservation where confirmation_number=" + ConNumber
                + " and status = 'pending' ";
        return getOneRowAsHashMap(SQL);
    }

    public TableView getReservationsByPhone(String phone) {
        String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + " C.username as 'Customer', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + " R.branch_city as City, R.branch_location as Location"
                + " from customer C, reservation R "
                + " where C.username = R.customer_username "
                //+ " and R.branch_city = " + addQuotation(city)
                //+ " and R.branch_location = " + addQuotation(location)
                + " and C.phone = " + addQuotation(phone)
                + " and R.status = 'pending' ";
        System.out.println(SQL);
        return getTableViewForSQL(SQL);

    }

    public String getPhone(String username) {
        String SQL = "select phone from customer "
                + " where username = " + addQuotation(username);
        System.out.println(SQL);
        ResultSet rs = queryDatabase(SQL);
        String phone = null;
        try {
            if (rs.next()) {
                phone = rs.getString("phone");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phone;
    }

    // return the auto incremented id, return -1 if something went wrong
    public int createReservation(LocalDate pickup_date, int pickup_time,
            LocalDate return_date, int return_time, int estimation_cost,
            String branch_city, String branch_location, String customer_username,
            String status, String vehicleType) {
//pickup_date date,
//pickup_time integer,
//return_date date,
//return_time integer,
//estimation_cost integer,
//branch_city varchar(20),
//branch_location varchar(20),
//customer_username varchar(20),
//status varchar(20),
//vehicleType varchar(20),
        int confirmNo = -1;
        String sql = "insert into reservation "
                + " (pickup_date, pickup_time, return_date, return_time, estimation_cost, "
                + " branch_city, branch_location, customer_username, status, vehicleType)"
                + " values ( "
                + pickup_date.toString() + ", "
                + pickup_time + ", "
                + return_date.toString() + ", "
                + return_time + ", "
                + estimation_cost + ", "
                + addQuotation(branch_city) + ", "
                + addQuotation(branch_location) + ", "
                + addQuotation(customer_username) + ", "
                + " 'pending' " + ", "
                + addQuotation(vehicleType) + " )";
        System.out.println(sql);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                confirmNo = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }

        return confirmNo;
    }

    public ArrayList<String> getExpDates(String VehicleNumber) throws SQLException {
        String SQL = "select *"
                + " from rent"
                + " where rent.vlicense = " + addQuotation(VehicleNumber)
                + " and rent.rentid not in (select vreturn.rent_id from vreturn where vreturn.rent_id = rent.rentid and rent.vlicense = " + addQuotation(VehicleNumber) + ")";

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> rentList = new ArrayList<>();

        while (rs.next()) {
            String toDate = rs.getString("expected_return_date");
            String toTime = rs.getString("expected_return_time");
            rentList.add(toDate);
            rentList.add(toTime);
            rs.close();
            return rentList;

        }
        return null;

    }

}
