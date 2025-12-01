package main.java.model;

import main.java.service.FlightService;

import java.util.Date;
import java.util.List;

public class SystemAdministrator {

    // Static admin credentials
    private static final String ADMIN_USERNAME = "admin01";
    private static final String ADMIN_PASSWORD = "admin02";

    // Admin state
    private boolean loggedIn = false;
    private FlightService flightService;

    // Constructor
    public SystemAdministrator() {
        flightService = new FlightService();
    }

    // ---------------------
    // Login
    // ---------------------
    public boolean login(String username, String password) {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            this.loggedIn = true;
            System.out.println("System Admin login successful! Welcome, Admin.");
            return true;
        }

        System.out.println("Invalid admin username or password.");
        return false;
    }

    // ---------------------
    // Admin Functions
    // ---------------------

    public boolean addFlight(String flightCode, String airLine, Date flightDate, String flightDuration, AreaCode departureAreaCode, AreaCode arrivalAreaCode, double price) {
        if (!loggedIn) {
            System.out.println("Admin not logged in.");
            return false;
        }
        boolean success = flightService.addFlight(flightCode, airLine,flightDate, flightDuration, price, departureAreaCode, arrivalAreaCode);
        if (success) System.out.println("Flight has been added successfully.");
        return success;
    }

    public boolean updateFlightDate(String flightID, Date newFlightDate) {
        if (!loggedIn) {
            System.out.println("Admin not logged in.");
            return false;
        }

        boolean success = flightService.updateFlightDate(flightID, newFlightDate);
        if (success) System.out.println("Flight date has been updated successfully.");
        return success;
    }


    public boolean deleteFlight(String flightID) {
        if (!loggedIn) {
            System.out.println("Admin not logged in.");
            return false;
        }
        boolean success = flightService.deleteFlight(flightID);
        if (success) System.out.println("Flight has been deleted successfully.");
        return success;
    }

    public List<Flight> viewFlightSchedule() {
        if (!loggedIn) {
            System.out.println("Admin not logged in.");
            return null;
        }

        List<Flight> schedule = flightService.searchAllFlights();
        if (schedule.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("Flight Schedule:");
            for (Flight f : schedule) {
                System.out.println(f.getFlightCode() + " | " +
                                   f.getAirLine() + " | " +
                                   f.getDepartureAreaCode() + " -> " +
                                   f.getArrivalAreaCode() + " | " +
                                   f.getFlightDate());
            }
        }
        return schedule;
    }
}
