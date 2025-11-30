package main.java.model;
import java.util.List;
import main.java.service.FlightService;



public class SystemAdministrator {

    // Fields are set below
    private boolean loggedIn = false;
    private Customer loggedCustomer;
    private FlightService flightService;

    // Constructor is written here
    public SystemAdministrator() {
        flightService = new FlightService();
    }
    
    // Login
    public boolean login(String username, String password, List<Customer> customers) {
        
		// Looping through all of the customers here that are passed into this method here
		for (Customer c : customers) {
			if (c.username.equals(username) && c.password.equals(password)) {
				// If match found -> login success
				this.loggedIn = true;
				this.loggedCustomer = c;
				
				System.out.println("Login is successful! You are Welcome, " + c.fname + " " + c.lname);
				return true;
		    }
		}
		
		// If no match is found then do this
		System.out.println("Invalid username or password.");
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
