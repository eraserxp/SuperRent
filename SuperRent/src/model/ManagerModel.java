/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class ManagerModel extends UserModel {
    
    
    public ManagerModel() {
        super();
    }
    

    
    
    /*
    create table vehicleforrent
(vid integer not null,
isAvailable boolean,
starting_date date,
category varchar(20),
vehicleType varchar(20),
manager varchar(20),
PRIMARY KEY(vid),
index vehicleType_ind (vehicleType),
FOREIGN KEY(vehicleType) REFERENCES vehicletype(typeName),
index manager_ind (manager),
FOREIGN KEY(manager) REFERENCES manager(userName)
)
    */
    public boolean addVehicle(String category,String type, LocalDate date, String vlicense ) {
       
        
        int isAvailable=1;
        String manager= "Nakisa";
        
        String addVehicle = "insert into vehicleforrent values ("+ addQuotation(vlicense) 
                + "," + isAvailable+ "," + date + ","
                + addQuotation(category) + "," + addQuotation(type)+ ","
                + addQuotation(manager) + ")";
      
        return updateDatabaseBatch(addVehicle);
    }
    
    
    
    
    
}