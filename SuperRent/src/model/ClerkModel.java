/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 *
 */
public class ClerkModel extends UserModel {

    public ClerkModel() {
        super();
    }

    public TableView<VehicleSelection> getAvailableVehicles(String city, String location,
            String vehicleType, String fromDate, String toDate) {
        String SQL = "select distinct VR.vlicense, VR.category, VR.vehicleType, VR.brand, VR.odometer"
                + " from vehicleforrent VR, vehicleinbranch VB"
                + " where vehicleType = " + addQuotation(vehicleType)
                + " and VR.isAvailable=1 "
                + " and VB.vlicense = VR.vlicense "
                + " and VB.city = " + addQuotation(city)
                + " and VB.location = " + addQuotation(location);

        System.out.println(SQL);

        TableView<VehicleSelection> tableView = new TableView<>();
        ArrayList< ArrayList<String>> resultMatrix = getMatrixForSQL(SQL);
        // parse the first row of the result
        ArrayList<String> firstRow = resultMatrix.get(0);
        ObservableList<VehicleSelection> data = FXCollections.observableArrayList();
        for (int i = 1; i < resultMatrix.size(); i++) {
            VehicleSelection vs = new VehicleSelection();
            ArrayList<String> row = resultMatrix.get(i);
            vs.setVlicense(row.get(0));
            vs.setCategory(row.get(1));
            vs.setVehicleType(row.get(2));
            vs.setBrand(row.get(3));
            vs.setOdometer(Integer.parseInt(row.get(4)) / 1000);
            data.add(vs);
        }
        TableColumn<VehicleSelection, String> vlicenseCol = new TableColumn("Plate No.");
        vlicenseCol.setCellValueFactory(cellData -> cellData.getValue().vlicenseProperty());

        TableColumn<VehicleSelection, String> categoryCol = new TableColumn("Category");
        categoryCol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        TableColumn<VehicleSelection, String> vehicleTypeCol = new TableColumn("Vehicle Type");
        vehicleTypeCol.setCellValueFactory(cellData -> cellData.getValue().vehicleProperty());

        TableColumn<VehicleSelection, String> brandCol = new TableColumn("Brand");
        brandCol.setCellValueFactory(cellData -> cellData.getValue().brandProperty());

        TableColumn<VehicleSelection, String> odometerCol = new TableColumn("Odometer(km)");
        odometerCol.setCellValueFactory(cellData -> cellData.getValue().odometerProperty());

        tableView.getColumns().addAll(vlicenseCol, categoryCol, vehicleTypeCol, brandCol, odometerCol);
        tableView.setItems(data);

        return tableView;

    }

    public ArrayList<String> getRentDetails(String VehicleNumber) throws SQLException {
        String SQL = "select *"
                + " from rent"
                + " where rent.vlicense = " + addQuotation(VehicleNumber)
                + " and rent.rentid not in (select vreturn.rent_id from vreturn where vreturn.rent_id = rent.rentid and rent.vlicense = " + addQuotation(VehicleNumber) + ")";

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> rentList = new ArrayList<>();

        while (rs.next()) {
//            String vlicense = rs.getString("vlicense");
//            Boolean is_reserve = rs.getBoolean("is_reserve");
//            String driver_license = rs.getString("driver_license");
//            String pickuptimestr = String.format("%02d:00", pickuptime);
            String customerusername = rs.getString("customer_username");
            String branchcity = rs.getString("branch_city");
            String branchlocation = rs.getString("branch_location");
            Date pickupdate = rs.getDate("from_date");
            Integer pickuptime = rs.getInt("from_time");
            String rentid = rs.getString("rentid");
            String toDate = rs.getString("expected_return_date");
            String toTime = rs.getString("expected_return_time");
            String fromDate = rs.getString("from_date");

//            String equipmentname = rs.getString("equip_name");
//            String equipmenttype = rs.getString("equipment_type");
//            Integer noofequipment = rs.getInt("no_of_equipment");
//            String cardnumber = rs.getString("card_no");
//            String expirydate = rs.getString("expiry_date");
//            rentList.add(vlicense);
//            rentList.add(driver_license);
//            rentList.add(pickuptimestr);
            rentList.add(customerusername);
            rentList.add(branchcity);
            rentList.add(branchlocation);
            rentList.add(pickupdate.toString());
            rentList.add(pickuptime.toString());
            rentList.add(rentid);
            rentList.add(toDate);
            rentList.add(toTime);
            rentList.add(fromDate);
            rs.close();
            return rentList;
//            rentList.add(equipmentname);
//            rentList.add(noofequipment.toString());
        }
        return null;

    }

    public ArrayList<String> getClerkDetails(String username) throws SQLException {
        String SQL = "select branch_city,branch_location"
                + " from clerk"
                + " where clerk.username = " + addQuotation(username);

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> clerkbranch = new ArrayList<>();
        while (rs.next()) {
            String city = rs.getString("branch_city");
            String location = rs.getString("branch_location");

            clerkbranch.add(city);
            clerkbranch.add(location);

        }
        rs.close();
        return clerkbranch;

    }

    public ArrayList<String> getEquipmentDetails(String rentid) throws SQLException {

        String SQL = "select equipName,quantity"
                + " from rent_addon"
                + " where rentid = " + addQuotation(rentid);

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> equipments = new ArrayList<>();
        while (rs.next()) {
            String equipName = rs.getString("equipName");
            String quantity = rs.getString("quantity");

            equipments.add(equipName);
            equipments.add(quantity);

        }
        rs.close();
        return equipments;

    }

    public String getVehicleType(String Vlicense) throws SQLException {

        String SQL = "select vehicleType"
                + " from vehicleforrent"
                + " where vlicense = " + addQuotation(Vlicense);

        ResultSet rs = queryDatabase(SQL);
        if (rs.next()) {
            String vehicletype = rs.getString("vehicleType");
            return vehicletype;
        }
        return null;
    }

}
