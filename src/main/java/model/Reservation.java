package main.java.model;

import java.util.Date;

public class Reservation {

    private static int nextId = 1;  // auto–increment counter (fallback for UI)

    private String reservationID;
    private int bookingDbId = -1;
    private Customer customer;
    private Date dateBooked;
    private Flight flight;
    private double totalPrice;

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

    public int getBookingDbId() {
        return bookingDbId;
    }

    public void setBookingDbId(int bookingDbId) {
        this.bookingDbId = bookingDbId;
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
