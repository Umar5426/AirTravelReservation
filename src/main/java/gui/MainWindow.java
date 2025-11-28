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

        setTitle("Flight Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ============================================================
        // TOP TITLE
        // ============================================================
        JLabel titleLabel = new JLabel("Book Your Flight", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ============================================================
        // LEFT SIDE MENU
        // ============================================================
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton searchBtn = new JButton("Search Flights");
        JButton viewBtn = new JButton("View Flight");
        JButton historyBtn = new JButton("Booking History");
        JButton logoutBtn = new JButton("Logout");

        menuPanel.add(searchBtn);
        menuPanel.add(viewBtn);
        menuPanel.add(historyBtn);
        menuPanel.add(logoutBtn);

        // Disable restricted buttons for guests
        if (isGuest) {
            viewBtn.setEnabled(false);
            historyBtn.setEnabled(false);
        }

        add(menuPanel, BorderLayout.WEST);

        // ============================================================
        // CENTER PANEL — "EXPEDIA STYLE" FLIGHT SEARCH BOX
        // ============================================================
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField fromField = new JTextField();
        JTextField toField = new JTextField();
        JTextField departDateField = new JTextField();
        JTextField returnDateField = new JTextField();

        JLabel fromLabel = new JLabel("From (Area Code):");
        JLabel toLabel = new JLabel("To (Area Code):");
        JLabel departLabel = new JLabel("Departure Date (YYYY-MM-DD):");
        JLabel returnLabel = new JLabel("Return Date (YYYY-MM-DD):");

        gbc.gridx = 0; gbc.gridy = 0; searchPanel.add(fromLabel, gbc);
        gbc.gridx = 1; searchPanel.add(fromField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; searchPanel.add(toLabel, gbc);
        gbc.gridx = 1; searchPanel.add(toField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; searchPanel.add(departLabel, gbc);
        gbc.gridx = 1; searchPanel.add(departDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; searchPanel.add(returnLabel, gbc);
        gbc.gridx = 1; searchPanel.add(returnDateField, gbc);

        JButton bigSearchButton = new JButton("Search Flights");
        bigSearchButton.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(bigSearchButton, gbc);

        add(searchPanel, BorderLayout.CENTER);

        // ============================================================
        // EVENT LISTENERS
        // ============================================================

        // Side-panel search button also triggers the search
        searchBtn.addActionListener(e -> bigSearchButton.doClick());

        bigSearchButton.addActionListener(e -> {
            String from = fromField.getText().trim();
            String to = toField.getText().trim();
            String depart = departDateField.getText().trim();
            String ret = returnDateField.getText().trim();

            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter both departure and arrival area codes.",
                        "Missing Info",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Pass the search values to the next GUI
            FlightSearchWindow fsw = new FlightSearchWindow(isGuest);
            fsw.setVisible(true);
            dispose();
        });

        viewBtn.addActionListener(e -> {
            customerService.viewFlight();
            JOptionPane.showMessageDialog(this, "Viewing flight...");
        });

        historyBtn.addActionListener(e -> new BookingHistoryWindow().setVisible(true));

        logoutBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            dispose();
        });
    }
}
