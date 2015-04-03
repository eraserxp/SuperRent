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
        String sql = "select vlicense, category, vehicleType, brand, odometer"
                + " from vehicleforrent where "
                + " isAvailable = 1 "
                + " and vehicleType = " + addQuotation(vehicleType)
                //+ " and "
                ;
        TableView<VehicleSelection> tableView = new TableView<>();
        ArrayList< ArrayList<String>> resultMatrix = getMatrixForSQL(sql);
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
            vs.setOdometer(Integer.parseInt(row.get(4))/1000);
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
        String SQL = "select * from rent where vlicense=" + addQuotation(VehicleNumber);
        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> reservationList = new ArrayList<>();

        if (rs.next()) {
            String vlicense = rs.getString("vlicense");
            Boolean is_reserve = rs.getBoolean("is_reserve");
            String driver_license = rs.getString("driver_license");
            Date pickupdate = rs.getDate("from_date");
            Integer pickuptime = rs.getInt("from_time");
            String pickuptimestr = String.format("%02d:00", pickuptime);
            String branchcity = rs.getString("branch_city");
            String branchlocation = rs.getString("branch_location");
            String customerusername = rs.getString("customer_username");
            String equipmentname = rs.getString("equip_name");
            String equipmenttype = rs.getString("equipment_type");
            Integer noofequipment = rs.getInt("no_of_equipment");
            String cardnumber = rs.getString("card_no");
            String expirydate = rs.getString("expiry_date");

            reservationList.add(vlicense);
            reservationList.add(driver_license);
            reservationList.add(pickupdate.toString());
            reservationList.add(pickuptimestr);
            reservationList.add(branchcity);
            reservationList.add(branchlocation);
            reservationList.add(customerusername);
            reservationList.add(equipmentname);
            reservationList.add(noofequipment.toString());

            rs.close();

            return reservationList;
        } else {
            return null;
        }

    }

}
