package main.java.service;

import main.java.dao.BookingDAO;
import main.java.dao.FlightDAO;
import main.java.model.Customer;
import main.java.model.Flight;
import main.java.model.Reservation;

import java.util.List;

public class BookingService {

    private BookingDAO bookingDAO;
    private FlightDAO flightDAO;
    private Customer loggedInCustomer;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.flightDAO = new FlightDAO();
    }

    // ---------------------
    // Set logged-in customer
    // ---------------------
    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    // ---------------------
    // Make a new reservation
    // ---------------------
    public Reservation makeReservation(Flight flight, double totalPrice) {
        if (loggedInCustomer == null) return null;

        boolean success = bookingDAO.createBooking(loggedInCustomer, flight, totalPrice);
        if (success) {
            return new Reservation(loggedInCustomer, flight, totalPrice);
        }
        return null;
    }

    // ---------------------
    // Cancel a reservation
    // ---------------------
    public boolean cancelReservation(String reservationID) {
        return bookingDAO.cancelBooking(reservationID);
    }

    // ---------------------
    // Modify an existing reservation (change flight)
    // ---------------------
    public boolean modifyReservation(String reservationID, String FlightId) {

        Flight newFlight = flightDAO.fetchFlightByID(FlightId);
        Reservation existing = bookingDAO.fetchReservationByID(reservationID);
        if (existing == null) return false;

        // Cancel old booking and create new one with the same reservation ID
        boolean cancelled = bookingDAO.cancelBooking(reservationID);
        if (!cancelled) return false;

        // Create new reservation with same customer and new flight
        return bookingDAO.createBooking(existing.getCustomer(), newFlight, existing.getTotalPrice());
    }

    // ---------------------
    // View booking history for logged-in customer
    // ---------------------
    public List<Reservation> viewBookingHistory() {
        if (loggedInCustomer == null) return null;

        return bookingDAO.getReservationsForCustomer(loggedInCustomer.getCustomerID());
    }

    // ---------------------
    // Fetch a single reservation by ID
    // ---------------------
    public Reservation getReservationById(String reservationID) {
        return bookingDAO.fetchReservationByID(reservationID);
    }

    // ---------------------
    // Generate booking confirmation
    // ---------------------
    public String generateBookingConfirmation(String reservationID) {
        Reservation reservation = bookingDAO.fetchReservationByID(reservationID);
        if (reservation == null) return "Reservation not found.";

        return "Booking Confirmation:\n" +
               "Customer: " + reservation.getCustomer().getFname() + " " + reservation.getCustomer().getLname() + "\n" +
               "Flight: " + reservation.getFlight().getFlightCode() + "\n" +
               "Date Booked: " + reservation.getDateBooked() + "\n" +
               "Price: $" + reservation.getTotalPrice();
    }
}
