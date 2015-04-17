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

    int boxNumVan = 0;
    int carNumVan = 0;
    int foot12NumVan = 0;
    int foot15NumVan = 0;
    int foot24NumVan = 0;
    int stanNumVan = 0;
    int vanNumVan = 0;
    int comNumVan = 0;
    int ecNumVan = 0;
    int fullNumVan = 0;
    int luxNumVan = 0;
    int midNumVan = 0;
    int premNumVan = 0;
    int suvNumVan = 0;

    int boxNumTor = 0;
    int carNumTor = 0;
    int foot12NumTor = 0;
    int foot15NumTor = 0;
    int foot24NumTor = 0;
    int stanNumTor = 0;
    int vanNumTor = 0;
    int comNumTor = 0;
    int ecNumTor = 0;
    int fullNumTor = 0;
    int luxNumTor = 0;
    int midNumTor = 0;
    int premNumTor = 0;
    int suvNumTor = 0;

    int boxAmountVan = 0;
    int carAmountVan = 0;
    int foot12AmountVan = 0;
    int foot15AmountVan = 0;
    int foot24AmountVan = 0;
    int stanAmountVan = 0;
    int vanAmountVan = 0;
    int comAmountVan = 0;
    int ecAmountVan = 0;
    int fullAmountVan = 0;
    int luxAmountVan = 0;
    int midAmountVan = 0;
    int premAmountVan = 0;
    int suvAmountVan = 0;

    int boxAmountTor = 0;
    int carAmountTor = 0;
    int foot12AmountTor = 0;
    int foot15AmountTor = 0;
    int foot24AmountTor = 0;
    int stanAmountTor = 0;
    int vanAmountTor = 0;
    int comAmountTor = 0;
    int ecAmountTor = 0;
    int fullAmountTor = 0;
    int luxAmountTor = 0;
    int midAmountTor = 0;
    int premAmountTor = 0;
    int suvAmountTor = 0;

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

            updateStatement = updateStatement + "vehicletype.milelimit =" + addQuotation(mileLimit) + ",";

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

        String SQL = "select vehicleinbranch.vlicense,vehicleinbranch.location,vehicleforrent.vehicleType,vehicleforrent.starting_date from"
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
            SQL = SQL + " order by vehicleinbranch.location,vehicleforrent.vehicleType ;";
        } else if (locationCBSlected && !categoryCBSelected) {
            SQL = SQL + " order by vehicleinbranch.location ;";

        } else if (!locationCBSlected && categoryCBSelected) {
            SQL = SQL + " order by vehicleforrent.vehicleType;";

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

    public TableView getRentalReports(String location, LocalDate date) throws SQLException {
        TableView tableview;

        String SQL = "select rent.vlicense,rent.branch_location,rent.branch_city, vehicleforrent.vehicleType,rent.from_date"
                + " from rent, vehicleforrent"
                + " where rent.vlicense= vehicleforrent.vlicense";

        if (date == null) {

            SQL = SQL + " and rent.from_date= CURDATE() ";

        } else {
            SQL = SQL + " and rent.from_date=" + addQuotation(date.toString());

        }

        if (!location.equals("")) {
            SQL = SQL + " and rent.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.vehicleType ;";

        } else {

            SQL = SQL + " order by rent.branch_location,vehicleforrent.vehicleType ;";

        }

        System.out.println(SQL);

        tableview = getTableViewForSQL(SQL);

        return tableview;

    }

    public TableView getRetrunReports(String location, LocalDate date) throws SQLException {
        TableView tableview;
        String SQL = "select rent.vlicense,vreturn.branch_location,vreturn.branch_city, vehicleforrent.vehicleType,vreturn.return_date "
                + "from vreturn, vehicleforrent, rent"
                + " where rent.vlicense= vehicleforrent.vlicense "
                + " and  vreturn.rent_id=rent.rentid  ";
        if (date == null) {

            SQL = SQL + " and vreturn.return_date= CURDATE() ";

        } else {
            SQL = SQL + " and vreturn.return_date=" + addQuotation(date.toString());

        }

        if (location.equals("")) {
            SQL = SQL + " order by vreturn.branch_location,vehicleforrent.vehicleType ;";

        } else {

            SQL = SQL + " and vreturn.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.vehicleType ;";

        }

        System.out.println(SQL);

        tableview = getTableViewForSQL(SQL);

        return tableview;

    }

    public int[] countRentVehicles(String location, LocalDate date) throws SQLException {

        String SQL = "select rent.vlicense,rent.branch_location,rent.branch_city, vehicleforrent.vehicleType,rent.from_date"
                + " from rent, vehicleforrent"
                + " where rent.vlicense= vehicleforrent.vlicense";
        if (date == null) {

            SQL = SQL + " and rent.from_date= CURDATE() ";

        } else {
            SQL = SQL + " and rent.from_date=" + addQuotation(date.toString());

        }
        if (location.equals("")) {
            SQL = SQL + " order by rent.branch_location,vehicleforrent.vehicleType ;";

        } else {

            SQL = SQL + " and rent.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.vehicleType ;";

        }

        System.out.print("\n" + SQL);
        System.out.print("\ncountVehicle");

        boxNumVan = 0;
        carNumVan = 0;
        foot12NumVan = 0;
        foot15NumVan = 0;
        foot24NumVan = 0;
        stanNumVan = 0;
        vanNumVan=0;
        comNumVan = 0;
        ecNumVan = 0;
        fullNumVan = 0;
        luxNumVan = 0;
        midNumVan = 0;
        premNumVan = 0;
        suvNumVan = 0;

        boxNumTor = 0;
        carNumTor = 0;
        foot12NumTor = 0;
        foot15NumTor = 0;
        foot24NumTor = 0;
        stanNumTor = 0;
        vanNumTor = 0;
        comNumTor = 0;
        ecNumTor = 0;
        fullNumTor = 0;
        luxNumTor = 0;
        midNumTor = 0;
        premNumTor = 0;
        suvNumTor = 0;

        boxAmountVan = 0;
        carAmountVan = 0;
        foot12AmountVan = 0;
        foot15AmountVan = 0;
        foot24AmountVan = 0;
        stanAmountVan = 0;
        vanAmountVan = 0;
        comAmountVan = 0;
        ecAmountVan = 0;
        fullAmountVan = 0;
        luxAmountVan = 0;
        midAmountVan = 0;
        premAmountVan = 0;
        suvAmountVan = 0;

        boxAmountTor = 0;
        carAmountTor = 0;
        foot12AmountTor = 0;
        foot15AmountTor = 0;
        foot24AmountTor = 0;
        stanAmountTor = 0;
        vanAmountTor = 0;
        comAmountTor = 0;
        ecAmountTor = 0;
        fullAmountTor = 0;
        luxAmountTor = 0;
        midAmountTor = 0;
        premAmountTor = 0;
        suvAmountTor = 0;

        countTruckVanoucver = 0;
        countTruckToronto = 0;
        countCarVancouver = 0;
        countCarToronto = 0;

        amountTruckVanoucver = 0;
        amountTruckToronto = 0;
        amountCarVancouver = 0;
        amountCarToronto = 0;

        int[] counts = new int[60];

        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getString(4).toString());
                System.out.println("\n" + rs.getString(3).toString());

                if (rs.getString(4).equalsIgnoreCase("boxtrucks") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    boxNumVan++;

                }
                if (rs.getString(4).equalsIgnoreCase("cargovans") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    carNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("foot12") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    foot12NumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("foot15") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    foot15NumVan++;

                }
                if (rs.getString(4).equalsIgnoreCase("foot24") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    foot24NumVan++;
                }

                if (rs.getString(4).equalsIgnoreCase("compact") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    comNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("economy") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    ecNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("full-size") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    fullNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("luxury") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    luxNumVan++;

                }
                if (rs.getString(4).equalsIgnoreCase("midsize") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    midNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("premium") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    premNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("suv") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    suvNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("van") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    vanNumVan++;
                }
                if (rs.getString(4).equalsIgnoreCase("standard") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    stanNumVan++;
                }

                if (rs.getString(4).equalsIgnoreCase("boxtrucks") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    boxNumTor++;

                }
                if (rs.getString(4).equalsIgnoreCase("cargovans") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    carNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("foot12") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    foot12NumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("foot15") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    foot15NumTor++;

                }
                if (rs.getString(4).equalsIgnoreCase("foot24") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    foot24NumTor++;
                }

                if (rs.getString(4).equalsIgnoreCase("compact") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    comNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("economy") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    ecNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("full-size") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    fullNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("luxury") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    luxNumTor++;

                }
                if (rs.getString(4).equalsIgnoreCase("midsize") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    midNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("premium") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    premNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("suv") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    suvNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("van") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    vanNumTor++;
                }
                if (rs.getString(4).equalsIgnoreCase("standard") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    stanNumTor++;
                }

            }

            counts[0] = boxNumVan;
            counts[1] = carNumVan;
            counts[2] = foot12NumVan;
            counts[3] = foot15NumVan;
            counts[4] = foot24NumVan;
            counts[5] = stanNumVan;
            counts[6] = vanNumVan;
            counts[7] = comNumVan;
            counts[8] = ecNumVan;
            counts[9] = fullNumVan;
            counts[10] = luxNumVan;
            counts[11] = midNumVan;
            counts[12] = premNumVan;
            counts[13] = suvNumVan;

            counts[14] = boxNumTor;
            counts[15] = carNumTor;
            counts[16] = foot12NumTor;
            counts[17] = foot15NumTor;
            counts[18] = foot24NumTor;
            counts[19] = stanNumTor;
            counts[20] = vanNumTor;
            counts[21] = comNumTor;
            counts[22] = ecNumTor;
            counts[23] = fullNumTor;
            counts[24] = luxNumTor;
            counts[25] = midNumTor;
            counts[26] = premNumTor;
            counts[27] = suvNumTor;

            counts[28] = boxAmountVan / 100;
            counts[29] = carAmountVan / 100;
            counts[30] = foot12AmountVan / 100;
            counts[31] = foot15AmountVan / 100;
            counts[32] = foot24AmountVan / 100;
            counts[33] = stanAmountVan / 100;
            counts[34] = vanAmountVan / 100;
            counts[35] = comAmountVan / 100;
            counts[36] = ecAmountVan / 100;
            counts[37] = fullAmountVan / 100;
            counts[38] = luxAmountVan / 100;
            counts[39] = midAmountVan / 100;
            counts[40] = premAmountVan / 100;
            counts[41] = suvAmountVan / 100;

            counts[42] = boxAmountTor / 100;
            counts[43] = carAmountTor / 100;
            counts[44] = foot12AmountTor / 100;
            counts[45] = foot15AmountTor / 100;
            counts[46] = foot24AmountTor / 100;
            counts[47] = stanAmountTor / 100;
            counts[48] = vanAmountTor / 100;
            counts[49] = comAmountTor / 100;
            counts[50] = ecAmountTor / 100;
            counts[51] = fullAmountTor / 100;
            counts[52] = luxAmountTor / 100;
            counts[53] = midAmountTor / 100;
            counts[54] = premAmountTor / 100;
            counts[55] = suvAmountTor / 100;


            /*
             counts[0] = countTruckVanoucver;
             counts[1] = countCarVancouver;
             counts[2] = countTruckToronto;
             counts[3] = countCarToronto;

             counts[4] = amountTruckVanoucver;
             counts[5] = amountTruckToronto;
             counts[6] = amountCarToronto;
             counts[7] = amountCarVancouver;

             for (int i = 0; i < 26; i++) {
             System.out.print("\n" + counts[i]);

             }
                    
                    
             */
        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return counts;

    }

    public int[] countReturnVehicles(String location, LocalDate date) throws SQLException {

        String SQL = "select rent.vlicense,vreturn.branch_location,vreturn.branch_city, vehicleforrent.vehicleType,vreturn.return_date,vreturn.total_cost "
                + "from vreturn, vehicleforrent, rent"
                + " where rent.vlicense= vehicleforrent.vlicense "
                + " and  vreturn.rent_id=rent.rentid  ";
        if (date == null) {

            SQL = SQL + " and vreturn.return_date= CURDATE() ";

        } else {
            SQL = SQL + " and vreturn.return_date=" + addQuotation(date.toString());

        }

        if (location.equals("")) {
            SQL = SQL + " order by vreturn.branch_location,vehicleforrent.vehicleType ;";

        } else {

            SQL = SQL + " and vreturn.branch_location=" + addQuotation(location)
                    + "order by rent.branch_location,vehicleforrent.vehicleType ;";

        }

        System.out.print("\n" + SQL);
        System.out.print("\ncountVehicle");

        boxNumVan = 0;
        carNumVan = 0;
        foot12NumVan = 0;
        foot15NumVan = 0;
        foot24NumVan = 0;
        stanNumVan = 0;
        comNumVan = 0;
        vanNumVan=0;
        ecNumVan = 0;
        fullNumVan = 0;
        luxNumVan = 0;
        midNumVan = 0;
        premNumVan = 0;
        suvNumVan = 0;

        boxNumTor = 0;
        carNumTor = 0;
        foot12NumTor = 0;
        foot15NumTor = 0;
        foot24NumTor = 0;
        stanNumTor = 0;
        vanNumTor = 0;
        comNumTor = 0;
        ecNumTor = 0;
        fullNumTor = 0;
        luxNumTor = 0;
        midNumTor = 0;
        premNumTor = 0;
        suvNumTor = 0;

        boxAmountVan = 0;
        carAmountVan = 0;
        foot12AmountVan = 0;
        foot15AmountVan = 0;
        foot24AmountVan = 0;
        stanAmountVan = 0;
        vanAmountVan = 0;
        comAmountVan = 0;
        ecAmountVan = 0;
        fullAmountVan = 0;
        luxAmountVan = 0;
        midAmountVan = 0;
        premAmountVan = 0;
        suvAmountVan = 0;

        boxAmountTor = 0;
        carAmountTor = 0;
        foot12AmountTor = 0;
        foot15AmountTor = 0;
        foot24AmountTor = 0;
        stanAmountTor = 0;
        vanAmountTor = 0;
        comAmountTor = 0;
        ecAmountTor = 0;
        fullAmountTor = 0;
        luxAmountTor = 0;
        midAmountTor = 0;
        premAmountTor = 0;
        suvAmountTor = 0;

        countTruckVanoucver = 0;
        countTruckToronto = 0;
        countCarVancouver = 0;
        countCarToronto = 0;

        amountTruckVanoucver = 0;
        amountTruckToronto = 0;
        amountCarVancouver = 0;
        amountCarToronto = 0;

        int[] counts = new int[60];

        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getString(4).toString());
                System.out.println("\n" + rs.getString(3).toString());

                if (rs.getString(4).equalsIgnoreCase("boxtrucks") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    boxNumVan++;
                    boxAmountVan += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("cargovans") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    carNumVan++;
                    carAmountVan += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("foot12") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    foot12NumVan++;
                    foot12AmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("foot15") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    foot15NumVan++;
                    foot15AmountVan += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("foot24") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    foot24NumVan++;
                    foot24AmountVan += rs.getInt(6);
                }

                if (rs.getString(4).equalsIgnoreCase("compact") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    comNumVan++;
                    comAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("economy") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    ecNumVan++;
                    ecAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("full-size") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    fullNumVan++;
                    fullAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("luxury") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    luxNumVan++;
                    luxAmountVan += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("midsize") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    midNumVan++;
                    midAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("premium") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    premNumVan++;
                    premAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("suv") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    suvNumVan++;
                    suvAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("van") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    vanNumVan++;
                    vanAmountVan += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("standard") && rs.getString(3).equalsIgnoreCase("Vancouver")) {
                    stanNumVan++;
                    stanAmountVan += rs.getInt(6);
                }

                if (rs.getString(4).equalsIgnoreCase("boxtrucks") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    boxNumTor++;
                    boxAmountTor += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("cargovans") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    carNumTor++;
                    carAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("foot12") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    foot12NumTor++;
                    foot12AmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("foot15") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    foot15NumTor++;
                    foot15AmountTor += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("foot24") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    foot24NumTor++;
                    foot24AmountTor += rs.getInt(6);
                }

                if (rs.getString(4).equalsIgnoreCase("compact") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    comNumTor++;
                    comAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("economy") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    ecNumTor++;
                    ecAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("full-size") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    fullNumTor++;
                    fullAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("luxury") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    luxNumTor++;
                    luxAmountTor += rs.getInt(6);

                }
                if (rs.getString(4).equalsIgnoreCase("midsize") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    midNumTor++;
                    midAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("premium") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    premNumTor++;
                    premAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("suv") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    suvNumTor++;
                    suvAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("van") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    vanNumTor++;
                    vanAmountTor += rs.getInt(6);
                }
                if (rs.getString(4).equalsIgnoreCase("standard") && rs.getString(3).equalsIgnoreCase("Toronto")) {
                    stanNumTor++;
                    stanAmountTor += rs.getInt(6);
                }

            }

            counts[0] = boxNumVan;
            counts[1] = carNumVan;
            counts[2] = foot12NumVan;
            counts[3] = foot15NumVan;
            counts[4] = foot24NumVan;
            counts[5] = stanNumVan;
            counts[6] = vanNumVan;
            counts[7] = comNumVan;
            counts[8] = ecNumVan;
            counts[9] = fullNumVan;
            counts[10] = luxNumVan;
            counts[11] = midNumVan;
            counts[12] = premNumVan;
            counts[13] = suvNumVan;

            counts[14] = boxNumTor;
            counts[15] = carNumTor;
            counts[16] = foot12NumTor;
            counts[17] = foot15NumTor;
            counts[18] = foot24NumTor;
            counts[19] = stanNumTor;
            counts[20] = vanNumTor;
            counts[21] = comNumTor;
            counts[22] = ecNumTor;
            counts[23] = fullNumTor;
            counts[24] = luxNumTor;
            counts[25] = midNumTor;
            counts[26] = premNumTor;
            counts[27] = suvNumTor;

            counts[28] = boxAmountVan / 100;
            counts[29] = carAmountVan / 100;
            counts[30] = foot12AmountVan / 100;
            counts[31] = foot15AmountVan / 100;
            counts[32] = foot24AmountVan / 100;
            counts[33] = stanAmountVan / 100;
            counts[34] = vanAmountVan / 100;
            counts[35] = comAmountVan / 100;
            counts[36] = ecAmountVan / 100;
            counts[37] = fullAmountVan / 100;
            counts[38] = luxAmountVan / 100;
            counts[39] = midAmountVan / 100;
            counts[40] = premAmountVan / 100;
            counts[41] = suvAmountVan / 100;

            counts[42] = boxAmountTor / 100;
            counts[43] = carAmountTor / 100;
            counts[44] = foot12AmountTor / 100;
            counts[45] = foot15AmountTor / 100;
            counts[46] = foot24AmountTor / 100;
            counts[47] = stanAmountTor / 100;
            counts[48] = vanAmountTor / 100;
            counts[49] = comAmountTor / 100;
            counts[50] = ecAmountTor / 100;
            counts[51] = fullAmountTor / 100;
            counts[52] = luxAmountTor / 100;
            counts[53] = midAmountTor / 100;
            counts[54] = premAmountTor / 100;
            counts[55] = suvAmountTor / 100;
            /*
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
             */
        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return counts;

    }

}
