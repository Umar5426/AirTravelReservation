package main.java.gui;

import java.awt.*;
import javax.swing.*;
import main.java.service.CustomerService;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private CustomerService customerService = new CustomerService();

    public LoginWindow() {
        setTitle("Flight Reservation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton loginBtn = new JButton("Login");
        JButton guestBtn = new JButton("Continue as Guest");

        buttonPanel.add(loginBtn);
        buttonPanel.add(guestBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== Event Listeners =====

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            attemptLogin(user, pass);
        });

        guestBtn.addActionListener(e -> continueAsGuest());
    }

    // =============================
    //   Methods Called on Actions
    // =============================

    private void attemptLogin(String username, String password) {
        
        System.out.println("Attempting login: " + username);
        // Check for admin logic

        if (username.equals("Captain") && password.equals("Abdul4")) {
            JOptionPane.showMessageDialog(this, "Admin Login Successful!");
            new AdminWindow().setVisible(true);
            dispose();
            return;
        }
        
        // Check for Customer login

        boolean authenticated = customerService.login(username, password);

        if (authenticated) {
            JOptionPane.showMessageDialog(this, "Login Successful!");

            // Open Main Window in logged-in mode
            new MainWindow(false).setVisible(true);
            dispose();
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    private void continueAsGuest() {
        System.out.println("Continuing as Guest...");

        new MainWindow(true).setVisible(true);
        dispose();
    }
}
