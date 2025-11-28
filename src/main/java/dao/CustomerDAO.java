package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends BaseDAO {

    public void addCustomer(String name, String email, String phone) throws SQLException {
        String sql = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";

        // Using the Template Method from BaseDAO
        executeUpdate(sql, stmt -> {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
        });
    }

    public void updateCustomer(int id, String name, String email, String phone) throws SQLException {
        String sql = "UPDATE customers SET name=?, email=?, phone=? WHERE id=?";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(4, id);
        });
    }

    public void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE id=?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id));
    }

    // Simple SELECT implementation
    public List<String> getAllCustomers() throws SQLException {
        String sql = "SELECT id, name, email FROM customers";
        List<String> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(
                    rs.getInt("id") + " - " +
                    rs.getString("name") + " (" + rs.getString("email") + ")"
                );
            }
        }
        return list;
    }
}