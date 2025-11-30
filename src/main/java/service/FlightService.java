package main.java.service;

import main.java.model.Flight;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class FlightService {

    // The temporary in-memory list is written below.
    private List<Flight> flights = new ArrayList<>();
    
    public FlightService() {
        // Constructor is here
        // Initialize the DAO here when ready
        // this.flightDAO = new FlightDAO();
    }

    // CREATE – Add a new flight

    public void addFlight(Flight flight) {
        // TEMPORARY: stores in memory
        flights.add(flight);

        // When DB is ready:
        // flightDAO.insertFlight(flight);     // Uncomment these when needed 

        System.out.println("FlightService: addFlight() is called for " + flight.flightCode);
    }

    // Updating – Updates an existing flight
    public void updateFlight(String flightID, Flight updatedFlight) {
        // There is the Temporary - in-memory update written here
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).flightID.equals(flightID)) {
                flights.set(i, updatedFlight);
                System.out.println("FlightService: flight has been updated (in-memory).");
                return;
            }
        }

        // When DB is ready:
        // flightDAO.updateFlight(flightID, updatedFlight);

        System.out.println("FlightService: updateFlight() is called for ID " + flightID);
    }

    
    // Delete – Delete a flight by ID
 
    public void deleteFlight(String flightID) {
        // TEMPORARY in-memory delete here
        flights.removeIf(f -> f.flightID.equals(flightID));

        // When DB is ready:
        // flightDAO.deleteFlight(flightID);

        System.out.println("FlightService: deleteFlight() is called for ID " + flightID);
    }

    // Read – Get all flights (view flight schedule)
    public List<Flight> getAllFlights() {
        // Temporary return in-memory list

        // When DB is ready:
        // return flightDAO.getAllFlights();

        return Collections.unmodifiableList(flights);
    }

    // Search methods can be added here later if needed
}
