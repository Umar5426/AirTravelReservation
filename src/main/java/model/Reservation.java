package main.java.model;

import java.util.Date;

public class Reservation {

    private static int nextId = 1;  // auto–increment counter for new reservations

    private String reservationID;
    private Customer customer;
    private Flight flight;
    private Date dateBooked;
    private double totalPrice;

    // ========================
    // Constructor for new reservation (auto-generates ID)
    // ========================
    public Reservation(Customer customer, Flight flight, double totalPrice) {
        this.reservationID = generateReservationID();
        this.customer = customer;
        this.flight = flight;
        this.totalPrice = totalPrice;
        this.dateBooked = new Date(); // today's date
    }

    // ========================
    // Constructor for loading existing reservation (uses existing ID)
    // ========================
    public Reservation(String reservationID, Customer customer, Flight flight, Date dateBooked, double totalPrice) {
        this.reservationID = reservationID;
        this.customer = customer;
        this.flight = flight;
        this.dateBooked = dateBooked;
        this.totalPrice = totalPrice;
    }

    // ========================
    // Auto-generate reservation ID
    // ========================
    private String generateReservationID() {
        return "R" + nextId++;
    }

    // ========================
    // Getters & Setters
    // ========================
    public String getReservationID() {
        return reservationID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(Date dateBooked) {
        this.dateBooked = dateBooked;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
