package main.java.model;

import java.util.Date;

public class Reservation {

    private static int nextId = 1;  // auto–increment counter

    private String reservationID;
    private Customer customer;
    private Date dateBooked;
    private Flight flight;

    // -----------------------
    // Constructor
    // -----------------------
    public Reservation(Customer customer, Flight flight) {
        this.reservationID = "R" + nextId++;  // e.g., R1, R2, R3...
        this.customer = customer;
        this.flight = flight;
        this.dateBooked = new Date();         // today's date
    }

    // -----------------------
    // Getters / Setters 
    // -----------------------
    public String getReservationID() {
        return reservationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
