/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 *
 */
public class ManagerModel extends UserModel {

    int countTruckVanoucver = 0;
    int countTruckToronto = 0;
    int countCarVancouver = 0;
    int countCarToronto = 0;

    int amountTruckVanoucver = 0;
    int amountTruckToronto = 0;
    int amountCarVancouver = 0;
    int amountCarToronto = 0;

    public ManagerModel() {
        super();
    }

    public boolean addVehicleinRentAndInbranch(String username, String plateNumber, String city, String location, LocalDate date, String category, String type, String brand) throws SQLException {

        String isAvailable = "1";
        String manager = getManagerName(username);
        Integer odometer = 0;

        String addVehicleRent = "insert into vehicleforrent values (" + addQuotation(plateNumber)
                + "," + addQuotation(isAvailable) + "," + addQuotation(date.toString()) + ","
                + addQuotation(category) + "," + addQuotation(type) + "," + addQuotation(brand) + ","
                + addQuotation(manager) + "," + addQuotation(odometer.toString()) + ")";
        System.out.println(addVehicleRent);
        String addVehicleBranch = "insert into vehicleinbranch values (" + addQuotation(plateNumber)
                + "," + addQuotation(city) + "," + addQuotation(location) + ")";

        return updateDatabaseBatch(addVehicleBranch, addVehicleRent);

    }

    public String getManagerName(String username) {
        String SQL = "SELECT name FROM user WHERE username='" + username + "'";
        String name = "";
        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {
            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Error on Building Data");

        }

        return name;
    }

    public boolean checkPlateNumber(String plateNumber) {

        // String SQL = "SELECT count(vlicense) FROM vehicleinbranch WHERE vlicense='" + plateNumber + "'";
        int count = 0;
        String SQL = "select vlicense from vehicleinbranch union select  vlicense from  vehiclesold";

        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {
            System.out.println(SQL);

            while (rs.next()) {
                if (rs.getString(1).equals(plateNumber)) {
                    count++;
                }

            }

            if (count > 0) {
                System.out.print(count);
                return true;

            } else {
                System.out.print(count);
                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return true;
    }

    public boolean removeFromSaleAndBranchAddtoSold(String username, String plateNumber, String type, String category, String brand, String odometer, String price) throws SQLException {

        String manager = getManagerName(username);

        String deleteVehicleSale = "delete from vehicleforsale where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicleSale);

        String deleteVehicleBranch = "delete from vehicleinbranch where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicleBranch);

        String addSoldVehicle = "insert into vehiclesold values (" + addQuotation(plateNumber)
                + "," + addQuotation(price) + "," + addQuotation(LocalDate.now().toString()) + "," + addQuotation(type) + ","
                + addQuotation(category) + "," + addQuotation(brand) + "," + addQuotation(manager) + ","
                + addQuotation(odometer) + ")";
        System.out.println(" \n " + addSoldVehicle);

        return updateDatabaseBatch(deleteVehicleSale, deleteVehicleBranch, addSoldVehicle);

    }

    public boolean removeFromRent(String plateNumber) throws SQLException {

        String deleteVehicle = "delete from vehicleforrent where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicle);
        return updateDatabase(deleteVehicle);

    }

    public boolean addVehicleForSale(String plateNumber, String price) throws SQLException {

        int count = 0;
        String SQL = "select vlicense,starting_date,category,vehicletype,brand,odometer  from vehicleforrent where vlicense =" + addQuotation(plateNumber) + ";";

        String addForSaleVehicle = "";

        System.out.println(SQL);

        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {

            // rs.getString("name");
            while (rs.next()) {
                System.out.println(rs.getString("vlicense"));
                System.out.println(price);
                System.out.println(rs.getString("starting_date"));

                addForSaleVehicle = "insert into vehicleforsale values (" + addQuotation(rs.getString("vlicense"))
                        + "," + addQuotation(price) + "*100," + addQuotation(rs.getString("starting_date")) + "," + addQuotation(rs.getString("category")) + ","
                        + addQuotation(rs.getString("brand")) + "," + addQuotation(rs.getString("vehicletype")) + "," + addQuotation(rs.getString("odometer"))
                        + ")";

                System.out.println(addForSaleVehicle);
            }

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");
            return false;

        }

        return updateDatabase(addForSaleVehicle);

    }

    public boolean updateVehivleRates(String vehicleType, String weeklyRate, String dailylyRate, String hourlylyRate, String PkRate, String weeklyInsurance, String dailyInsurance, String hourlyInsurance, String mileLimit) throws SQLException {

        String updateStatement = "update vehicletype set ";

        if (!weeklyRate.equals("")) {

            updateStatement = updateStatement + " vehicletype.w_rate = " + addQuotation(weeklyRate) + "*100,";
        }

        if (!dailylyRate.equals("")) {

            updateStatement = updateStatement + " vehicletype.d_rate =" + addQuotation(dailylyRate) + "*100,";

        }
        if (!hourlylyRate.equals("")) {

            updateStatement = updateStatement + " vehicletype.h_rate =" + addQuotation(hourlylyRate) + "*100,";

        }
        if (!PkRate.equals("")) {

            updateStatement = updateStatement + " vehicletype.pk_rate =" + addQuotation(PkRate) + "*100,";

        }
        if (!weeklyInsurance.equals("")) {

            updateStatement = updateStatement + " vehicletype.w_insurance =" + addQuotation(weeklyInsurance) + "*100,";

        }
        if (!dailyInsurance.equals("")) {

            updateStatement = updateStatement + " vehicletype.h_insurance =" + addQuotation(dailyInsurance) + "*100,";

        }

        if (!hourlyInsurance.equals("")) {

            updateStatement = updateStatement + "vehicletype.d_insurance =" + addQuotation(hourlyInsurance) + "*100,";

        }
        if (!mileLimit.equals("")) {

            updateStatement = updateStatement + "vehicletype.milelimit =" + addQuotation(mileLimit) + "*100,";

        }

        updateStatement = updateStatement.substring(0, updateStatement.length() - 1) + " where vehicletype.typeName= " + addQuotation(vehicleType) + " ;";

        System.out.println(" \n " + updateStatement);

        return updateDatabase(updateStatement);

    }

    public ArrayList<String> getVehicleType() {
        String SQL = "select distinct typeName from vehicletype VT"
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

    public TableView getVehicles(String location,
            String category, String year, boolean locationCBSlected, boolean categoryCBSelected) throws IOException {
        TableView tableview;

        String SQL = "select vehicleinbranch.vlicense,vehicleinbranch.location,vehicleforrent.category,vehicleforrent.starting_date from"
                + " vehicleinbranch, vehicleforrent where  vehicleinbranch.vlicense=vehicleforrent.vlicense";

        if (!location.equals("")) {

            SQL = SQL + " and vehicleinbranch.location=" + addQuotation(location);
        }

        if (!category.equals("")) {
            SQL = SQL + " and vehicleforrent.category =" + addQuotation(category);

        }
        if (!year.equals("")) {
            SQL = SQL + " and DATEDIFF(CURDATE(),vehicleforrent.starting_date) >" + addQuotation(year) + "*365 ";

        }

        if (locationCBSlected && categoryCBSelected) {
            SQL = SQL + " order by vehicleinbranch.location,vehicleforrent.category ;";
        } else if (locationCBSlected && !categoryCBSelected) {
            SQL = SQL + " order by vehicleinbranch.location ;";

        } else if (!locationCBSlected && categoryCBSelected) {
            SQL = SQL + " order by vehicleforrent.category ;";

        } else if (!locationCBSlected && !categoryCBSelected) {
            SQL = SQL + " order by vehicleforrent.starting_date ;";

        }

        System.out.println(SQL);

        tableview = getTableViewForSQL(SQL);

        return tableview;

    }

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    private TableView tableview;

    //MAIN EXECUTOR
    //CONNECTION DATABASE
    public void buildData() {
        List<String> list = new ArrayList<String>();

        ObservableList<String> observableList = FXCollections.observableList(list);
        observableList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("change!");
            }
        });
        observableList.add("item one");
        list.add("item two");
        System.out.println("Size: " + observableList.size());

    }

    public TableView getRentalReports(String location) throws SQLException {
        TableView tableview;

        String SQL = "select rent.vlicense,rent.branch_location,rent.branch_city, vehicleforrent.category,rent.from_date"
                + " from rent, vehicleforrent"
                + " where rent.vlicense= vehicleforrent.vlicense"
                + " and rent.from_date= CURDATE() ";

        if (!location.equals("")) {
            SQL = SQL + " and rent.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.category ;";

        } else {

            SQL = SQL + " order by rent.branch_location,vehicleforrent.category ;";

        }

        System.out.println(SQL);

        tableview = getTableViewForSQL(SQL);

        return tableview;

    }

    public TableView getRetrunReports(String location) throws SQLException {
        TableView tableview;
        String SQL = "select rent.vlicense,vreturn.branch_location,vreturn.branch_city, vehicleforrent.category,vreturn.return_date "
                + "from vreturn, vehicleforrent, rent"
                + " where rent.vlicense= vehicleforrent.vlicense "
                + " and  vreturn.rent_id=rent.rentid  "
                + " and  vreturn.return_date= CURDATE() ";

        if (location.equals("")) {
            SQL = SQL + " order by vreturn.branch_location,vehicleforrent.category ;";

        } else {

            SQL = SQL + " and vreturn.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.category ;";

        }

        System.out.println(SQL);

        tableview = getTableViewForSQL(SQL);

        return tableview;

    }

    public int[] countRentVehicles(String location) throws SQLException {

        String SQL = "select rent.vlicense,rent.branch_location,rent.branch_city, vehicleforrent.category,rent.from_date"
                + " from rent, vehicleforrent"
                + " where rent.vlicense= vehicleforrent.vlicense"
                + " and rent.from_date= CURDATE() ";

        if (location.equals("")) {
            SQL = SQL + " order by rent.branch_location,vehicleforrent.category ;";

        } else {

            SQL = SQL + " and rent.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.category ;";

        }

        System.out.print("\n" + SQL);
        System.out.print("\ncountVehicle");

        countTruckVanoucver = 0;
        countTruckToronto = 0;
        countCarVancouver = 0;
        countCarToronto = 0;

        amountTruckVanoucver = 0;
        amountTruckToronto = 0;
        amountCarVancouver = 0;
        amountCarToronto = 0;

        int[] counts = new int[8];

        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getString(4).toString());
                System.out.println("\n" + rs.getString(3).toString());

                if (rs.getString(4).equalsIgnoreCase("truck") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    countTruckVanoucver++;

                }
                if (rs.getString(4).equalsIgnoreCase("truck") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    countTruckToronto++;
                }
                if (rs.getString(4).equalsIgnoreCase("car") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    countCarToronto++;
                }
                if (rs.getString(4).equalsIgnoreCase("car") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    countCarVancouver++;
                }

            }

            counts[0] = countTruckVanoucver;
            counts[1] = countCarVancouver;
            counts[2] = countTruckToronto;
            counts[3] = countCarToronto;

            counts[4] = amountTruckVanoucver;
            counts[5] = amountTruckToronto;
            counts[6] = amountCarToronto;
            counts[7] = amountCarVancouver;

            for (int i = 0; i < 4; i++) {
                System.out.print("\n" + counts[i]);

            }

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return counts;

    }

    public int[] countReturnVehicles(String location) throws SQLException {

        String SQL = "select rent.vlicense,vreturn.branch_location,vreturn.branch_city, vehicleforrent.category,vreturn.return_date,vreturn.total_cost "
                + "from vreturn, vehicleforrent, rent"
                + " where rent.vlicense= vehicleforrent.vlicense "
                + " and  vreturn.rent_id=rent.rentid  "
                + " and  vreturn.return_date= CURDATE() ";

        if (location.equals("")) {
            SQL = SQL + " order by vreturn.branch_location,vehicleforrent.category ;";

        } else {

            SQL = SQL + " and vreturn.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.category ;";

        }

        System.out.print("\n" + SQL);
        System.out.print("\nReturnVehicle");
        System.out.print(location);

        countTruckVanoucver = 0;
        countTruckToronto = 0;
        countCarVancouver = 0;
        countCarToronto = 0;

        amountTruckVanoucver = 0;
        amountTruckToronto = 0;
        amountCarVancouver = 0;
        amountCarToronto = 0;

        int[] counts = new int[8];

        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getString(4).toString());
                // System.out.println("\n"+rs.getString(3).toString());

                if (rs.getString(4).equalsIgnoreCase("truck") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    countTruckVanoucver++;
                    amountTruckVanoucver += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("truck") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    countTruckToronto++;
                    amountTruckToronto += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("car") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    countCarToronto++;
                    amountCarToronto += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("car") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    countCarVancouver++;
                    amountCarVancouver += rs.getInt(6);
                }

            }

            counts[0] = countTruckVanoucver;
            counts[1] = countCarVancouver;
            counts[2] = countTruckToronto;
            counts[3] = countCarToronto;

            counts[4] = amountTruckVanoucver / 100;
            counts[5] = amountTruckToronto / 100;
            counts[6] = amountCarVancouver / 100;
            counts[7] = amountCarToronto / 100;

            for (int i = 0; i < 4; i++) {
                System.out.print("\n" + counts[i]);

            }

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return counts;

    }

}
