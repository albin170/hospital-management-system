package hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Manages the MySQL database connection.
 * Uses a single shared connection (Singleton pattern).
 */
public class DBConnection {

    // ---- Change these if your MySQL setup is different ----
    private static final String URL      = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER     = "root";
    private static final String PASSWORD = "";          // set your MySQL root password here
    // -------------------------------------------------------

    private static Connection connection = null;

    // Private constructor so nobody can create instances
    private DBConnection() {}

    /**
     * Returns the single shared database connection.
     * Creates it the first time it is called.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Add mysql-connector-j.jar to /lib");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    /** Closes the connection (call on application exit) */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
