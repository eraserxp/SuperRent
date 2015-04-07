/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.MysqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
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
public class PaymentModel extends UserModel{

    
     public boolean isMember(String username,String usertype) 
    { 
        boolean valid=false;
          
        try {

            String SQL = "SELECT isClubMember FROM customer WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                   valid=rs.getBoolean("isClubMember");
            }
           
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return valid;
    }
    
    public String isMemberPaid(String username,String usertype) 
    {
        String payment_date="";
                
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT payment_date FROM user WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while(rs.next()){
             
            //System.out.println("TYPE "+type);
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return payment_date;
    }
    
    public boolean isUserExist(String username) 
    {
       int count=0;
        
          
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT type FROM user WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

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
     
     return true;     
    }


    public boolean addCustomer(String username, String passwd, String name, String type,
            String phone, String address) {
        boolean result = false;
        String addUser = "insert into user values (" + addQuotation(username) + ","
                + addQuotation(passwd) + "," + addQuotation(name)
                + "," + addQuotation(type) + ")";
        String isRoadStar = "0";
        String isClubMember = "0";
        Integer point = 0;
        String addCustomer = "insert into customer values ( " + addQuotation(username) + ","
                + addQuotation(phone) + "," + addQuotation(address) + ", " 
                + isRoadStar + ", " + isClubMember + ", " + point.toString() + ")";

        return updateDatabaseBatch(addUser, addCustomer);
    }

    

}
