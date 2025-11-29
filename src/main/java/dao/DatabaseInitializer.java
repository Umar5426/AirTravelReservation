package main.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper class to initialize the database schema.
 * Creates the database and all required tables if they don't exist.
 * This ensures the Java application always has a matching database structure.
 */
public class DatabaseInitializer {

    /**
     * Initializes the database by creating it and all required tables.
     * Uses CREATE TABLE IF NOT EXISTS to avoid errors if tables already exist.
     */
    public static void initializeDatabase() {
        try (Connection conn = DBConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            // Create users table for authentication
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) UNIQUE NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "role VARCHAR(20) NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            // Create customers table matching Customer model
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS customers (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "customer_id VARCHAR(50) UNIQUE NOT NULL, " +
                "fname VARCHAR(100) NOT NULL, " +
                "lname VARCHAR(100) NOT NULL, " +
                "dob DATE, " +
                "username VARCHAR(50) UNIQUE, " +
                "password VARCHAR(255), " +
                "email VARCHAR(100), " +
                "phone VARCHAR(20), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            // Create flights table matching Flight model
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS flights (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "flight_id VARCHAR(50) UNIQUE NOT NULL, " +
                "flight_code VARCHAR(20) NOT NULL, " +
                "airline VARCHAR(100) NOT NULL, " +
                "flight_date DATE NOT NULL, " +
                "flight_duration VARCHAR(20), " +
                "departure_area_code VARCHAR(10) NOT NULL, " +
                "arrival_area_code VARCHAR(10) NOT NULL, " +
                "capacity INT DEFAULT 100, " +
                "price DECIMAL(10, 2) DEFAULT 0.00, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            // Create bookings/reservations table matching Reservation model
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS bookings (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "customer_id INT NOT NULL, " +
                "flight_id INT NOT NULL, " +
                "booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "status VARCHAR(20) DEFAULT 'CONFIRMED', " +
                "total_price DECIMAL(10, 2) NOT NULL, " +
                "FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE" +
                ")"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database: " + e.getMessage(), e);
        }
    }
}
