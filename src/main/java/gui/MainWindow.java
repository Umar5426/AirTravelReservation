package main.java.gui;

import java.awt.*;
import javax.swing.*;
import main.java.service.CustomerService;
import main.java.model.Customer;

public class MainWindow extends JFrame {

    private boolean isGuest;
    private Customer customer;

    // Color Theme (same as LoginWindow)
    private final Color SLATE = new Color(0x2E3B4E);
    private final Color ICE_WHITE = new Color(0xFFFFFF);
    private final Color NAVY = new Color(0x0A1A2F);

    /** When user logs in */
    public MainWindow(Customer customer) {
        this.customer = customer;
        this.isGuest = (customer == null);
        initializeUI();
    }

    /** Manual guest constructor */
    public MainWindow(boolean isGuest) {
        this.isGuest = isGuest;
        initializeUI();
    }

    // ============================================================
    // MAIN WINDOW UI SETUP
    // ============================================================
    private void initializeUI() {
        setTitle("Flight Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(SLATE);

        // ============================================================
        // TITLE BAR
        // ============================================================
        JLabel titleLabel = new JLabel("Book Your Flight", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(NAVY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ============================================================
        // LEFT MENU (buttons)
        // ============================================================
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.setBackground(SLATE);

        JButton searchBtn = styledButton("Search Flights");
        JButton viewBtn = styledButton("View Flight");
        JButton historyBtn = styledButton("Booking History");
        JButton logoutBtn = styledButton("Logout");

        menuPanel.add(searchBtn);
        menuPanel.add(viewBtn);
        menuPanel.add(historyBtn);
        menuPanel.add(logoutBtn);

        // Guest cannot view or see history
        if (isGuest) {
            viewBtn.setEnabled(false);
            historyBtn.setEnabled(false);
        }

        add(menuPanel, BorderLayout.WEST);

        // ============================================================
        // CENTER SEARCH PANEL (Expedia style)
        // ============================================================
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        searchPanel.setBackground(SLATE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JTextField fromField = styledTextField();
        JTextField toField = styledTextField();
        JTextField departDateField = styledTextField();
        JTextField returnDateField = styledTextField();

        JLabel fromLabel = styledLabel("From (Area Code):");
        JLabel toLabel = styledLabel("To (Area Code):");
        JLabel departLabel = styledLabel("Departure Date (YYYY-MM-DD):");
        JLabel returnLabel = styledLabel("Return Date (YYYY-MM-DD):");

        gbc.gridx = 0; gbc.gridy = 0; searchPanel.add(fromLabel, gbc);
        gbc.gridx = 1; searchPanel.add(fromField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; searchPanel.add(toLabel, gbc);
        gbc.gridx = 1; searchPanel.add(toField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; searchPanel.add(departLabel, gbc);
        gbc.gridx = 1; searchPanel.add(departDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; searchPanel.add(returnLabel, gbc);
        gbc.gridx = 1; searchPanel.add(returnDateField, gbc);

        JButton bigSearchButton = styledButton("Search Flights");
        bigSearchButton.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(bigSearchButton, gbc);

        add(searchPanel, BorderLayout.CENTER);

        // ============================================================
        // EVENT LISTENERS
        // ============================================================

        searchBtn.addActionListener(e -> bigSearchButton.doClick());

        bigSearchButton.addActionListener(e -> {
            String from = fromField.getText().trim();
            String to = toField.getText().trim();

            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Enter both departure and arrival area codes.",
                        "Missing Info",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            FlightSearchWindow fsw = new FlightSearchWindow(isGuest);
            fsw.setVisible(true);
            dispose();
        });

        viewBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Viewing flight...");
        });

        historyBtn.addActionListener(e -> {
            new BookingHistoryWindow(new CustomerService()).setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            dispose();
        });
    }

    // ============================================================
    // STYLING HELPERS
    // ============================================================

    private JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(SLATE);
        btn.setForeground(NAVY);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(NAVY, 2));
        return btn;
    }

    private JTextField styledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(180, 32));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(ICE_WHITE);
        field.setForeground(NAVY);
        return field;
    }

    private JLabel styledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(NAVY);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }
}
