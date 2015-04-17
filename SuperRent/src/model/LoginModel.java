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
public class LoginModel extends UserModel{

    
     public boolean isValidCredentials(String username,String passwrd) 
    {
        String pass="";
        boolean valid=false;
          
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT password FROM user WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while(rs.next())
            {
                      
            pass=rs.getString("password");
            //System.out.println("Password"+pass);

            if(pass.equals(passwrd))
                valid=true;
            else
                valid=false;
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return valid;
    }
    
    public String getUserCredentials(String username,String password) 
    {
        String data;
        String type="";

          
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT type FROM user WHERE username='"+username+"' and password='"+password+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while(rs.next()){
            type = rs.getString("type");
            //System.out.println("TYPE "+type);
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return type;
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
//        String isRoadStar = "0";
//        String isClubMember = "0";
//        Integer point = 0;
//        String addCustomer = "insert into customer values ( " + addQuotation(username) + ","
//                + addQuotation(phone) + "," + addQuotation(address) + ", " 
//                + isRoadStar + ", " + isClubMember + ", " + point.toString() + ")";
        
        String isRoadStar = "0";
        String isClubMember = "0";
        Integer point = 0;
        String addCustomer = "insert into customer "
                + "( username, phone, address, isRoadStar, isClubMember, point, payment_date ) "
                + " values ( " + addQuotation(username) + ","
                + addQuotation(phone) + "," + addQuotation(address) + ", " 
                + isRoadStar + ", " + isClubMember + ", " + point.toString() + "," 
                + " null "
                + ")";

        return updateDatabaseBatch(addUser, addCustomer);
    }

    

}
