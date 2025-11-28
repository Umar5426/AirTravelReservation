package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationDAO extends BaseDAO {

    /**
     * Attempts to log the user in.
     * @return The user's role (e.g., "ADMIN", "CUSTOMER") if successful, null otherwise.
     */
    public String login(String username, String password) throws SQLException {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password); // Note: In production, passwords should be hashed!
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        }
        return null;
    }

    /**
     * Registers a new user.
     */
    public void registerUser(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        // Use the BaseDAO template method for the insert
        executeUpdate(sql, stmt -> {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
        });
    }
}