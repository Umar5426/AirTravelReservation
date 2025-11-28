package main.java.dao;

import java.sql.*;

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
                     "c.name AS customer_name, c.email, " +
                     "f.flight_number, f.source, f.destination " +
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
                           "\nCustomer: " + rs.getString("customer_name") +
                           " (" + rs.getString("email") + ")" +
                           "\nFlight: " + rs.getString("flight_number") +
                           " | " + rs.getString("source") +
                           " -> " + rs.getString("destination") +
                           "\nStatus: " + rs.getString("status") +
                           "\nTotal: $" + rs.getDouble("total_price") +
                           "\nDate: " + rs.getTimestamp("booking_date");
                }
            }
        }
        return "Booking not found.";
    }
}