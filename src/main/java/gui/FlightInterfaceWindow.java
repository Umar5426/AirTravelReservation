package main.java.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class FlightInterfaceWindow extends JFrame{
    private JTextField flightCodeField;
    private JTextField originField;
    private JTextField destinationField;

    public FlightInterfaceWindow() {
        setTitle("Flight Management");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Flight Information", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        form.add(new JLabel("Flight Code:"));
        flightCodeField = new JTextField();
        form.add(flightCodeField);

        form.add(new JLabel("Origin:"));
        originField = new JTextField();
        form.add(originField);

        form.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        form.add(destinationField);

        add(form, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Flight");
        JButton updateBtn = new JButton("Update Flight");
        JButton deleteBtn = new JButton("Delete Flight");
        JButton backBtn = new JButton("Back");

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ===== Listeners =====
        addBtn.addActionListener(e -> showMessage("Added"));
        updateBtn.addActionListener(e -> showMessage("Updated"));
        deleteBtn.addActionListener(e -> showMessage("Deleted"));

        backBtn.addActionListener(e -> {
            new AdminWindow().setVisible(true);
            dispose();
        });
    }

    private void showMessage(String action) {
        JOptionPane.showMessageDialog(this,
                "Flight " + action + ":\n" +
                "Code: " + flightCodeField.getText() + "\n" +
                "From: " + originField.getText() + "\n" +
                "To: " + destinationField.getText());
    }

    
}
