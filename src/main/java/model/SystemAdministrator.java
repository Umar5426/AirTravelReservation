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

    for (Customer c : customers) {
        if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
            this.loggedIn = true;
            this.loggedCustomer = c;

            System.out.println("Login is successful! You are Welcome, " 
                               + c.getFname() + " " + c.getLname());
            return true;
        }
    }

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
