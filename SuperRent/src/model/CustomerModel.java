/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
}
