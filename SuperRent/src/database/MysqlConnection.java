/*
 * Connection to mysql database
 */
package database;

import java.sql.*; 
import java.util.logging.Level;
import java.util.logging.Logger;




/*
 * This class is a singleton class that provides methods 
 * to connect to an Oracle database, return the connection, 
 * set the connection, and determine whether or not the Oracle
 * JDBC driver has been loaded. To obtain a reference to an
 * instance of this class, use the getInstance() method.
 */ 
public class MysqlConnection {
    private static MysqlConnection _moc = null;
    private static String username;
    private static String password;
    protected Connection con = null;
    protected boolean driverLoaded = false;


    /*
     * The constructor is declared protected so that only subclasses
     * can access it.
     */ 
    protected MysqlConnection() {
	// empty
    }

    
    /*
     * Returns an instance of MvbOracleConnection
     */ 
    public static MysqlConnection getInstance() {
    	if (_moc == null) {
    		_moc = new MysqlConnection(); 
    	}

    	return _moc;
    }


    /* 
     * Loads the Oracle JDBC driver and connects to the database named ug using 
     * the given username and password.
     * Returns true if the connection is successful; false otherwise.
     */ 
    public boolean connect(String username, String password) {
    	try{
    		// change the url if the branch table is located somewhere else
    		String url = "jdbc:mysql://dbserver.mss.icics.ubc.ca/team04";

    		if (!driverLoaded) {
                  // Load the MySQL JDBC driver
                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    driverLoaded = true; 
    		}
                
    		con = DriverManager.getConnection(url, username, password);

    		con.setAutoCommit(false);
                
                //save username and password for later reconnection
                MysqlConnection.username = username;
                MysqlConnection.password = password;
    		return true; 
    	} catch (SQLException ex) {
    		return false; 
    	}
    }


    /*
     * Returns the connection
     */
    public Connection getConnection() {
    	return con; 
    }

    /**
     * 
     * close the current connection and returns a new connection
     */
    public Connection refreshConnection() {
        // close the current connection
        if (con!=null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MysqlConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //create a new connection
        if (this.connect(MysqlConnection.username, MysqlConnection.password)==true) {
            System.out.println("Successfully refesh the connection to database!");
            return con;
        } else {
            System.out.println("Failed to get an new connection to database!");
            return null;
        }

    }

    /*
     * Sets the connection
     */
    public void setConnection(Connection connect) {
    	con = connect; 
    }


    /*
     * Returns true if the driver is loaded; false otherwise
     */ 
    public boolean isDriverLoaded() {
    	return driverLoaded; 
    }


    /*
     * This method allows members of this class to clean up after itself and
     * before it is garbage collected. It is called by the garbage collector.
     */ 
    protected void finalize() throws Throwable {		
    	if (con != null) {
    		con.close();
    	}

    	// finalize() must call super.finalize() as the last thing it does
    	super.finalize();	
    }
    
}

