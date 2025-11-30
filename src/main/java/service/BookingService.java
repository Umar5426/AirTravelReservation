package main.java.service;

import main.java.model.Reservation;
import main.java.model.Customer;
import main.java.model.Flight;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class BookingService {
    // In-memory reservation storage is written here
    private List<Reservation> reservations = new ArrayList<>();

    public BookingService() {
        // Later, we connect DAO here
    }

    // Makes Reservation here
    public void makeReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("BookingService: Reservation created for flight " 
                           + reservation.flight.flightCode);
    }

    
    // Modifies Reservation here
    public void modifyReservation(Reservation oldReservation, Reservation newReservation) {
        int index = reservations.indexOf(oldReservation);

        if (index != -1) {
            reservations.set(index, newReservation);
            System.out.println("BookingService: Reservation updated.");
        } else {
            System.out.println("BookingService: Reservation not found for update.");
        }
    }

    
    // Cancels Reservation here
    public void cancelReservation(Reservation reservation) {
        reservations.remove(reservation);
        System.out.println("BookingService: Reservation cancelled.");
    }

    
    // Views the Booking History here
    public List<Reservation> getBookingHistory(Customer customer) {
        List<Reservation> result = new ArrayList<>();

        for (Reservation r : reservations) {
            if (r.customer.equals(customer)) {
                result.add(r);
            }
        }

        return Collections.unmodifiableList(result);
    }

    
    // Generates Booking Confirmation here
    public String generateBookingConfirmation(Reservation reservation) {
        return "Booking Confirmation:\n" +
               "Customer: " + reservation.customer.fname + " " + reservation.customer.lname + "\n" +
               "Flight: " + reservation.flight.flightCode + "\n" +
               "Date Booked: " + reservation.dateBooked;
    }
    
}

