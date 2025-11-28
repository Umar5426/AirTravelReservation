package main.java.gui;

import main.java.model.Flight;
import main.java.service.CustomerService;

import javax.swing.*;
import java.awt.*;

public class FlightSearchWindow extends JFrame {

    private JTextField fromField;
    private JTextField toField;
    private JTextField dateField;

    private CustomerService customerService;
    private boolean isGuest;

    public FlightSearchWindow(boolean isGuest) {
        this.customerService = new CustomerService();
        this.isGuest = isGuest;

        setTitle("Search Flights");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =======================
        // Title
        // =======================
        JLabel title = new JLabel("Search Available Flights", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // =======================
        // Search Form
        // =======================
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        form.add(new JLabel("From (Area Code):"));
        fromField = new JTextField();
        form.add(fromField);

        form.add(new JLabel("To (Area Code):"));
        toField = new JTextField();
        form.add(toField);

        form.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        form.add(dateField);

        add(form, BorderLayout.CENTER);

        // =======================
        // Buttons
        // =======================
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton searchBtn = new JButton("Search");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(searchBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // =======================
        // Action Listeners
        // =======================
        searchBtn.addActionListener(e -> searchFlights());
        backBtn.addActionListener(e -> goBack());

        // =======================
        // X Button Behavior
        // =======================
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                goBack();
            }
        });
    }

    private void searchFlights() {
        // Placeholder: Connect to your upcoming real search logic
        String from = fromField.getText();
        String to = toField.getText();
        String date = dateField.getText();

        JOptionPane.showMessageDialog(this,
                "Searching flights...\nFrom: " + from +
                "\nTo: " + to +
                "\nDate: " + date);

        // You can replace this with: List<Flight> flights = customerService.searchFlights(...)

        // After searching, you may want another window like "FlightResultsWindow"
    }

    private void goBack() {
        new MainWindow(isGuest).setVisible(true);
        dispose();
    }
}
