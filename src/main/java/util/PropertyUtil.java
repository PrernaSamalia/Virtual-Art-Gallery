package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read properties from a configuration file.
 * Provides a method to construct a database connection string
 * based on properties such as hostname, port, database name, username, and password.
*/
public class PropertyUtil {

    /**
     * Reads the properties from the specified file and constructs a database connection string.
     * 
     * @param propertyFileName the path to the properties file.
     * @return a connection string for the database, or null if an error occurs.
    */
    public static String getPropertyString(String propertyFileName) {

        // Create a Properties object to hold the key-value pairs from the configuration file
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(propertyFileName)) {

            // Loading properties from the specified file
            properties.load(input);

            // Retrieving the required properties
            String hostname = properties.getProperty("hostname");
            String port = properties.getProperty("port");
            String dbname = properties.getProperty("dbname");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            // Returning the connection string
            return "jdbc:mysql://" + hostname + ":" + port + "/" + dbname + "?user=" + username + "&password=" + password;
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }
}