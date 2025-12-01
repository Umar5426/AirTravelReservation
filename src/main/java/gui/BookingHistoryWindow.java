package main.java.gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import main.java.model.Reservation;
import main.java.service.CustomerService;

public class BookingHistoryWindow extends JFrame {

    private CustomerService customerService;

    public BookingHistoryWindow(CustomerService customerService) {
        this.customerService = customerService;

        setTitle("Your Booking History");
        setSize(650, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("Your Reservations", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(title, BorderLayout.NORTH);

        // ===== Main Panel Inside ScrollPane =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // ===== FETCH RESERVATIONS =====
        List<Reservation> reservations = customerService.viewBookingHistory();

        if (reservations.isEmpty()) {
            JLabel emptyLabel = new JLabel("No reservations found.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            add(emptyLabel, BorderLayout.CENTER);
            return;
        }

        for (Reservation r : reservations) {
            JPanel card = createReservationCard(r);
            mainPanel.add(card);
            mainPanel.add(Box.createVerticalStrut(15));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Bottom Buttons =====
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.addActionListener(e -> {
            new MainWindow(false).setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);
    }

    // =====================================================================
    // CARD UI FOR EACH RESERVATION
    // =====================================================================
    private JPanel createReservationCard(Reservation r) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(new Color(245, 245, 245));

        // ===== LEFT SIDE: FLIGHT INFO =====
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        info.add(new JLabel("Reservation ID: " + r.getReservationID()));
        info.add(new JLabel("Flight: " + r.getFlight().getFlightCode()));
        info.add(new JLabel("Route: " + r.getFlight().getDepartureAreaCode() + " → " + r.getFlight().getArrivalAreaCode()));
        info.add(new JLabel("Date: " + r.getDateBooked()));
        info.add(new JLabel("Price: (N/A)"));

        card.add(info, BorderLayout.CENTER);

        // ===== RIGHT SIDE: ACTION BUTTONS =====
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 1, 10, 10));

        JButton editBtn = new JButton("Edit Reservation");
        JButton cancelBtn = new JButton("Cancel Reservation");

        editBtn.addActionListener(e -> editReservation(r));
        cancelBtn.addActionListener(e -> cancelReservation(r));

        buttons.add(editBtn);
        buttons.add(cancelBtn);

        card.add(buttons, BorderLayout.EAST);

        return card;
    }

    // =====================================================================
    // SERVICE CALLS
    // =====================================================================
    private void editReservation(Reservation r) {
        JOptionPane.showMessageDialog(this,
                "Editing reservation " + r.getReservationID() + "… (open edit window here)");
    }

    private void cancelReservation(Reservation r) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel reservation " + r.getReservationID() + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            customerService.cancelReservation(r.getReservationID());
            JOptionPane.showMessageDialog(this, "Reservation cancelled.");

            // Refresh page
            new BookingHistoryWindow(customerService).setVisible(true);
            dispose();
        }
    }
}
