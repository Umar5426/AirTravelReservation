package main.java.dao;

import main.java.model.Flight;
import main.java.model.AreaCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDAO {

    private static final String DB_URL = "jdbc:sqlite:flight_reservation.db";

    // -----------------------------
    // Add a new flight
    // -----------------------------
    public boolean addFlight(String flightCode, String airLine, Date flightDate,
                             String flightDuration, double price, AreaCode departureAreaCode,
                             AreaCode arrivalAreaCode) {

        String sql = "INSERT INTO Flight(flightID, flight_code, airline, flight_date, duration, price, departure_area, arrival_area) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Flight flight = new Flight(flightCode, airLine, flightDate, flightDuration,
                                       departureAreaCode, arrivalAreaCode, price);

            pstmt.setString(1, flight.getFlightID());
            pstmt.setString(2, flight.getFlightCode());
            pstmt.setString(3, flight.getAirLine());
            pstmt.setDate(4, new java.sql.Date(flight.getFlightDate().getTime()));
            pstmt.setString(5, flight.getFlightDuration());
            pstmt.setDouble(6, flight.getPrice());
            pstmt.setString(7, flight.getDepartureAreaCode().name());
            pstmt.setString(8, flight.getArrivalAreaCode().name());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Remove flight by flightID
    // -----------------------------
    public boolean removeFlight(String flightID) {
        String sql = "DELETE FROM Flight WHERE flightID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flightID);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Update flight details
    // -----------------------------
    public boolean updateFlight(String flightID, String flightCode, String airLine, Date flightDate,
                                String flightDuration, double price, AreaCode departureAreaCode,
                                AreaCode arrivalAreaCode) {

        String sql = "UPDATE Flight SET flight_code=?, airline=?, flight_date=?, duration=?, price=?, departure_area=?, arrival_area=? " +
                     "WHERE flightID=?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flightCode);
            pstmt.setString(2, airLine);
            pstmt.setDate(3, new java.sql.Date(flightDate.getTime()));
            pstmt.setString(4, flightDuration);
            pstmt.setDouble(5, price);
            pstmt.setString(6, departureAreaCode.name());
            pstmt.setString(7, arrivalAreaCode.name());
            pstmt.setString(8, flightID);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Search flights
    // -----------------------------
    public List<Flight> searchFlights(AreaCode from, AreaCode to, Date departDate, Date returnDate) {
        String sql = "SELECT * FROM Flight WHERE departure_area = ? AND arrival_area = ? AND flight_date BETWEEN ? AND ?";
        List<Flight> flights = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, from.name());
            pstmt.setString(2, to.name());
            pstmt.setDate(3, new java.sql.Date(departDate.getTime()));
            pstmt.setDate(4, new java.sql.Date(returnDate.getTime()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Flight flight = new Flight(
                            rs.getString("flightID"),
                            rs.getString("flight_code"),
                            rs.getString("airline"),
                            rs.getDate("flight_date"),
                            rs.getString("duration"),
                            AreaCode.valueOf(rs.getString("departure_area")),
                            AreaCode.valueOf(rs.getString("arrival_area")),
                            rs.getDouble("price")
                    );
                    flights.add(flight);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    // -----------------------------
    // Fetch flight by flightID
    // -----------------------------
    public Flight fetchFlightByID(String flightID) {
        String sql = "SELECT * FROM Flight WHERE flightID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flightID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Flight(
                            rs.getString("flightID"),
                            rs.getString("flight_code"),
                            rs.getString("airline"),
                            rs.getDate("flight_date"),
                            rs.getString("duration"),
                            AreaCode.valueOf(rs.getString("departure_area")),
                            AreaCode.valueOf(rs.getString("arrival_area")),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
