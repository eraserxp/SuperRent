/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;

/**
 *
 * 
 */
public class ClerkModel extends UserModel {
    
        public ClerkModel() {
        super();
    }
    
    
 public boolean checkConNumber(String ConNumber) throws SQLException{
     
     String SQL = "SELECT * FROM reservation WHERE confirmation_number="+ConNumber;
     ResultSet rs = queryDatabase(SQL);
     
     if(rs.next()){
         return true;
     }else{
         return false;
     }
     
 }   
  
 public ArrayList<String> getReservationDetails(String ConNumber) throws SQLException{
     String SQL = "select * from reservation where confirmation_number="+ConNumber;
     ResultSet rs = queryDatabase(SQL);
     ArrayList<String> reservationList = new ArrayList<>();
     
     if(rs.next()){
     String confirmationnumber = rs.getString("confirmation_number");
     Date pickupdate = rs.getDate("pickup_date");
     Time pickuptime = rs.getTime("pickup_time");
     Date returndate = rs.getDate("return_date");
     Time returntime = rs.getTime("return_time");
     Float estimationcost = rs.getFloat("estimation_cost");
     Integer vid = rs.getInt("vid");
     String branchcity = rs.getString("branch_city");
     String branchlocation = rs.getString("branch_location");
     String customerusername = rs.getString("customer_username");     
     String equipmenttype = rs.getString("equipment_type"); 
     Integer noofequipment= rs.getInt("no_of_equipment");
     String equipmentname = rs.getString("equip_name"); 
     
     reservationList.add(confirmationnumber);
     reservationList.add(pickupdate.toString());    
     reservationList.add(pickuptime.toString());  
     reservationList.add(returndate.toString()); 
     reservationList.add(returntime.toString());
     reservationList.add(estimationcost.toString());  
     reservationList.add(vid.toString());  
     reservationList.add(branchcity);  
     reservationList.add(branchlocation);  
     reservationList.add(customerusername);  
     reservationList.add(equipmenttype);   
     reservationList.add(noofequipment.toString());
     reservationList.add(equipmentname);
     
     rs.close();
     }
     return reservationList;
     
 }
    
    
}
