package main.java.gui;

import main.java.model.AreaCode;
import main.java.model.Customer;
import main.java.model.Flight;
import main.java.service.CustomerService;
import main.java.service.FlightService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FlightSearchWindow extends JFrame {

    private final String from;
    private final String to;
    private final String departDate;
    private final String returnDate;
    private final Customer customer;
    private final boolean isGuest;

    private CustomerService customerService;
    private FlightService flightService;

    // ========= COLOR THEME =========
    private final Color SLATE = new Color(0x2E3B4E);
    private final Color ICE_WHITE = new Color(0xFFFFFF);
    private final Color NAVY = new Color(0x0A1A2F);

    public FlightSearchWindow(String from, String to, String departDate, String returnDate, Customer customer) {
        this.from = from;
        this.to = to;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.customer = customer;
        this.isGuest = (customer == null);

        this.customerService = new CustomerService();
        this.flightService = new FlightService();

        initializeUI();
        loadFlightResults();
    }

    private void initializeUI() {
        setTitle("Available Flights");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(SLATE);

        // ===== TITLE =====
        JLabel title = new JLabel("Available Flights", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(ICE_WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(title, BorderLayout.NORTH);

        // ===== RESULTS PANEL =====
        flightsPanel = new JPanel();
        flightsPanel.setLayout(new BoxLayout(flightsPanel, BoxLayout.Y_AXIS));
        flightsPanel.setBackground(SLATE);

        scrollPane = new JScrollPane(flightsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(SLATE);
        add(scrollPane, BorderLayout.CENTER);

        // ===== BACK BUTTON =====
        JButton backBtn = styledButton("Back");
        backBtn.addActionListener(e -> {
            new MainWindow(customer).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(SLATE);
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Prevent exiting without going back
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                new MainWindow(customer).setVisible(true);
                dispose();
            }
        });
    }

    private JPanel flightsPanel;
    private JScrollPane scrollPane;

    // =====================================================
    // LOAD FLIGHT RESULTS
    // =====================================================
    private void loadFlightResults() {

        flightsPanel.removeAll();

        // Convert strings to AreaCode enums
        AreaCode departureCode;
        AreaCode arrivalCode;
        try {
            departureCode = AreaCode.valueOf(from.toUpperCase());
            arrivalCode = AreaCode.valueOf(to.toUpperCase());
        } catch (IllegalArgumentException e) {
            JLabel errorLabel = new JLabel("Invalid departure or arrival code.", SwingConstants.CENTER);
            errorLabel.setForeground(ICE_WHITE);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            flightsPanel.add(errorLabel);
            revalidate();
            return;
        }

        // Convert date strings to Date objects
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date departDateObj;
        Date returnDateObj;
        try {
            departDateObj = sdf.parse(departDate);
            returnDateObj = sdf.parse(returnDate);
        } catch (ParseException e) {
            JLabel errorLabel = new JLabel("Invalid departure or return date format.", SwingConstants.CENTER);
            errorLabel.setForeground(ICE_WHITE);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            flightsPanel.add(errorLabel);
            revalidate();
            return;
        }

        // Fetch flights from service
        List<Flight> flights = flightService.searchFlights(departureCode, arrivalCode, departDateObj, returnDateObj);

        if (flights == null || flights.isEmpty()) {
            JLabel emptyLabel = new JLabel("No flights found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 18));
            emptyLabel.setForeground(ICE_WHITE);
            flightsPanel.add(emptyLabel);
            revalidate();
            return;
        }

        for (Flight flight : flights) {
            flightsPanel.add(createFlightCard(flight));
            flightsPanel.add(Box.createVerticalStrut(10));
        }

        revalidate();
    }

    // =====================================================
    // CREATE INDIVIDUAL FLIGHT CARD
    // =====================================================
    private JPanel createFlightCard(Flight flight) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(NAVY);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Flight info
        JLabel info = new JLabel(
                "<html><b>Airline:</b> " + flight.getAirLine() +
                "<br><b>Date:</b> " + flight.getFlightDate() +
                "<br><b>Price:</b> "+ flight.getPrice() + "</html>"
        );
        info.setForeground(ICE_WHITE);
        info.setFont(new Font("Arial", Font.PLAIN, 16));

        card.add(info, BorderLayout.CENTER);

        // Select button
        JButton selectBtn = styledButton("Select");
        selectBtn.addActionListener(e -> {
            new FlightManagementPanel(customer, flight);
            dispose();
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(NAVY);
        rightPanel.add(selectBtn);

        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    // =====================================================
    // REUSABLE STYLED BUTTON
    // =====================================================
    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(SLATE);
        b.setForeground(ICE_WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(ICE_WHITE, 2));
        b.setPreferredSize(new Dimension(120, 35));
        return b;
    }
}
