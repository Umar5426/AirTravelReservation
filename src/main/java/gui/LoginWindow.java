package main.java.gui;

import java.awt.*;
import javax.swing.*;
import main.java.service.CustomerService;
import main.java.model.Customer;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private CustomerService customerService = new CustomerService();

    // === Color Theme ===
    private final Color NAVY = new Color(0x0A1A2F);
    private final Color SLATE = new Color(0x708090);
    private final Color WHITE = new Color(0xFFFFFF);

    public LoginWindow() {

        // Window Setup
        setTitle("Flight Reservation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 330);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(SLATE);

        // ======= Title =======
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(NAVY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ======= Form Panel =======
        JPanel formPanel = new JPanel();
        formPanel.setBackground(SLATE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label
        JLabel userLabel = styledLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        usernameField = styledTextField();
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(usernameField, gbc);

        // Password Label
        JLabel passLabel = styledLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passLabel, gbc);

        passwordField = styledPasswordField();
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ======= Buttons Panel =======
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(SLATE);
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 60, 30, 60));

        JButton specialLoginBtn = styledButton("Special Login");
        JButton loginBtn = styledButton("Login");
        JButton guestBtn = styledButton("Continue as Guest");

        buttonPanel.add(specialLoginBtn);
        buttonPanel.add(loginBtn);
        buttonPanel.add(guestBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ======= Event Listeners =======

        specialLoginBtn.addActionListener(e -> {
            new SystemAdminLoginWindow().setVisible(true);
            dispose();
        });

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            attemptLogin(user, pass);
        });

        guestBtn.addActionListener(e -> {
            new MainWindow(null).setVisible(true); // Guest mode
            dispose();
        });
    }

    // =============================
    //        Style Helpers
    // =============================

    private JLabel styledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(NAVY);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private JTextField styledTextField() {
        JTextField field = new JTextField(15);
        field.setPreferredSize(new Dimension(180, 32));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(WHITE);
        field.setForeground(NAVY);
        return field;
    }

    private JPasswordField styledPasswordField() {
        JPasswordField field = new JPasswordField(15);
        field.setPreferredSize(new Dimension(180, 32));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(WHITE);
        field.setForeground(NAVY);
        return field;
    }

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setBackground(SLATE);
        btn.setForeground(NAVY);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setBorder(BorderFactory.createLineBorder(NAVY, 2));
        return btn;
    }

    // =============================
    //       Auth Logic
    // =============================

    private void attemptLogin(String username, String password) {

        Customer customer = customerService.login(username, password);

        if (customer != null) {
            JOptionPane.showMessageDialog(this, "Login Successful!");

            new MainWindow(customer).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
}
