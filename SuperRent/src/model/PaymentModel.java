/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.MysqlConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    
    public Date MemberPayDate(String username) 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date payment_date=new Date();
        boolean isCMember;
        String date_return="";
                
        try {

            //select everything from the given table
            //String SQL = "SELECT type from user where username="+username+"and password="+password;

            String SQL = "SELECT isClubMember,payment_date FROM customer WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while(rs.next())
            {
            isCMember=rs.getBoolean("isClubMember");
            payment_date=rs.getDate("payment_date");
            
            if(isCMember==true)
                return payment_date;
            
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return payment_date;
    }
    
       
    public boolean isMembershipOverdue(String username)
    {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date payment_date=new Date();
        boolean isCMember;
        Date date_return;
        Date current_date;
        boolean valid=true;
        
        current_date=GetCurrentDate();
                
        try {

            String SQL = "SELECT isClubMember,payment_date FROM customer WHERE username='"+username+"'";
            //execute the sql statement and obtain the result
            ResultSet rs = con.createStatement().executeQuery(SQL);

            while(rs.next())
            {
            isCMember=rs.getBoolean("isClubMember");
            payment_date=rs.getDate("payment_date");
            
            date_return=GetReturnDate(payment_date);
            
            if(isCMember==true)
            {
                if(payment_date.compareTo(current_date)==-1 || payment_date.compareTo(current_date)==0)
                {
                    if(current_date.compareTo(date_return)==-1)
                    {
                        valid=false;
                        //System.out.println(current_date);
                        //System.out.println(date_return);
                        //System.out.println(current_date.compareTo(date_return));
                        return valid;
                    }
                    else 
                    {
                        valid =true;
                        //System.out.println("false");
                        return valid;
                    }
                        
                }
                    
                
            
            }
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return valid;
    }
    

    public boolean updateCustomer(String username, String name, String address)  {
        boolean result = false,result1=false;
             
        
        
        try {
        
        //System.out.println("Actual Date"+current_date);
         
        String updateCustomer = "update customer set address='" 
                + address + "',isClubMember=1,point=500,payment_date=CURRENT_DATE where username='"+username+"'"; //+ addQuotation(username);
        
        String updateUser = "update user set name='"+name+"'where username='"+username+"'";
        
         
        result=updateDatabase(updateUser);
        result1=updateDatabase(updateCustomer);
         
        return result&&result1;
        }
        
        catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        
        return false;
        
    }
public boolean updateCustomerMembership(String username)  {
        boolean result = false,result1=false;
             
        
        
        try {
        
        //System.out.println("Actual Date"+current_date);
         
        String updateCustomer = "update customer set isClubMember=0,point=0,payment_date=NULL where username='"+username+"'"; //+ addQuotation(username);
        
               
        
        result=updateDatabase(updateCustomer);
         
        return result;
        }
        
        catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        
        return false;
        
    }

   public Date GetCurrentDate() 
   {
  
	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   //get current date time with Date()
	   Date date = new Date();
	   //System.out.println((date));
 
	   return date;   
 
  }
  
   public Date GetReturnDate(Date payment_date)
   {
       Date date_return;
       Calendar cal = Calendar.getInstance();
	cal.setTime(payment_date);
        cal.add(Calendar.YEAR,1);
        date_return=cal.getTime();
        
        return date_return;
   }
}
