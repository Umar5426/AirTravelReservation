package main.java.service;

import java.util.ArrayList;
import java.util.List;

import main.java.dao.AuthorizationDAO;
import main.java.dao.FlightDAO;
import main.java.model.Customer;
import main.java.model.Flight;
import main.java.model.Reservation;

public class CustomerService {

    private List<Customer> customers = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    private Customer loggedInCustomer;

    // Inject DAO
    private AuthorizationDAO authDAO = new AuthorizationDAO();
    private FlightDAO flightDAO = new FlightDAO();

    // ---------------------
    // Authentication
    // ---------------------

    public boolean login(String username, String password) {
        try {
            String role = authDAO.login(username, password);
            if (role != null) {
                // Minimal customer stub; user records are in the users table in this schema.
                loggedInCustomer = new Customer(-1, username, "", null, username, username, password, null, null);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logout() {
        loggedInCustomer = null;
    }

    // ---------------------
    // Flight Searching (Using DAO)
    // ---------------------

    public List<String> searchFlights(String departure, String destination) {
        try {
            return flightDAO.searchFlights(departure, destination);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ---------------------
    // Flight Details
    // ---------------------

    // If needed, you can implement viewFlight() using DAO later
    public Flight viewFlight(String flightCode) {
        return null;  // Placeholder until we add DAO method getFlightByNumber()
    }

    // ---------------------
    // Reservation Handling
    // ---------------------

    public Reservation makeReservation(String flightCode) {
        if (loggedInCustomer == null) return null;

        // Once viewFlight() is DAO-powered, this will work
        Flight flight = viewFlight(flightCode);
        if (flight == null) return null;

        Reservation newRes = new Reservation(loggedInCustomer, flight);
        reservations.add(newRes);
        return newRes;
    }

    public boolean cancelReservation(String reservationID) {
        return reservations.removeIf(r -> r.getReservationID().equals(reservationID));
    }

    public boolean modifyReservation(String reservationID, String newFlightCode) {
        Reservation res = getReservationById(reservationID);
        if (res == null) return false;

        Flight newFlight = viewFlight(newFlightCode);
        if (newFlight == null) return false;

        res.setFlight(newFlight);
        return true;
    }

    public List<Reservation> viewBookingHistory() {
        if (loggedInCustomer == null) return null;

        List<Reservation> history = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getCustomer().getCustomerID().equals(loggedInCustomer.getCustomerID())) {
                history.add(r);
            }
        }
        return history;
    }

    // Helper
    private Reservation getReservationById(String id) {
        for (Reservation r : reservations) {
            if (r.getReservationID().equals(id)) {
                return r;
            }
        }
        return null;
    }
}
