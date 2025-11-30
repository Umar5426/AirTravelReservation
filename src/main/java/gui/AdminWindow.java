package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class AdminWindow extends JFrame {

    public AdminWindow() {
        setTitle("Admin Panel");
        setSize(510, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("System Administrator Panel", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Center buttons are here
        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JButton manageFlightsBtn = new JButton("Manage Flights");
        JButton viewSystemBtn = new JButton("Manage Flight");
        JButton logoutBtn = new JButton("Logout");

        center.add(manageFlightsBtn);
        center.add(viewSystemBtn);
        center.add(logoutBtn);

        add(center, BorderLayout.CENTER);

        // Button Listeners
        manageFlightsBtn.addActionListener(e -> {
            new FlightInterfaceWindow().setVisible(true);
            dispose();
        });


    }
    
}
