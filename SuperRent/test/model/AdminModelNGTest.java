/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.MysqlConnection;
import java.util.HashMap;
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
public class AdminModelNGTest {

    private static MysqlConnection database;
    private static AdminModel adminModel;

    public AdminModelNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //connect to the database
        database = MysqlConnection.getInstance();
        if (database.connect("team04", "pr0j3ct!")) {
            System.out.println("Connect to database successfully!");
        }
        //create the user model
        adminModel = new AdminModel();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        //close the connection to database
        database.getConnection().close();
        System.out.println("Close the database connection.");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testAddRemoveCustomer() {
        // TODO 
        //create customer data for insertion
        HashMap<String, String> customerData = new HashMap<>();
        customerData.put("username", "someRandomName");
        customerData.put("password", "abcqwt256");
        customerData.put("name", "Some name");
        customerData.put("type", "customer");
        customerData.put("phone", "507-112-866");
        customerData.put("address", "3345 Maple St.");
        adminModel.addCustomer(customerData.get("username"), customerData.get("password"),
                customerData.get("name"), customerData.get("type"),
                customerData.get("phone"), customerData.get("address"));
        // test the user is there
        assertTrue(adminModel.isUserExisted(customerData.get("username")));
        assertTrue(adminModel.isCustomerExisted(customerData.get("username")));

        //test the user data is inserted correctly
        String SQL = "select * from user where username = '"
                + customerData.get("username") + "' ";
//        System.out.println(SQL);
        HashMap<String, String> userRecord = adminModel.getRecordAsHashMap(SQL);
        //iterate over keys
        for (String key : userRecord.keySet()) {
            assertTrue(userRecord.get(key).equals(customerData.get(key)));
        }

        SQL = "select * from customer where username = '"
                + customerData.get("username") + "' ";
        HashMap<String, String> customerRecord = adminModel.getRecordAsHashMap(SQL);
//        System.out.println(SQL);
        for (String key : customerRecord.keySet()) {
            System.out.println("key: " + key);
            if (key.equals("isRoadStar")) {
                System.out.println("isRoadStar " + customerRecord.get(key));
                assertEquals(Integer.parseInt(customerRecord.get(key)), 0);
                continue;
            }

            if (key.equals("isClubMember")) {
                assertEquals(Integer.parseInt(customerRecord.get(key)), 0);
                continue;
            }

            if (key.equals("point")) {
                assertEquals(Integer.parseInt(customerRecord.get(key)), 0);
                continue;
            }

            if (key.equals("isAnnualPaid")) {
                System.out.println("isAnnualPaid: " + customerRecord.get(key));
                assertTrue(customerRecord.get(key)==null);
                continue;
            }
            
            if (key.equals("payment_date")) {
                assertTrue(customerRecord.get(key)==null);
                continue;
            }
            
            assertTrue(customerRecord.get(key).equals(customerData.get(key)));
            
        }

        adminModel.removeUser(customerData.get("username"));
        assertFalse(adminModel.isCustomerExisted(customerData.get("username")));
        assertFalse(adminModel.isUserExisted(customerData.get("username")));
    }

    @Test
    public void testAddRemoveClerk() {
         //create customer data for insertion
        HashMap<String, String> clerkData = new HashMap<>();
        clerkData.put("username", "someClerk");
        clerkData.put("password", "anqhn3lybl");
        clerkData.put("name", "Some name");
        clerkData.put("type", "clerk");
        clerkData.put("branch_city", "Toronto");
        clerkData.put("branch_location", "300 Regina Street");
        adminModel.addClerk(clerkData.get("username"), clerkData.get("password"),
                clerkData.get("name"), clerkData.get("type"),
                clerkData.get("branch_city"), clerkData.get("branch_location"));
        // test the user is there
        assertTrue(adminModel.isUserExisted(clerkData.get("username")));
        assertTrue(adminModel.isClerkExisted(clerkData.get("username")));

        //test the user data is inserted correctly
        String SQL = "select * from user where username = '"
                + clerkData.get("username") + "' ";
//        System.out.println(SQL);
        HashMap<String, String> userRecord = adminModel.getRecordAsHashMap(SQL);
        //iterate over keys
        for (String key : userRecord.keySet()) {
            assertTrue(userRecord.get(key).equals(clerkData.get(key)));
        }

        SQL = "select * from clerk where username = '"
                + clerkData.get("username") + "' ";
        HashMap<String, String> clerkRecord = adminModel.getRecordAsHashMap(SQL);
//        System.out.println(SQL);
        for (String key : clerkRecord.keySet()) {
            System.out.println("key: " + key);            
            assertTrue(clerkRecord.get(key).equals(clerkData.get(key)));            
        }

        adminModel.removeUser(clerkData.get("username"));
        assertFalse(adminModel.isUserExisted(clerkData.get("username")));
        assertFalse(adminModel.isClerkExisted(clerkData.get("username")));
    }

}
