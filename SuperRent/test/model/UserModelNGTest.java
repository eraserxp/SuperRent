/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.MysqlConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.converter.LocalDateStringConverter;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author eraserxp
 */
public class UserModelNGTest {

    private static MysqlConnection database;
    private static UserModel userModel;

    public UserModelNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //connect to the database
        database = MysqlConnection.getInstance();
        if (database.connect("team04", "pr0j3ct!")) {
            System.out.println("Connect to database successfully!");
        }
        //create the user model
        userModel = new UserModel();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {

        //close the connection to database
        database.getConnection().close();
        System.out.println("Close the database connection.");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        //set up the data for the testing
        //add "test" as a customer for the testing
        AdminModel adminModel = new AdminModel();
        boolean addTestCustomerOK = adminModel.addCustomer("test1", "test", "test", "test", "test", "test");
        if (addTestCustomerOK) {
            System.out.println("Add 'test1' as a customer");
            System.out.println("Will use 'test1' for the following tests");
        }

    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        //remove the 'test' customer
        AdminModel adminModel = new AdminModel();
        boolean removeTestCustomerOK = adminModel.removeUser("test1");
        if (removeTestCustomerOK) {
            System.out.println("Remove the 'test1' customer");
        }
    }

    @Test
    public void testReservationWithEquips() {
        // TODO review the generated test code and remove the default call to fail.
        HashMap<String, String> reserveData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String pickupDateString = "2015-06-06";
        LocalDate pickupDate = LocalDate.parse(pickupDateString, formatter);

        reserveData.put("pickup_date", pickupDate.toString());
        reserveData.put("pickup_time", "8");
        String returnDateString = "2015-07-06";
        LocalDate returnDate = LocalDate.parse(returnDateString, formatter);

        reserveData.put("return_date", returnDateString);
        reserveData.put("return_time", "20");

        reserveData.put("estimation_cost", "20000");
        reserveData.put("branch_city", "Vancouver");
        reserveData.put("branch_location", "2660 Wesbrook Mall");
        reserveData.put("customer_username", "test1");
        reserveData.put("status", "pending");
        reserveData.put("vehicleType", "economy");

        int confirmation_number = userModel.createReservation(
                pickupDate,
                Integer.parseInt(reserveData.get("pickup_time")),
                returnDate,
                Integer.parseInt(reserveData.get("return_time")),
                reserveData.get("branch_city"),
                reserveData.get("branch_location"),
                reserveData.get("customer_username"),
                reserveData.get("status"),
                reserveData.get("vehicleType")
        );

        String SQL = "select * from reservation where confirmation_number = "
                + confirmation_number;
        HashMap<String, String> reserveRecord = userModel.getRecordAsHashMap(SQL);
        for (String key : reserveRecord.keySet()) {
            System.out.println("key: " + key + "   " + reserveRecord.get(key));
            if (key.equals("confirmation_number")) {
                assertEquals(Integer.parseInt(reserveRecord.get(key)), confirmation_number);
                continue;
            }
            assertTrue(reserveRecord.get(key).equals(reserveData.get(key)));
        }

        // now add some equipment associated with the reservation
        ArrayList<String> equipments = new ArrayList<>();
        equipments.add("child_safety_seat");
        equipments.add("ski_rack");
        ArrayList<Integer> equipmentQuantities = new ArrayList<>();
        equipmentQuantities.add(2);
        equipmentQuantities.add(1);

        userModel.createEquipReservation(confirmation_number, equipments, equipmentQuantities);
        SQL = " select * from reserve_addon where confirmNo = " + confirmation_number
                + " and equipName = 'child_safety_seat' ";
        HashMap<String, String> equip1Record = userModel.getRecordAsHashMap(SQL);
        assertEquals(equip1Record.get("confirmNo"), Integer.toString(confirmation_number));
        assertEquals(equip1Record.get("equipName"), "child_safety_seat");
        assertEquals(equip1Record.get("quantity"), "2");

        SQL = " select * from reserve_addon where confirmNo = " + confirmation_number
                + " and equipName = 'ski_rack' ";
        HashMap<String, String> equip2Record = userModel.getRecordAsHashMap(SQL);
        assertEquals(equip2Record.get("confirmNo"), Integer.toString(confirmation_number));
        assertEquals(equip2Record.get("equipName"), "ski_rack");
        assertEquals(equip2Record.get("quantity"), "1");

        //remove the reservation
        assertTrue(userModel.removeReservation(confirmation_number));

    }

    @Test
    public void testRentWithEquips() {
        HashMap<String, String> rentData = new HashMap<>();
        rentData.put("is_reserve", "0");
        rentData.put("driver_license", "WX12345");
        rentData.put("vlicense", "ABC-1");
        rentData.put("card_type", "Visa");
        rentData.put("card_no", "2253637808191235");
        rentData.put("expiry_date", "12/2019");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String from_date_string = "2015-06-06";
        LocalDate from_date = LocalDate.parse(from_date_string, formatter);

        rentData.put("from_date", from_date_string);
        rentData.put("from_time", "8");
        String expected_return_date_string = "2015-07-06";
        LocalDate expected_return_date = LocalDate.parse(expected_return_date_string, formatter);

        rentData.put("expected_return_date", expected_return_date_string);
        rentData.put("expected_return_time", "20");

//        rentData.put("estimation_cost", "20000");
        rentData.put("branch_city", "Vancouver");
        rentData.put("branch_location", "2660 Wesbrook Mall");
        rentData.put("customer_username", "test1");

        int rentid = userModel.createRent(
                Integer.parseInt(rentData.get("is_reserve")),
                rentData.get("driver_license"),
                rentData.get("vlicense"),
                rentData.get("branch_city"),
                rentData.get("branch_location"),
                rentData.get("customer_username"),
                rentData.get("card_type"),
                rentData.get("card_no"),
                rentData.get("expiry_date"),
                from_date,
                Integer.parseInt(rentData.get("from_time")),
                expected_return_date,
                Integer.parseInt(rentData.get("expected_return_time")),
                -1);

        String SQL = "select * from rent where rentid = "
                + rentid;
        HashMap<String, String> rentRecord = userModel.getRecordAsHashMap(SQL);
        for (String key : rentRecord.keySet()) {
            System.out.println("key: " + key + "   " + rentRecord.get(key));
            if (key.equals("rentid")) {
                assertEquals(Integer.parseInt(rentRecord.get(key)), rentid);
                continue;
            }
            assertTrue(rentRecord.get(key).equals(rentData.get(key)));
        }

        //now the vehicle should be unavailable
        assertFalse(userModel.isVehicleAvailable(rentData.get("vlicense")));

        // now add some equipment associated with the reservation
        ArrayList<String> equipments = new ArrayList<>();
        equipments.add("child_safety_seat");
        equipments.add("ski_rack");
        ArrayList<Integer> equipmentQuantities = new ArrayList<>();
        equipmentQuantities.add(2);
        equipmentQuantities.add(1);

        //find the old quantities
        ArrayList<Integer> oldQuantities = new ArrayList<>();
        for (String equipName : equipments) {
            int old = userModel.getEquipQuantity(rentData.get("branch_city"),
                    rentData.get("branch_location"),
                    equipName);
            oldQuantities.add(old);
        }

        userModel.createEquipRent(rentid, equipments, equipmentQuantities);
        SQL = " select * from rent_addon where rentid = " + rentid
                + " and equipName = 'child_safety_seat' ";
        HashMap<String, String> equip1Record = userModel.getRecordAsHashMap(SQL);
        assertEquals(equip1Record.get("rentid"), Integer.toString(rentid));
        assertEquals(equip1Record.get("equipName"), "child_safety_seat");
        assertEquals(equip1Record.get("quantity"), "2");

        SQL = " select * from rent_addon where rentid = " + rentid
                + " and equipName = 'ski_rack' ";
        HashMap<String, String> equip2Record = userModel.getRecordAsHashMap(SQL);
        assertEquals(equip2Record.get("rentid"), Integer.toString(rentid));
        assertEquals(equip2Record.get("equipName"), "ski_rack");
        assertEquals(equip2Record.get("quantity"), "1");

        //check the inventory of the equipments
        int new1 = userModel.getEquipQuantity(rentData.get("branch_city"),
                rentData.get("branch_location"),
                "child_safety_seat");
        assertEquals(new1 - oldQuantities.get(0), 2);

        int new2 = userModel.getEquipQuantity(rentData.get("branch_city"),
                rentData.get("branch_location"),
                "ski_rack");
        assertEquals(new2 - oldQuantities.get(1), 1);

        //remove the reservation
        assertTrue(userModel.removeRent(rentid));
        // make vehicle available
        assertTrue(userModel.setVehicleAvailability(rentData.get("vlicense"), "1"));
        // restore the equipment inventory to previous values
        assertTrue(userModel.updateEquipInventory(rentData.get("branch_city"),
                rentData.get("branch_location"),
                "child_safety_seat", -2)
        );

        assertTrue(userModel.updateEquipInventory(rentData.get("branch_city"),
                rentData.get("branch_location"),
                "ski_rack", -1)
        );
    }

}
