package main.java.service;

import main.java.dao.FlightDAO;
import main.java.model.Flight;
import main.java.model.AreaCode;

import java.util.Date;
import java.util.List;

public class FlightService {

    private FlightDAO flightDAO;

    public FlightService() {
        this.flightDAO = new FlightDAO(); // Initialize the DAO
    }

    // CREATE – Add a new flight
    public boolean addFlight(String flightCode, String airLine, Date flightDate,
                             String flightDuration, double price,
                             AreaCode departureAreaCode, AreaCode arrivalAreaCode) {

        return flightDAO.addFlight(flightCode, airLine, flightDate, flightDuration,
                                   price, departureAreaCode, arrivalAreaCode);
    }

    // UPDATE – Update an existing flight
    public boolean updateFlight(String flightID, String flightCode, String airLine, Date flightDate,
                                String flightDuration, double price,
                                AreaCode departureAreaCode, AreaCode arrivalAreaCode) {

        return flightDAO.updateFlight(flightID, flightCode, airLine, flightDate,
                                      flightDuration, price, departureAreaCode, arrivalAreaCode);
    }

    // DELETE – Delete a flight by ID
    public boolean deleteFlight(String flightID) {
        return flightDAO.removeFlight(flightID);
    }

    // FETCH a single flight by flightID
    public Flight getFlightByID(String flightID) {
        return flightDAO.fetchFlightByID(flightID);
    }

    // SEARCH – Search flights between two areas and dates
    public List<Flight> searchFlights(AreaCode from, AreaCode to, Date departDate, Date returnDate) {
        return flightDAO.searchFlights(from, to, departDate, returnDate);
    }
}
