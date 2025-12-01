package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.model.AreaCode;
import main.java.model.Customer;
import main.java.model.Flight;
import main.java.model.Reservation;

public class BookingDAO extends BaseDAO {

    public int createBooking(int customerId, int flightId, double totalPrice) throws SQLException {
        // We use RETURN_GENERATED_KEYS here, so we do manual JDBC instead of the template
        String sql = "INSERT INTO bookings (customer_id, flight_id, booking_date, status, total_price) " +
                     "VALUES (?, ?, NOW(), 'CONFIRMED', ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, flightId);
            stmt.setDouble(3, totalPrice);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1; // Failure
    }

    public void cancelBooking(int bookingId) throws SQLException {
        String sql = "UPDATE bookings SET status='CANCELLED' WHERE id=?";
        executeUpdate(sql, stmt -> stmt.setInt(1, bookingId));
    }

    public String getBookingInfo(int bookingId) throws SQLException {
        // Complex join query
        String sql = "SELECT b.id, b.booking_date, b.status, b.total_price, " +
                     "c.customer_id, c.fname, c.lname, c.email, " +
                     "f.flight_id, f.flight_code, f.departure_area_code, f.arrival_area_code " +
                     "FROM bookings b " +
                     "JOIN customers c ON b.customer_id = c.id " +
                     "JOIN flights f ON b.flight_id = f.id " +
                     "WHERE b.id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return "Booking #" + rs.getInt("id") +
                           "\nCustomer: " + rs.getString("fname") + " " + rs.getString("lname") +
                           " [" + rs.getString("customer_id") + "]" +
                           (rs.getString("email") != null ? " (" + rs.getString("email") + ")" : "") +
                           "\nFlight: " + rs.getString("flight_id") + " (" + rs.getString("flight_code") + ")" +
                           " | " + rs.getString("departure_area_code") +
                           " -> " + rs.getString("arrival_area_code") +
                           "\nStatus: " + rs.getString("status") +
                           "\nTotal: $" + rs.getDouble("total_price") +
                           "\nDate: " + rs.getTimestamp("booking_date");
                }
            }
        }
        return "Booking not found.";
    }

    public List<Reservation> getReservationsForCustomer(int customerDbId) throws SQLException {
        String sql = "SELECT b.id AS booking_id, b.booking_date, b.status, b.total_price, " +
                     "c.id AS c_id, c.name AS c_name, c.email AS c_email, c.phone AS c_phone, " +
                     "f.id AS f_id, f.flight_number, f.source, f.destination, f.departure_time, f.arrival_time, f.capacity, f.price " +
                     "FROM bookings b " +
                     "JOIN flights f ON b.flight_id = f.id " +
                     "JOIN customers c ON b.customer_id = c.id " +
                     "WHERE c.id = ? ORDER BY b.booking_date DESC";

        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerDbId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = buildCustomer(rs);
                    Flight flight = buildFlight(rs);
                    Reservation reservation = new Reservation(customer, flight);
                    reservation.setBookingDbId(rs.getInt("booking_id"));
                    Timestamp bookedAt = rs.getTimestamp("booking_date");
                    if (bookedAt != null) {
                        reservation.setDateBooked(new Date(bookedAt.getTime()));
                    }
                    reservation.setTotalPrice(rs.getDouble("total_price"));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    private Customer buildCustomer(ResultSet rs) throws SQLException {
        String fullName = rs.getString("c_name");
        String[] parts = fullName != null ? fullName.split(" ", 2) : new String[] {"", ""};
        String fname = parts[0];
        String lname = parts.length > 1 ? parts[1] : "";

        return new Customer(
            rs.getInt("c_id"),
            fname,
            lname,
            null,
            String.valueOf(rs.getInt("c_id")),
            null,
            null,
            rs.getString("c_email"),
            rs.getString("c_phone")
        );
    }

    private Flight buildFlight(ResultSet rs) throws SQLException {
        java.sql.Timestamp departure = rs.getTimestamp("departure_time");
        java.sql.Timestamp arrival = rs.getTimestamp("arrival_time");

        Flight flight = new Flight(
            rs.getString("flight_number"),
            rs.getString("flight_number"),
            null,
            departure != null ? new java.util.Date(departure.getTime()) : null,
            null,
            null,
            null
        );
        flight.setId(rs.getInt("f_id"));
        flight.setCapacity(rs.getInt("capacity"));
        flight.setPrice(rs.getDouble("price"));
        // Reuse duration to store a human-friendly segment until a richer model is added.
        if (departure != null && arrival != null) {
            long millis = arrival.getTime() - departure.getTime();
            long hours = millis / (1000 * 60 * 60);
            long minutes = (millis / (1000 * 60)) % 60;
            flight.setFlightDuration(String.format("%dh %02dm", hours, minutes));
        }
        // Store source/destination in codes if enums are extended later
        flight.setDepartureAreaCode(parseAreaCode(rs.getString("source")));
        flight.setArrivalAreaCode(parseAreaCode(rs.getString("destination")));
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
