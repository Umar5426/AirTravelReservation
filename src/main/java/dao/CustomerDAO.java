package main.java.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Customer;

public class CustomerDAO extends BaseDAO {

    /**
     * Inserts a new customer row.
     */
    public void addCustomer(String customerId,
                            String firstName,
                            String lastName,
                            java.util.Date dob,
                            String username,
                            String password,
                            String email,
                            String phone) throws SQLException {

        String sql = "INSERT INTO customers " +
                "(customer_id, fname, lname, dob, username, password, email, phone) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, customerId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            if (dob != null) {
                stmt.setDate(4, new Date(dob.getTime()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setString(5, username);
            stmt.setString(6, password);
            stmt.setString(7, email);
            stmt.setString(8, phone);
        });
    }

    /**
     * Updates all editable columns for a customer.
     */
    public void updateCustomer(int id,
                               String customerId,
                               String firstName,
                               String lastName,
                               java.util.Date dob,
                               String username,
                               String password,
                               String email,
                               String phone) throws SQLException {

        String sql = "UPDATE customers " +
                "SET customer_id=?, fname=?, lname=?, dob=?, username=?, password=?, email=?, phone=? " +
                "WHERE id=?";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, customerId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            if (dob != null) {
                stmt.setDate(4, new Date(dob.getTime()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setString(5, username);
            stmt.setString(6, password);
            stmt.setString(7, email);
            stmt.setString(8, phone);
            stmt.setInt(9, id);
        });
    }

    /**
     * Deletes a customer by database id.
     */
    public void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id=?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id));
    }

    /**
     * Returns all customers in a simple string form (temporary helper for GUI).
     */
    public List<String> getAllCustomers() throws SQLException {
        String sql = "SELECT id, customer_id, fname, lname, email FROM customers ORDER BY id";
        List<String> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(
                    rs.getInt("id") + " - " +
                    rs.getString("customer_id") + " | " +
                    rs.getString("fname") + " " + rs.getString("lname") +
                    " (" + (rs.getString("email") != null ? rs.getString("email") : "no email") + ")"
                );
            }
        }
        return list;
    }

    /**
     * Returns all customers as domain objects.
     */
    public List<Customer> getCustomerRecords() throws SQLException {
        String sql = "SELECT * FROM customers ORDER BY id";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        }
        return customers;
    }

    /**
     * Validates a customer's credentials against the database.
     *
     * @param username customer's username
     * @param password customer's password (plain text for now)
     * @return true if a matching customer exists, otherwise false
     * @throws SQLException if the query fails
     */
    public Customer findByCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM customers WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        }
        return null;
    }

    /**
     * Finds the database id for a given customer_id.
     */
    public int findCustomerIdByBusinessId(String customerId) throws SQLException {
        String sql = "SELECT id FROM customers WHERE customer_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
    public Customer findById(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }
        }
        return null;
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        java.sql.Date dob = rs.getDate("dob");
        java.util.Date dobUtil = dob != null ? new java.util.Date(dob.getTime()) : null;

        return new Customer(
            rs.getInt("id"),
            rs.getString("fname"),
            rs.getString("lname"),
            dobUtil,
            rs.getString("customer_id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("phone")
        );
    }
}
 