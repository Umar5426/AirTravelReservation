package main.java.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import main.java.model.Flight;
import main.java.model.Customer;

public class FlightManagementPanel extends JFrame {
    private Customer customer;
    private Flight flight;

    public FlightManagementPanel(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;

        setTitle("Flight Details");
        setSize(510, 460);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title is here
        JLabel title = new JLabel("Flight Details", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Information Panel
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(7, 1, 8, 8));
        info.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        info.add(new JLabel("Flight Code: " + flight.getFlightCode()));
        info.add(new JLabel("Airline: " + flight.getAirLine()));
        info.add(new JLabel("Date: " + flight.getFlightDate()));
        info.add(new JLabel("Duration: " + flight.getFlightDuration()));
        info.add(new JLabel("From: " + flight.getDepartureAreaCode()));
        info.add(new JLabel("To: " + flight.getArrivalAreaCode()));
        info.add(new JLabel("Price: $" + "500"));

        add(info, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel bottom = new JPanel();

        JButton bookBtn = new JButton("Book Flight");
        JButton backBtn = new JButton("Back");

        bookBtn.addActionListener(e -> {
            new BookingPanel(customer, flight).setVisible(true);
            dispose();
        });

        backBtn.addActionListener(e -> dispose());

        bottom.add(bookBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);
    }

    
}
