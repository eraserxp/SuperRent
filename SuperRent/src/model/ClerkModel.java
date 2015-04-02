/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.TableView;

/**
 *
 *
 */
public class ClerkModel extends UserModel {

    public ClerkModel() {
        super();
    }

    public TableView getSelectedVehicles() {
        String sql = "select vlicense, category, vehicleType, brand, odometer"
                + " from vehicleforrent where vehicleType = 'economy'";
        return getTableViewForSQL(sql);
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
