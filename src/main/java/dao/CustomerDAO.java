package main.java.dao;

import main.java.model.Customer;
import java.sql.*;
import java.text.SimpleDateFormat;

public class CustomerDAO {

    private static final String DB_URL = "jdbc:sqlite:flight_reservation.db";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // -----------------------------
    // Add a new customer
    // -----------------------------
    public boolean addCustomer(String fname, String lname, java.util.Date dob, String username, String password) {
        // Create a new Customer object first (ID is auto-assigned)
        Customer newCustomer = new Customer(fname, lname, dob, username, password);

        String sql = "INSERT INTO Customer(customer_id, fname, lname, dob, username, password) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newCustomer.getCustomerID()); // use auto-assigned ID
            pstmt.setString(2, newCustomer.getFname());
            pstmt.setString(3, newCustomer.getLname());
            pstmt.setString(4, DATE_FORMAT.format(newCustomer.getDob()));
            pstmt.setString(5, newCustomer.getUsername());
            pstmt.setString(6, newCustomer.getPassword());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Edit customer details
    // -----------------------------
    public boolean editCustomer(String customerID, String fname, String lname, java.util.Date dob, String password) {
        String sql = "UPDATE Customer SET fname = ?, lname = ?, dob = ?, password = ? WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, DATE_FORMAT.format(dob));
            pstmt.setString(4, password);
            pstmt.setString(5, customerID);

            int updated = pstmt.executeUpdate();
            return updated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // View customer by username
    // -----------------------------
    public Customer viewCustomer(String username) {
        String sql = "SELECT * FROM Customer WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    Date dob = rs.getDate("dob");
                    String customerID = rs.getString("customer_id");
                    String user = rs.getString("username");
                    String pass = rs.getString("password");

                    return new Customer(fname, lname, dob, customerID, user, pass);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // -----------------------------
    // Fetch customer by ID
    // -----------------------------
    public Customer fetchById(String customerID) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    Date dob = rs.getDate("dob");
                    String user = rs.getString("username");
                    String pass = rs.getString("password");

                    return new Customer(fname, lname, dob, customerID, user, pass);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // -----------------------------
    // Login method
    // -----------------------------
    public Customer login(String username, String password) {
        String sql = "SELECT * FROM Customer WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    Date dob = rs.getDate("dob");
                    String customerID = rs.getString("customer_id");
                    String user = rs.getString("username");
                    String pass = rs.getString("password");

                    return new Customer(fname, lname, dob, customerID, user, pass);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // no match found
    }
}
