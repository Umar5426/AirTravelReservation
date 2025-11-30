package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class SystemAdminLoginWindow extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    public SystemAdminLoginWindow() {
        setTitle("System Admin Login");
        setSize(410, 260);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("System Administrator Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        form.add(new JLabel("Username:"));
        usernameField = new JTextField();
        form.add(usernameField);

        form.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        form.add(passwordField); 
        
        add(form, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");
        btnPanel.add(loginBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        loginBtn.addActionListener(e -> verifyLogin());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void verifyLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        
        if (user.equals("Abdul Shakoor Raed") && pass.equals("Abdul4")) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new AdminWindow().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    
}
