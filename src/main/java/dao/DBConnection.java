package main.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
// import java.sql.SQLException; // Not strictly used in the catch block type unless specific handling is added

public class DBConnection {

    // The single instance of the class (Lazy Loading)
    private static DBConnection instance;
    private Connection connection;

    // Database constants
    private static final String URL = "jdbc:mysql://localhost:3306/air_travel_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // Private constructor prevents external instantiation
    private DBConnection() {
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } 
        catch (Exception e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    // Public access point to the single instance
    public static DBConnection getInstance() {
        if (instance == null) {
            // Synchronized block for thread safety
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}