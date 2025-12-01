package main.java.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.model.AreaCode;
import main.java.model.Flight;

public class FlightDAO extends BaseDAO {

    public void addFlight(String flightId,
                          String flightCode,
                          String airline,
                          java.util.Date flightDate,
                          String flightDuration,
                          String departureAreaCode,
                          String arrivalAreaCode,
                          int capacity,
                          double price) throws SQLException {

        String sql = "INSERT INTO flights (flight_id, flight_code, airline, flight_date, flight_duration, " +
                     "departure_area_code, arrival_area_code, capacity, price) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, flightId);
            stmt.setString(2, flightCode);
            stmt.setString(3, airline);
            if (flightDate != null) {
                stmt.setDate(4, new Date(flightDate.getTime()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setString(5, flightDuration);
            stmt.setString(6, departureAreaCode);
            stmt.setString(7, arrivalAreaCode);
            stmt.setInt(8, capacity);
            stmt.setDouble(9, price);
        });
    }

    public void updateFlight(int id,
                             String flightId,
                             String flightCode,
                             String airline,
                             java.util.Date flightDate,
                             String flightDuration,
                             String departureAreaCode,
                             String arrivalAreaCode,
                             int capacity,
                             double price) throws SQLException {

        String sql = "UPDATE flights SET flight_id=?, flight_code=?, airline=?, flight_date=?, flight_duration=?, " +
                     "departure_area_code=?, arrival_area_code=?, capacity=?, price=? WHERE id=?";

        executeUpdate(sql, stmt -> {
            stmt.setString(1, flightId);
            stmt.setString(2, flightCode);
            stmt.setString(3, airline);
            if (flightDate != null) {
                stmt.setDate(4, new Date(flightDate.getTime()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setString(5, flightDuration);
            stmt.setString(6, departureAreaCode);
            stmt.setString(7, arrivalAreaCode);
            stmt.setInt(8, capacity);
            stmt.setDouble(9, price);
            stmt.setInt(10, id);
        });
    }

    public void deleteFlight(int id) throws SQLException {
        String sql = "DELETE FROM flights WHERE id=?";
        executeUpdate(sql, stmt -> stmt.setInt(1, id));
    }

    public List<String> searchFlights(String departureAreaCode, String arrivalAreaCode) throws SQLException {
        String sql = "SELECT * FROM flights WHERE departure_area_code=? AND arrival_area_code=?";
        List<String> flights = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, departureAreaCode);
            stmt.setString(2, arrivalAreaCode);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    flights.add(
                        rs.getInt("id") + " - " +
                        rs.getString("flight_id") + " (" + rs.getString("flight_code") + ") | " +
                        rs.getString("departure_area_code") + " -> " +
                        rs.getString("arrival_area_code") + " | $" +
                        rs.getDouble("price")
                    );
                }
            }
        }
        return flights;
    }

    public int findFlightDbIdByBusinessId(String flightId) throws SQLException {
        String sql = "SELECT id FROM flights WHERE flight_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    public List<Flight> getAllFlights() throws SQLException {
        String sql = "SELECT * FROM flights ORDER BY flight_date";
        List<Flight> flights = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                flights.add(mapFlight(rs));
            }
        }
        return flights;
    }

    public Flight findById(int id) throws SQLException {
        String sql = "SELECT * FROM flights WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapFlight(rs);
                }
            }
        }
        return null;
    }

    public Flight findByFlightId(String flightId) throws SQLException {
        String sql = "SELECT * FROM flights WHERE flight_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapFlight(rs);
                }
            }
        }
        return null;
    }

    private Flight mapFlight(ResultSet rs) throws SQLException {
        java.sql.Date flightDate = rs.getDate("flight_date");
        AreaCode departure = parseAreaCode(rs.getString("departure_area_code"));
        AreaCode arrival = parseAreaCode(rs.getString("arrival_area_code"));

        Flight flight = new Flight(
            rs.getString("flight_id"),
            rs.getString("flight_code"),
            rs.getString("airline"),
            flightDate != null ? new java.util.Date(flightDate.getTime()) : null,
            rs.getString("flight_duration"),
            departure,
            arrival
        );
        flight.setId(rs.getInt("id"));
        flight.setCapacity(rs.getInt("capacity"));
        flight.setPrice(rs.getDouble("price"));
        return flight;
    }

    private AreaCode parseAreaCode(String code) {
        if (code == null) return null;
        try {
            return AreaCode.valueOf(code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}