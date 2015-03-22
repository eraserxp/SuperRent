/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 *
 */
public class AppContext {

    private final static AppContext instance = new AppContext();

    public static AppContext getInstance() {
        return instance;
    }

    private String username;
    
    private String userType;

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }

}
