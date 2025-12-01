package main.java.gui;

import main.java.model.Flight;
import main.java.model.Reservation;
import main.java.service.CustomerService;
import main.java.service.PaymentSystem;
import main.java.service.CreditCardPayment;
import main.java.service.PayPalPayment;

import javax.swing.*;
import java.awt.*;

public class BookingPanel extends JFrame {

    private CustomerService customerService;
    private Flight flight;
    
    // BookingPanel is written below 
    public BookingPanel(CustomerService customerService, Flight flight) {
        this.customerService = customerService;
        this.flight = flight;

        setTitle("Booking Summary");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("Confirm Your Booking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // ===== Info Panel =====
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(6, 1, 8, 8));
        info.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        info.add(new JLabel("Flight Code: " + flight.getFlightCode()));
        info.add(new JLabel("Airline: " + flight.getAirLine()));
        info.add(new JLabel("Date: " + flight.getFlightDate()));
        info.add(new JLabel("From: " + flight.getDepartureAreaCode()));
        info.add(new JLabel("To: " + flight.getArrivalAreaCode()));
        info.add(new JLabel("Price: $350")); // placeholder

        add(info, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel bottom = new JPanel();

        JButton confirmBtn = new JButton("Confirm & Pay");
        JButton backBtn = new JButton("Back");

        confirmBtn.addActionListener(e -> {
            Reservation r = customerService.makeReservation(flight.getFlightCode());

            if (r == null) {
                JOptionPane.showMessageDialog(this, "Booking failed (customer not logged in?)");
                return;
            }

            PaymentSystem payment = new PaymentSystem();

            String[] methods = {"Credit Card", "PayPal"};
            String choice = (String) JOptionPane.showInputDialog(
                    this,
                    "Select Payment Method:",
                    "Payment",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    methods,
                    methods[0]
            );

            if (choice == null) return;

            if (choice.equals("Credit Card")) {
                payment.setPaymentStrategy(new CreditCardPayment("1234-5678-9012", "Customer"));
                payment.pay(350);
            } else {
                payment.setPaymentStrategy(new PayPalPayment("customer@example.com"));
                payment.pay(350);
            }

            JOptionPane.showMessageDialog(this, "Booking Confirmed!");
            dispose();
        });

        backBtn.addActionListener(e -> {
            new FlightManagementPanel(customerService, flight).setVisible(true);
            dispose();
        });

        bottom.add(confirmBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);
    }
}
