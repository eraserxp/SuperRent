/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;

/**
 *
 * 
 */
public class CustomerModel extends UserModel {
    
    public String getPassword(String username) {
        String SQL = "select password from user "
                + " where username = " + addQuotation(username);
        //System.out.println(SQL);
        ResultSet rs = queryDatabase(SQL);
        String password = null;
        try {
            if (rs.next()) {
                password = rs.getString("password");
                //System.out.println("Password "+password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }
    
    public boolean changePhone(String username, String phone) {
        String changePhone = "update customer set phone = " + addQuotation(phone)
                + " where username = " + addQuotation(username);
        return updateDatabase(changePhone);
    }
    
    public boolean changeAddress(String username, String address) {
        String changeAddress = "update customer set address = " + addQuotation(address)
                + " where username = " + addQuotation(username);
        return updateDatabase(changeAddress);
    }
    
    
        
    public boolean isReservationExist(String user_name) throws SQLException {
        String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"'";
        System.out.println(SQL);
        
        try {
            
        int count=0;
        ResultSet rs=queryDatabase(SQL);
        
        while(rs.next()){
            count++;
            }
            
            rs.close();
            if(count==0)
                return false;
            else 
                return true;
           }
           
           catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }
     return false;

    }
    
    public boolean isReservationbyStatusExist(String user_name,String status) throws SQLException {
         String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"' and R.status='"+status+"'";
        System.out.println(SQL);
        
        try {
            
        int count=0;
        ResultSet rs=queryDatabase(SQL);
        
        while(rs.next()){
            count++;
            }
            
            rs.close();
            if(count==0)
                return false;
            else 
                return true;
           }
           
           catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }
     return false;

    }
    
    public boolean isReservationbyCNExist(String user_name,int confno) throws SQLException {
         String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"' and R.confirmation_number="+confno;
        System.out.println(SQL);
        
        try {
            
        int count=0;
        ResultSet rs=queryDatabase(SQL);
        
        while(rs.next()){
            count++;
            }
            
            rs.close();
            if(count==0)
                return false;
            else 
                return true;
           }
           
           catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }
     return false;

    }
    
    public TableView getReservationsbyCN (String user_name,int confno) {
         String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"' and R.confirmation_number="+confno;
        System.out.println(SQL);
        return getTableViewForSQL(SQL);

    }
    
    public boolean isReservationbyCNSExist(String user_name,int confno,String status) throws SQLException {
         String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"' and R.confirmation_number="+confno+" and R.status='"+status+"'";
        System.out.println(SQL);
        
        try {
            
        int count=0;
        ResultSet rs=queryDatabase(SQL);
        
        while(rs.next()){
            count++;
            }
            
            rs.close();
            if(count==0)
                return false;
            else 
                return true;
           }
           
           catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }
     return false;

    }
    
    public TableView getReservationsbyCNS (String user_name,int confno,String status) {
         String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"' and R.confirmation_number="+confno+" and R.status='"+status+"'";
        System.out.println(SQL);
        return getTableViewForSQL(SQL);

    }
    
     
    public TableView getReservationsbyStatus(String user_name,String status) {
         String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"' and R.status='"+status+"'";
        System.out.println(SQL);
        return getTableViewForSQL(SQL);

    }
    
    public TableView getallReservations(String user_name) {
        String SQL = "select R.confirmation_number as 'Confirmation No.', "
                + "R.pickup_date as 'Pickup date', "
                + " R.pickup_time as 'Pickup hour', "
                + "R.return_date as 'Return date', "
                + " R.return_time as 'Return hour', "
                + " R.branch_city as City, R.branch_location as Location, R.status as Status"
                + " from reservation R "
                + " where R.customer_username='"+user_name+"'";
        System.out.println(SQL);
        return getTableViewForSQL(SQL);

    }
    
    public boolean cancelreservation(String username,int confno, String status) throws SQLException {

        String deleteAddon = "delete from reserve_addon where confirmNo = " + confno;
        System.out.println(" \n" + deleteAddon);

        String changereserve = "update reservation set status ='cancelled'"
                + " where confirmation_number = " + confno;
        
        return updateDatabaseBatch(deleteAddon, changereserve);

    }
    
}
