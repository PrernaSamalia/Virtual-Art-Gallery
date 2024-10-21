package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the database connection.
 * Provides methods to establish and close the connection to the MySQL database.
*/
public class DBConnUtil {

    private static Connection connection;

    /**
     * Retrieves the existing database connection or establishes a new one if none exists.
     * 
     * @return Connection object for interacting with the database.
    */
    public static Connection getConnection() {

        if (connection == null) {

            try {

                // Registered the MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Retrieved connection string from the properties file
                String connectionString = PropertyUtil.getPropertyString("resources/db.properties");

                // Established the database connection
                connection = DriverManager.getConnection(connectionString);
            }
            catch (ClassNotFoundException e) {

                System.out.println("MySQL JDBC Driver not found.");
                e.printStackTrace();
            }
            catch (SQLException e) {

                System.out.println("Connection Failed!");
                e.printStackTrace();
            }
        }

        return connection;
    }

    /**
     * Closes the database connection if it is open.
     * Ensures that the connection is properly released when no longer needed.
    */
    public static void dbClose() {

        try {

            if (connection != null) {
                
                connection.close();
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
    }
}