/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.MysqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    
     public boolean isMember(String username) 
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
    
    public String MemberPayDate(String username,String usertype) throws ParseException 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date payment_date=sdf.parse("2010-01-01");
        boolean isCMember;
        String date_return="";
                
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT isClubMember,payment_date FROM user WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while(rs.next())
            {
            isCMember=rs.getBoolean("isClubMember");
            payment_date=rs.getDate("payment_date");
            
            if(isCMember==true)
                return payment_date.toString();
            
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return payment_date.toString();
    }
    
    public String isMemberPayDate(String username,String usertype) throws ParseException 
    {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date payment_date=dateFormat.parse("2009-12-31");
        boolean isCMember;
        Date date_return;
        Date current_date;
        
        Calendar cal = Calendar.getInstance();
	cal.setTime(payment_date);
        cal.add(Calendar.YEAR,1);
        date_return=cal.getTime();
        current_date=GetCurrentDate();
                
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT isClubMember,payment_date FROM user WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while(rs.next())
            {
            isCMember=rs.getBoolean("isClubMember");
            payment_date=rs.getDate("payment_date");
            
            if(isCMember==true)
            {
                
                return payment_date.toString();
            
            }
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return payment_date.toString();
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

   public Date GetCurrentDate() 
   {
  
	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println((date));
 
	   return date;   
 
  }
   
}
