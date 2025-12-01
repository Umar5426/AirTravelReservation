package main.java.dao;

import main.java.model.Customer;
import main.java.model.Flight;
import main.java.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDAO {

    private static final String DB_URL = "jdbc:sqlite:flight_reservation.db";

    // -----------------------------
    // Create a new booking
    // -----------------------------
    public boolean createBooking(Customer customer, Flight flight, double totalPrice) {
        String sql = "INSERT INTO Reservation(reservation_id, customer_id, flight_id, date_booked, total_price) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Reservation reservation = new Reservation(customer, flight, totalPrice);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reservation.getReservationID());
            pstmt.setString(2, customer.getCustomerID());
            pstmt.setString(3, flight.getFlightID());
            pstmt.setDate(4, new java.sql.Date(reservation.getDateBooked().getTime()));
            pstmt.setDouble(5, totalPrice);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Cancel a booking
    // -----------------------------
    public boolean cancelBooking(String reservationID) {
        String sql = "DELETE FROM Reservation WHERE reservation_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reservationID);
            int deleted = pstmt.executeUpdate();
            return deleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Get reservations for a customer
    // -----------------------------
    public List<Reservation> getReservationsForCustomer(String customerID) {
        String sql = "SELECT * FROM Reservation WHERE customer_id = ?";
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerID);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Load Customer and Flight separately if needed
                    Customer customer = new CustomerDAO().fetchById(rs.getString("customer_id"));
                    Flight flight = new FlightDAO().fetchFlightByID(rs.getString("flight_id"));

                    Date dateBooked = rs.getDate("date_booked");
                    double totalPrice = rs.getDouble("total_price");
                    String reservationID = rs.getString("reservation_id");

                    reservations.add(new Reservation(reservationID, customer, flight, dateBooked, totalPrice));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // -----------------------------
    // Fetch reservation by ID
    // -----------------------------
    public Reservation fetchReservationByID(String reservationID) {
        String sql = "SELECT * FROM Reservation WHERE reservation_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, reservationID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new CustomerDAO().fetchById(rs.getString("customer_id"));
                    Flight flight = new FlightDAO().fetchFlightByID(rs.getString("flight_id"));
                    Date dateBooked = rs.getDate("date_booked");
                    double totalPrice = rs.getDouble("total_price");

                    return new Reservation(reservationID, customer, flight, dateBooked, totalPrice);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
