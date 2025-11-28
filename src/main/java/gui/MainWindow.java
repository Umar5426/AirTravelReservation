package main.java.gui;

import main.java.service.CustomerService;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private boolean isGuest;
    private CustomerService customerService;

    public MainWindow() {
        this(true); // default guest
    }

    public MainWindow(boolean isGuest) {
        this.isGuest = isGuest;
        this.customerService = new CustomerService();

        setTitle("Flight Reservation System - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel titleLabel = new JLabel("Flight Reservation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // ===== Buttons Panel =====
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(7, 1, 10, 10));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton searchBtn = new JButton("Search Flights");
        JButton viewBtn = new JButton("View Flight");
        JButton reserveBtn = new JButton("Make Reservation");
        JButton modifyBtn = new JButton("Modify Reservation");
        JButton cancelBtn = new JButton("Cancel Reservation");
        JButton historyBtn = new JButton("View Booking History");
        JButton logoutBtn = new JButton("Logout");

        btnPanel.add(searchBtn);
        btnPanel.add(viewBtn);
        btnPanel.add(reserveBtn);
        btnPanel.add(modifyBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(historyBtn);
        btnPanel.add(logoutBtn);

        add(btnPanel, BorderLayout.CENTER);

        // Disable restricted buttons if guest
        if (isGuest) {
            reserveBtn.setEnabled(false);
            modifyBtn.setEnabled(false);
            cancelBtn.setEnabled(false);
            historyBtn.setEnabled(false);
        }

        // ===== EVENT LISTENERS =====

        searchBtn.addActionListener(e -> {
            customerService.searchFlights();
            JOptionPane.showMessageDialog(this, 
                "Searching flights... (service call triggered)");
        });

        viewBtn.addActionListener(e -> {
            customerService.viewFlight();
            JOptionPane.showMessageDialog(this, 
                "Viewing flight... (service call triggered)");
        });

        reserveBtn.addActionListener(e -> {
            customerService.makeReservation();
            JOptionPane.showMessageDialog(this, 
                "Making reservation...");
        });

        modifyBtn.addActionListener(e -> {
            customerService.modifyReservation();
            JOptionPane.showMessageDialog(this, 
                "Modifying reservation...");
        });

        cancelBtn.addActionListener(e -> {
            customerService.cancelReservation();
            JOptionPane.showMessageDialog(this, 
                "Cancelling reservation...");
        });

        historyBtn.addActionListener(e -> {
            customerService.viewBookingHistory();
            JOptionPane.showMessageDialog(this, 
                "Showing booking history...");
        });

        logoutBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            dispose();
        });
    }
}
