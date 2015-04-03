/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import javafx.stage.Stage;
import view_presenter.SuperRent;

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
    
    private SuperRent mainApp = null;
    
    private HashMap<String, String> tmpData = new HashMap<>();

    public void setMainApp(SuperRent mainApp) {
        this.mainApp = mainApp;
    }
    
    // get the primary stage of the current application
    public Stage getPrimaryStage() {
        return mainApp.getPrimaryStage();
    }
    
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
    
    public void setTempData(String k, String v) {
        tmpData.put(k, v);
    }
    
    public String getTempData(String k) {
        return tmpData.get(k);
    }
    
    /**
     * it is the responsibility of the receiver to clear the tmp data
     */
    public void emptyTempData() {
        tmpData.clear();
    }

}
