/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
  

    public boolean addVehicleinRentAndInbranch(String username, String plateNumber, String city, String location, LocalDate date, String category, String type, String brand) throws SQLException {

        String isAvailable = "1";
        String manager = getManagerName(username);
        Integer odometer = 0;

        String addVehicleRent = "insert into vehicleforrent values (" + addQuotation(plateNumber)
                + "," + addQuotation(isAvailable) + "," + addQuotation(date.toString()) + ","
                + addQuotation(category) + "," + addQuotation(type) + "," + addQuotation(brand) + ","
                + addQuotation(manager) + "," + addQuotation(odometer.toString()) + ")";
        System.out.println(addVehicleRent);
        String addVehicleBranch = "insert into vehicleinbranch values (" + addQuotation(plateNumber)
                + "," + addQuotation(city) + "," + addQuotation(location) + ")";

        return updateDatabaseBatch(addVehicleBranch, addVehicleRent);

    }

    public String getManagerName(String username) {
        String SQL = "SELECT name FROM user WHERE username='" + username + "'";
        String name = "";
        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {
            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Error on Building Data");

        }

        return name;
    }

    public boolean checkPlateNumber(String plateNumber) {
        String SQL = "SELECT count(vlicense) FROM vehicleinbranch WHERE vlicense='" + plateNumber + "'";
        try (ResultSet rs = con.createStatement().executeQuery(SQL)) {
            System.out.println(SQL);

            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.print(rs.getInt(1));
                return true;

            } else {
                System.out.print(rs.getInt(1));
                return false;
            }

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Error on Building Data");

        }

        return true;
    }

    public boolean removeFromSale(String plateNumber) throws SQLException {

        String deleteVehicle = "delete from vehicleforsale where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicle);
        return updateDatabase(deleteVehicle);

    }

    public boolean removeFromSaleAndBranchAddtoSold(String username, String plateNumber, String type, String category, String brand, String odometer) throws SQLException {
        
        String manager = getManagerName(username);

        String deleteVehicleSale = "delete from vehicleforsale where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicleSale);

        String deleteVehicleBranch = "delete from vehicleinbranch where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicleBranch);

        String addSoldVehicle = "insert into vehiclesold values (" + addQuotation(plateNumber)
                + "," + addQuotation(LocalDate.now().toString()) + "," + addQuotation(type) + ","
                + addQuotation(category) + "," + addQuotation(brand) + "," + addQuotation(manager) + ","
                + addQuotation(odometer) + ")";
        System.out.println(" \n " + addSoldVehicle);

        return updateDatabaseBatch(deleteVehicleSale, deleteVehicleBranch, addSoldVehicle);

    }

    public boolean removeFromRent(String plateNumber) throws SQLException {

        String deleteVehicle = "delete from vehicleforrent where vlicense = " + addQuotation(plateNumber);
        System.out.println(" \n" + deleteVehicle);
        return updateDatabase(deleteVehicle);

    }

    public boolean addVehicleForSale(String plateNumber, String price, String date, String category, String brand, String type, String odometer) throws SQLException {

        String addVehicle = "insert into vehicleforsale values (" + addQuotation(plateNumber)
                + "," + addQuotation(price) + "," + addQuotation(date) + ","
                + addQuotation(category) + "," + addQuotation(brand) + "," + addQuotation(type) + ","
                + addQuotation(odometer) + ")";
        System.out.println(" \n " + addVehicle);

        return updateDatabase(addVehicle);

    }

    public boolean updateVehivleRates(String vehicleType, String weeklyRate, String dailylyRate, String hourlylyRate, String PkRate, String weeklyInsurance, String dailyInsurance, String hourlyInsurance) throws SQLException {

        String updateStatement = "update vehicletype set vehicletype.w_rate =" + addQuotation(weeklyRate) + ", vehicletype.d_rate =" + addQuotation(dailylyRate) + ", vehicletype.h_rate =" + addQuotation(hourlylyRate)
                + ", vehicletype.PK_rate =" + addQuotation(PkRate) + ", vehicletype.w_insurance =" + addQuotation(weeklyInsurance) + ", vehicletype.d_insurance =" + addQuotation(dailyInsurance)
                + ", vehicletype.h_insurance =" + addQuotation(hourlyInsurance)
                + "where vehicletype.typeName= " + addQuotation(vehicleType);

        System.out.println(" \n " + updateStatement);

        return updateDatabase(updateStatement);

    }

      public ArrayList<String> getVehicleType() {
        String SQL = "select distinct typeName from vehicletype VT"
                  + " order by typeName";
        ResultSet rs = queryDatabase(SQL);
        System.out.println(SQL);
        ArrayList<String> typeList = new ArrayList<>();
        try {
            while (rs.next()) {
                String type;
                type = rs.getString("typeName");
                typeList.add(type);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return typeList;
    }
    
    
    
}
