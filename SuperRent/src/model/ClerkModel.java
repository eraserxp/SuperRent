/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * 
 */
public class ClerkModel extends UserModel {
    
        public ClerkModel() {
        super();
    }
    
    
 public boolean checkConNumber(String ConNumber) throws SQLException{
     
     String SQL = "SELECT * FROM reservation WHERE confirmation_number="+ConNumber;
     ResultSet rs = queryDatabase(SQL);
     
     if(rs.next()){
         return true;
     }else{
         return false;
     }
     
 }   
  
 public ArrayList<String> getReservationDetails(String ConNumber) throws SQLException{
     String SQL = "select * from reservation where confirmation_number="+ConNumber;
     ResultSet rs = queryDatabase(SQL);
     ArrayList<String> reservationList = new ArrayList<>();
     
     if(rs.next()){
     String confirmationnumber = rs.getString("confirmation_number");
     Date pickupdate = rs.getDate("pickup_date");
     Time pickuptime = rs.getTime("pickup_time");
     Date returndate = rs.getDate("return_date");
     Time returntime = rs.getTime("return_time");
     Float estimationcost = rs.getFloat("estimation_cost");
     Integer vid = rs.getInt("vid");
     String branchcity = rs.getString("branch_city");
     String branchlocation = rs.getString("branch_location");
     String customerusername = rs.getString("customer_username");     
     String equipmenttype = rs.getString("equipment_type"); 
     Integer noofequipment= rs.getInt("no_of_equipment");
     String equipmentname = rs.getString("equip_name"); 
     
     reservationList.add(confirmationnumber);
     reservationList.add(pickupdate.toString());    
     reservationList.add(pickuptime.toString());  
     reservationList.add(returndate.toString()); 
     reservationList.add(returntime.toString());
     reservationList.add(estimationcost.toString());  
     reservationList.add(vid.toString());  
     reservationList.add(branchcity);  
     reservationList.add(branchlocation);  
     reservationList.add(customerusername);  
     reservationList.add(equipmenttype);   
     reservationList.add(noofequipment.toString());
     reservationList.add(equipmentname);
     
     rs.close();
     }
     return reservationList;
     
 }

    public TableView setupTableview(String connumber,String vehicletype,String category,String brand) throws SQLException {
        //To change body of generated methods, choose Tools | Templates.
    
        ObservableList<ObservableList> rdata;
        rdata = FXCollections.observableArrayList();

        TableView tableview = new TableView();
        String statement = null;
        boolean checkvalue = false;
        if(!connumber.isEmpty()){
        checkvalue = checkConNumber(connumber);
        }
        if(checkvalue==true){
        statement = "SELECT reservation.confirmation_number,vehicleforrent.category,vehicleforrent.vehicleType,vehicleforrent.brand from vehicleforrent,reservation where vehicleforrent.vid = reservation.vid && reservation.confirmation_number = " + connumber + ";";
        }else if(checkvalue==false&&!vehicletype.isEmpty()&&!category.isEmpty()&&!brand.isEmpty()){

        statement = "select category,vehicleType,brand from vehicleforrent where category = "+addQuotation(category)+"&&vehicleType = "+addQuotation(vehicletype)+"&&brand = "+addQuotation(brand)+";";    

        }else{
        System.out.println("please define the validator");
        }
        
        
        
        
        ResultSet rs = con.createStatement().executeQuery(statement);
        
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {

                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {

                        return new SimpleStringProperty(param.getValue().get(j).toString());

                    }

                });

                tableview.getColumns().addAll(col);

            }
        
        
        
                while (rs.next()) {

                ObservableList<String> row = FXCollections.observableArrayList();
                // for each row, we add every columns
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                    //System.out.println("testing");

                }
                // add each row into the data
                rdata.add(row);
                    System.out.println(rdata);
            }
                
            //add all rows into the tableview
            tableview.setItems(rdata);
            //close the result set
            rs.close();
        
        
        
        return tableview;
    
    
    }
    
    
    
    
    
    
    
    
}
