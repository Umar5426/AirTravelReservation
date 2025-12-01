package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseDAO {

    // get connection from Singleton
    protected Connection getConnection() {
        return DBConnection.getInstance().getConnection();
    }

    // Template Method for INSERT, UPDATE, DELETE operations.
    // It handles the "boilerplate" code (creation, execution, closing)
    // and lets the subclass define the specific parameters via the Consumer.
    protected void executeUpdate(String sql, SQLConsumer<PreparedStatement> consumer) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            consumer.accept(stmt); // The variable part (setting params)
            stmt.executeUpdate();
        }
    }
}