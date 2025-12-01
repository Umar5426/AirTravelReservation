package main.java.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Minimal initializer that simply ensures we can connect to the database.
 * Schema and seed data are assumed to be managed externally (already created in MySQL).
 */
public class DatabaseInitializer {

    /**
     * Verifies a connection can be obtained. Does not create or seed anything.
     */
    public static void initializeDatabase() {
        try (Connection ignored = DBConnection.getInstance().getConnection()) {
            // Connection obtained successfully; nothing else to do.
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database: " + e.getMessage(), e);
        }
    }
}
