package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO extends BaseDAO {

    public void addFlight(String flightNumber, String source, String destination,
                          Timestamp departure, Timestamp arrival,
                          int capacity, double price) throws SQLException {

        String sql = "INSERT INTO flights (flight_number, source, destination, departure_time, arrival_time, capacity, price) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, flightNumber);
            stmt.setString(2, source);
            stmt.setString(3, destination);
            stmt.setTimestamp(4, departure);
            stmt.setTimestamp(5, arrival);
            stmt.setInt(6, capacity);
            stmt.setDouble(7, price);
        });
    }

    public void updateFlight(int id, String flightNumber, String source, String destination,
                             Timestamp departure, Timestamp arrival,
                             int capacity, double price) throws SQLException {

        String sql = "UPDATE flights SET flight_number=?, source=?, destination=?, departure_time=?, arrival_time=?, capacity=?, price=? WHERE id=?";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, flightNumber);
            stmt.setString(2, source);
            stmt.setString(3, destination);
            stmt.setTimestamp(4, departure);
            stmt.setTimestamp(5, arrival);
            stmt.setInt(6, capacity);
            stmt.setDouble(7, price);
            stmt.setInt(8, id);
        });
    }

    public void deleteFlight(int id) throws SQLException {
        String sql = "DELETE FROM flights WHERE id=?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id));
    }

    public List<String> searchFlights(String source, String destination) throws SQLException {
        String sql = "SELECT * FROM flights WHERE source=? AND destination=?";
        List<String> flights = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, source);
            stmt.setString(2, destination);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    flights.add(
                        rs.getInt("id") + " - " +
                        rs.getString("flight_number") + " | " +
                        rs.getString("source") + " -> " +
                        rs.getString("destination") + " | $" +
                        rs.getDouble("price")
                    );
                }
            }
        }
        return flights;
    }
}