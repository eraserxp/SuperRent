/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static java.lang.Math.round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 *
 */
public class ClerkModel extends UserModel {

    public ClerkModel() {
        super();
    }

//    public ArrayList<String> getAvailableVehicles(String city, String location,
//            String vehicleType, String fromDate, String toDate) {
//        String SQL = "select distinct VR.vehicleType, VB.city, VB.location, "
//                + " VR.vlicense, VR.category,  VR.brand"
//                + " from vehicleforrent VR, vehicleinbranch VB "
//                + " where vehicleType = " + addQuotation(vehicleType)
//                + " and VR.isAvailable=1 "
//                + " and VB.vlicense = VR.vlicense "
//                + " and VB.city = " + addQuotation(city)
//                + " and VB.location = " + addQuotation(location);
//        
//    }
    public TableView<VehicleSelection> getAvailableVehicles(String city, String location,
            String vehicleType, String fromDate, String toDate) {
        String SQL = "select distinct VR.vlicense, VR.category, VR.vehicleType, VR.brand, VR.odometer"
                + " from vehicleforrent VR, vehicleinbranch VB"
                + " where vehicleType = " + addQuotation(vehicleType)
                + " and VR.isAvailable=1 "
                + " and VB.vlicense = VR.vlicense "
                + " and VB.city = " + addQuotation(city)
                + " and VB.location = " + addQuotation(location);

        System.out.println(SQL);

        TableView<VehicleSelection> tableView = new TableView<>();
        ArrayList< ArrayList<String>> resultMatrix = getMatrixForSQL(SQL);
        // parse the first row of the result
        ArrayList<String> firstRow = resultMatrix.get(0);
        ObservableList<VehicleSelection> data = FXCollections.observableArrayList();
        for (int i = 1; i < resultMatrix.size(); i++) {
            VehicleSelection vs = new VehicleSelection();
            ArrayList<String> row = resultMatrix.get(i);
            vs.setVlicense(row.get(0));
            vs.setCategory(row.get(1));
            vs.setVehicleType(row.get(2));
            vs.setBrand(row.get(3));
            vs.setOdometer(Integer.parseInt(row.get(4)) / 1000);
            data.add(vs);
        }
        TableColumn<VehicleSelection, String> vlicenseCol = new TableColumn("Plate No.");
        vlicenseCol.setCellValueFactory(cellData -> cellData.getValue().vlicenseProperty());

        TableColumn<VehicleSelection, String> categoryCol = new TableColumn("Category");
        categoryCol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        TableColumn<VehicleSelection, String> vehicleTypeCol = new TableColumn("Vehicle Type");
        vehicleTypeCol.setCellValueFactory(cellData -> cellData.getValue().vehicleProperty());

        TableColumn<VehicleSelection, String> brandCol = new TableColumn("Brand");
        brandCol.setCellValueFactory(cellData -> cellData.getValue().brandProperty());

        TableColumn<VehicleSelection, String> odometerCol = new TableColumn("Odometer(km)");
        odometerCol.setCellValueFactory(cellData -> cellData.getValue().odometerProperty());

        tableView.getColumns().addAll(vlicenseCol, categoryCol, vehicleTypeCol, brandCol, odometerCol);
        tableView.setItems(data);

        return tableView;

    }

    public ArrayList<String> getRentDetails(String VehicleNumber) throws SQLException {
        String SQL = "select *"
                + " from rent"
                + " where rent.vlicense = " + addQuotation(VehicleNumber)
                + " and rent.rentid not in (select vreturn.rent_id from vreturn where vreturn.rent_id = rent.rentid and rent.vlicense = " + addQuotation(VehicleNumber) + ")";

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> rentList = new ArrayList<>();

        while (rs.next()) {
//            String vlicense = rs.getString("vlicense");
//            Boolean is_reserve = rs.getBoolean("is_reserve");
//            String driver_license = rs.getString("driver_license");
//            String pickuptimestr = String.format("%02d:00", pickuptime);
            String customerusername = rs.getString("customer_username");
            String branchcity = rs.getString("branch_city");
            String branchlocation = rs.getString("branch_location");
            Date pickupdate = rs.getDate("from_date");
            Integer pickuptime = rs.getInt("from_time");
            String rentid = rs.getString("rentid");
            String toDate = rs.getString("expected_return_date");
            String toTime = rs.getString("expected_return_time");
            String fromDate = rs.getString("from_date");

//            String equipmentname = rs.getString("equip_name");
//            String equipmenttype = rs.getString("equipment_type");
//            Integer noofequipment = rs.getInt("no_of_equipment");
//            String cardnumber = rs.getString("card_no");
//            String expirydate = rs.getString("expiry_date");
//            rentList.add(vlicense);
//            rentList.add(driver_license);
//            rentList.add(pickuptimestr);
            rentList.add(customerusername);
            rentList.add(branchcity);
            rentList.add(branchlocation);
            rentList.add(pickupdate.toString());
            rentList.add(pickuptime.toString());
            rentList.add(rentid);
            rentList.add(toDate);
            rentList.add(toTime);
            rentList.add(fromDate);
            rs.close();
            return rentList;
//            rentList.add(equipmentname);
//            rentList.add(noofequipment.toString());
        }
        return null;

    }

    public ArrayList<String> getClerkDetails(String username) throws SQLException {
        String SQL = "select branch_city,branch_location"
                + " from clerk"
                + " where clerk.username = " + addQuotation(username);

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> clerkbranch = new ArrayList<>();
        while (rs.next()) {
            String city = rs.getString("branch_city");
            String location = rs.getString("branch_location");

            clerkbranch.add(city);
            clerkbranch.add(location);

        }
        rs.close();
        return clerkbranch;

    }

    public ArrayList<String> getEquipmentDetails(String rentid) throws SQLException {

        String SQL = "select equipName,quantity"
                + " from rent_addon"
                + " where rentid = " + addQuotation(rentid);

        ResultSet rs = queryDatabase(SQL);
        ArrayList<String> equipments = new ArrayList<>();
        while (rs.next()) {
            String equipName = rs.getString("equipName");
            String quantity = rs.getString("quantity");

            equipments.add(equipName);
            equipments.add(quantity);

        }
        rs.close();
        return equipments;

    }

    public String getVehicleType(String Vlicense) throws SQLException {

        String SQL = "select vehicleType"
                + " from vehicleforrent"
                + " where vlicense = " + addQuotation(Vlicense);

        ResultSet rs = queryDatabase(SQL);
        if (rs.next()) {
            String vehicletype = rs.getString("vehicleType");
            return vehicletype;
        }
        return null;
    }

    public Boolean createVreturn(Integer Rentid, LocalDate Returndate, Integer Returntime, String city, String location, Integer Tank, Integer odometer, String totalcost, String Payment_Method) {
        Integer returnid = -1;

        String sql;

        //This will result in errors if you click return button twice in a row
        //because the appcontext.settempdata is set in calculate cost once
        //so the second return button has no Total cost temp data
        //hence parseInt get null and cause errors
//        if (AppContext.getInstance().getTempData("amount") != null) {
//            total = Integer.parseInt(AppContext.getInstance().getTempData("TotalCost"));
//            total = total / 100;
//            TotalCost = total.toString();
//        }
//
//        //AppContext.getInstance().emptyTempData();
//        if (TotalCost != null) {
//            AppContext.getInstance().setTempData("amount", TotalCost);
//        }
        Boolean checkValue = checkredundantRent(Rentid.toString());

        //check wheter the rentid has been returned
//        if (!TotalCost.isEmpty() && !Payment_Method.isEmpty() &&checkValue == false) {
        if (checkValue == false) {
            sql = "insert into vreturn "
                    + " (rent_id, return_date, return_time, branch_city,"
                    + " branch_location, tank_full, odometer, total_cost,payment_method)"
                    + " values ("
                    + addQuotation(Rentid.toString()) + ", "
                    + addQuotation(Returndate.toString()) + ", "
                    + addQuotation(Returntime.toString()) + ", "
                    + addQuotation(city) + ", "
                    + addQuotation(location) + ", "
                    + addQuotation(Tank.toString()) + ", "
                    + addQuotation(odometer.toString()) + ", "
                    + addQuotation(totalcost) + ", "
                    + addQuotation(Payment_Method) + " )";

//        } else if (!TotalCost.isEmpty() && Payment_Method.isEmpty()&&checkValue == false) {
//            sql = "insert into vreturn "
//                    + " (rent_id, return_date, return_time, branch_city,"
//                    + " branch_location, tank_full, odometer, total_cost,payment_method)"
//                    + " values ( "
//                    + addQuotation(Rentid.toString()) + ", "
//                    + addQuotation(Returndate.toString()) + ", "
//                    + addQuotation(Returntime.toString()) + ", "
//                    + addQuotation(city) + ", "
//                    + addQuotation(location) + ", "
//                    + addQuotation(Tank.toString()) + ", "
//                    + addQuotation(odometer.toString())
//                    + addQuotation(TotalCost) + ", "
//                    + ", null)";
        } else {
//            sql = "insert into vreturn "
//                    + " (rent_id, return_date, return_time, branch_city,"
//                    + " branch_location, tank_full, odometer, total_cost,payment_method)"
//                    + " values ( "
//                    + addQuotation(Rentid.toString()) + ", "
//                    + addQuotation(Returndate.toString()) + ", "
//                    + addQuotation(Returntime.toString()) + ", "
//                    + addQuotation(city) + ", "
//                    + addQuotation(location) + ", "
//                    + addQuotation(Tank.toString()) + ", "
//                    + addQuotation(odometer.toString()) + ", null, null)";

            return false;

        }

        //payment method is set to Cash by default
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                returnid = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }

            if (stmt != null) {
                try {
                    con.commit();
                    stmt.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }
        }

        ArrayList<String> branchrentlist;
        branchrentlist = getRentBranch(Rentid);

        String rentcity = branchrentlist.get(0);
        String rentlocation = branchrentlist.get(1);
        String vlicense = branchrentlist.get(2);

        //set the vehicle to be available
        updatevehicleforrent(vlicense);

        if (!rentcity.equals(city) && !rentlocation.equals(location)) {
            //update the vehicleinbranch
            updatevehicleinbranch(vlicense, city, location);
            System.out.println("The vehicle is returned to a different  branch!");

        } else {
            //do nothing
            System.out.println("The vehicle is returned to the rent branch!");

        }

        return true;
    }

//    public ArrayList<String> getVehicleBranch(String Vehicle_No){
//        ArrayList<String> templist = new ArrayList<String>();
//        ArrayList<String> branchlist = new ArrayList<String>();
//        try {
//            templist = getRentDetails(Vehicle_No);
//        } catch (SQLException ex) {
//            Logger.getLogger(ClerkModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        branchlist.add(templist.get(1));
//        branchlist.add(templist.get(2));        
//        
//        return branchlist;
//        
//        
//     
//    }
    public ArrayList<String> getRentBranch(Integer rentid) {
        ArrayList<String> templist = new ArrayList<>();
        try {
            String SQL = "select *"
                    + " from rent"
                    + " where rentid = " + addQuotation(rentid.toString());

            ResultSet rs = queryDatabase(SQL);

            if (rs.next()) {
                String city = rs.getString("branch_city");
                String location = rs.getString("branch_location");
                String vlicense = rs.getString("vlicense");

                templist.add(city);
                templist.add(location);
                templist.add(vlicense);
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClerkModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return templist;
    }

    //method to set the vehicle to be available
    public void updatevehicleforrent(String vlicense) {
        String updateavailable = "update vehicleforrent set isAvailable = 1"
                + " where vlicense = " + addQuotation(vlicense);
        updateDatabase(updateavailable);

    }

    //method to update the address of the vehicleinbranch
    public void updatevehicleinbranch(String vlicense, String returncity, String returnlocation) {
        String updateaddress = "update vehicleinbranch set city = "
                + addQuotation(returncity)
                + ", location = "
                + addQuotation(returnlocation)
                + " where vlicense = "
                + addQuotation(vlicense);
        updateDatabase(updateaddress);
    }

    public ArrayList<String> getAllVehicleTypes() {
        String SQL = " select distinct typeName from vehicletype ";
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

    public TableView showAvailableVehicles(LocalDate fromDate, LocalDate toDate) {
        refeshDatabaseConnection();
        String SQL = "select * from ( "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB "
                + " where VB.vlicense = VR.vlicense "
                + " and not exists (select * from rent R where R.vlicense=VR.vlicense)"
                + "       "
                + " union "
                + "       "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB, rent R "
                + " where VR.isAvailable=0  and VB.vlicense = VR.vlicense "
                + " and VR.vlicense = R.vlicense "
                + " and "
                + " not exists "
                + " ( select * from rent R2 where R2.vlicense = R.vlicense "
                + "and " + addQuotation(fromDate.toString())
                + " between R2.from_date and R2.expected_return_date "
                + " or " + addQuotation(toDate.toString())
                + " between R2.from_date and R2.expected_return_date )"
                + " ) t "
                + " order by city, location, vehicleType ";

        System.out.println(SQL);

        return getTableViewForSQL(SQL);
    }

    public TableView showAvailableVehicles(String city, String location,
            LocalDate fromDate, LocalDate toDate) {
        refeshDatabaseConnection();
        String SQL = "select * from ( "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB "
                + " where VB.vlicense = VR.vlicense "
                + " and not exists (select * from rent R where R.vlicense=VR.vlicense)"
                + "       "
                + " union "
                + "       "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB, rent R "
                + " where VR.isAvailable=0  and VB.vlicense = VR.vlicense "
                + " and VR.vlicense = R.vlicense "
                + " and "
                + " not exists "
                + " ( select * from rent R2 where R2.vlicense = R.vlicense "
                + "and " + addQuotation(fromDate.toString())
                + " between R2.from_date and R2.expected_return_date "
                + " or " + addQuotation(toDate.toString())
                + " between R2.from_date and R2.expected_return_date )"
                + " ) t "
                + " where city = " + addQuotation(city)
                + " and location = " + addQuotation(location)
                + " order by city, location, vehicleType ";

        System.out.println(SQL);

        return getTableViewForSQL(SQL);
    }

    public TableView showAvailableVehicles(String vehicleType,
            LocalDate fromDate, LocalDate toDate) {
        refeshDatabaseConnection();
        String SQL = "select * from ( "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB "
                + " where VB.vlicense = VR.vlicense "
                + " and not exists (select * from rent R where R.vlicense=VR.vlicense)"
                + "       "
                + " union "
                + "       "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB, rent R "
                + " where VR.isAvailable=0  and VB.vlicense = VR.vlicense "
                + " and VR.vlicense = R.vlicense "
                + " and "
                + " not exists "
                + " ( select * from rent R2 where R2.vlicense = R.vlicense "
                + "and " + addQuotation(fromDate.toString())
                + " between R2.from_date and R2.expected_return_date "
                + " or " + addQuotation(toDate.toString())
                + " between R2.from_date and R2.expected_return_date )"
                + " ) t "
                + " where vehicleType = " + addQuotation(vehicleType)
                + " order by vehicleType, city, location";

        System.out.println(SQL);

        return getTableViewForSQL(SQL);
    }

    public TableView showAvailableVehicles(String city, String location,
            String vehicleType, LocalDate fromDate, LocalDate toDate) {
        refeshDatabaseConnection();
        String SQL = "select * from ( "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB "
                + " where VB.vlicense = VR.vlicense "
                + " and not exists (select * from rent R where R.vlicense=VR.vlicense)"
                + "       "
                + " union "
                + "       "
                + " select VB.city, VB.location, VR.vehicleType,  VR.vlicense, "
                + " VR.category,  VR.brand  "
                + " from vehicleforrent VR, vehicleinbranch VB, rent R "
                + " where VR.isAvailable=0  and VB.vlicense = VR.vlicense "
                + " and VR.vlicense = R.vlicense "
                + " and "
                + " not exists "
                + " ( select * from rent R2 where R2.vlicense = R.vlicense "
                + "and " + addQuotation(fromDate.toString())
                + " between R2.from_date and R2.expected_return_date "
                + " or " + addQuotation(toDate.toString())
                + " between R2.from_date and R2.expected_return_date )"
                + " ) t "
                + " where city = " + addQuotation(city)
                + " and location = " + addQuotation(location)
                + " and vehicleType = " + addQuotation(vehicleType)
                + " order by city, location, vehicleType ";

        System.out.println(SQL);

        return getTableViewForSQL(SQL);
    }

    public TableView showOverdueVehicles(String city, String location, String vehicleType) {
        refeshDatabaseConnection();
        LocalDate currentDate = LocalDate.now();
        String SQL = " select VB.city, VB.location, VR.vehicleType, "
                + " R.rentid, R.customer_username, VR.vlicense, "
                + "  R.expected_return_date, VR.category "
                + " from vehicleinbranch VB, vehicleforrent VR,  rent R"
                + " where R.expected_return_date < " + addQuotation(currentDate.toString())
                + " and R.vlicense = VR.vlicense and VR.vlicense = VB.vlicense "
                + " and VR.isAvailable = 0 ";
        if (city != null) {
            SQL += " and VB.city = " + addQuotation(city);
        }

        if (location != null) {
            SQL += " and VB.location = " + addQuotation(location);
        }

        if (vehicleType != null) {
            SQL += " and VR.vehicleType = " + addQuotation(vehicleType);
        }

        SQL += " order by VB.city, VB.location, VR.vehicleType ";
        System.out.println(SQL);
        return getTableViewForSQL(SQL);
    }

    public TableView showVehiclesForSale(String city, String location, String vehicleType) {
        refeshDatabaseConnection();
        String SQL = " select VB.city, VB.location, VS.vehicleType, "
                + " VS.vlicense, VS.brand, VS.category,  round(VS.odometer/1000, 1) as 'odometer (km)' , "
                + "  round(VS.price/100, 2) as price "
                + " from vehicleinbranch VB, vehicleforsale VS "
                + " where VS.vlicense = VB.vlicense ";
        if (city != null) {
            SQL += " and VB.city = " + addQuotation(city);
        }

        if (location != null) {
            SQL += " and VB.location = " + addQuotation(location);
        }

        if (vehicleType != null) {
            SQL += " and VS.vehicleType = " + addQuotation(vehicleType);
        }

        SQL += " order by VB.city, VB.location, VS.vehicleType ";
        System.out.println(SQL);
        return getTableViewForSQL(SQL);
    }

    public void updateEquipNum(String EquipName, String City, String Location, Integer ReturnNum) {
        //keep_equipment
        String updateKeppE = "update keep_equipment set quantity = quantity+" + addQuotation(ReturnNum.toString())
                + " where equipName = " + addQuotation(EquipName) + "AND city = " + addQuotation(City) + "AND location =" + addQuotation(Location);
        updateDatabase(updateKeppE);

    }

    public void updatePoint(Integer totalcost, String customer) {
        Integer points = round(totalcost / 5);

        String updateKeppE = "update customer set point = point+"
                + addQuotation(points.toString())
                + " where username = " + addQuotation(customer)
                + "and isClubMember = 1";
        updateDatabase(updateKeppE);

    }

    public void updateOdometer(String Odoreading, String vehicleNum) {

        String updateKeppE = "update vehicleforrent set odometer = "
                + addQuotation(Odoreading)
                + " where vlicense = " + addQuotation(vehicleNum);
        updateDatabase(updateKeppE);

    }

//    public void updateTankStatus(String tankfull, String vehicleNum) {
//
//        String updateKeppE = "update vehicleforrent set tank_full = "
//                + addQuotation(tankfull)
//                + " where vlicense = " + addQuotation(vehicleNum);
//        updateDatabase(updateKeppE);
//
//    }

    public Boolean checkredundantRent(String Rentid) {
        String SQL = "select returnid from vreturn where rent_id = "
                + addQuotation(Rentid);
        ResultSet rs = queryDatabase(SQL);
        try {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClerkModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
