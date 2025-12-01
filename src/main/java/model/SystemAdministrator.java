package main.java.model;

import java.util.List;
import main.java.service.FlightService;



public class SystemAdministrator {

    // Static admin credentials
    private static final String ADMIN_USERNAME = "admin01";
    private static final String ADMIN_PASSWORD = "admin02";
	// Fields are set below 
	private boolean loggedIn = false;
    private FlightService flightService;

    // Constructor is written here
    public SystemAdministrator() {
        flightService = new FlightService();
    }
    
    // Login
    public boolean login(String username, String password) {

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            this.loggedIn = true;
            System.out.println("System Admin login successful! Welcome, Admin.");
            return true;
        }

        System.out.println("Invalid admin username or password.");
        return false;
    }


	
    // Admin Functions
    public void addFlight(Flight flight) {
        // calling flightService.addFlight(flight)
		flightService.addFlight(flight);
		// printing
		System.out.println("Flight has been added successfully");
    }

    public void updateFlight(String flightID, Flight updatedFlight) {
        // calling flightService.updateFlight(flightID, updatedFlight) here
		flightService.updateFlight(flightID, updatedFlight); 
		System.out.println("Flight has been updated.");
    }

    public void deleteFlight(String flightID) {
        // calling flightService.deleteFlight(flightID) here
		flightService.deleteFlight(flightID);
			
		System.out.println("Flight has been deleted successfully.");
    }

    public List<Flight> viewFlightSchedule() {
        // getting the flights from the service below
		List<Flight> schedule = flightService.getAllFlights();
		
		// printing each flight
		for (Flight f : schedule) {
			System.out.println(f.flightCode + " on " + f.flightDate);
		}
		return schedule;
	} 
     
    
}
