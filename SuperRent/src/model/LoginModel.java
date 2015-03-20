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
public class LoginModel {

    protected Connection con = null;
    protected MysqlConnection mysqlConnInstance = null;

    public LoginModel() {
        mysqlConnInstance = MysqlConnection.getInstance();
        con = MysqlConnection.getInstance().getConnection();
    }

    /**
     * refresh the database connection so that the info 
     */
    public void refeshDatabaseConnection() {
        con = mysqlConnInstance.refreshConnection();
    }

    /**
     * show a table -- as a test
     */
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
            System.out.println("Password"+pass);

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
            System.out.println("TYPE "+type);
            }
            rs.close();

        } 
       
   catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return type;
    }

}
