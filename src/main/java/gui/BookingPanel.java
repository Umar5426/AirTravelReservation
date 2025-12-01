package main.java.gui;

import main.java.model.Customer;
import main.java.model.Flight;
import main.java.model.Reservation;
import main.java.model.PaymentSystem;
import main.java.service.BookingService;
import main.java.service.CreditCardPayment;
import main.java.service.PayPalPayment;

import javax.swing.*;
import java.awt.*;

public class BookingPanel extends JFrame {

    private Customer customer;
    private Flight flight;

    private JComboBox<String> methodSelector;

    // Credit Card fields
    private JTextField ccNumberField;
    private JTextField ccExpiryField;
    private JTextField ccCVVField;
    private JTextField ccNameField;

    // PayPal fields
    private JTextField paypalEmailField;
    private JPasswordField paypalPasswordField;

    private JPanel paymentFieldsPanel;

    private BookingService bookingService = new BookingService();

    public BookingPanel(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;

        setTitle("Booking Summary");
        setSize(550, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TITLE =====
        JLabel title = new JLabel("Confirm Your Booking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // ===== FLIGHT INFO =====
        JPanel info = new JPanel();
        info.setLayout(new GridLayout(6, 1, 6, 6));
        info.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        info.add(new JLabel("Flight Code: " + flight.getFlightCode()));
        info.add(new JLabel("Airline: " + flight.getAirLine()));
        info.add(new JLabel("Date: " + flight.getFlightDate()));
        info.add(new JLabel("From: " + flight.getDepartureAreaCode()));
        info.add(new JLabel("To: " + flight.getArrivalAreaCode()));
        info.add(new JLabel("Price: $500"));

        add(info, BorderLayout.CENTER);

        // ===== PAYMENT SECTION =====
        JPanel paymentSection = new JPanel();
        paymentSection.setLayout(new BorderLayout());
        paymentSection.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JLabel payTitle = new JLabel("Payment Method:");
        payTitle.setFont(new Font("Arial", Font.BOLD, 16));
        paymentSection.add(payTitle, BorderLayout.NORTH);

        methodSelector = new JComboBox<>(new String[]{"Credit Card", "PayPal"});
        paymentSection.add(methodSelector, BorderLayout.CENTER);

        paymentFieldsPanel = new JPanel();
        paymentFieldsPanel.setLayout(new BoxLayout(paymentFieldsPanel, BoxLayout.Y_AXIS));
        updatePaymentFields("Credit Card");

        paymentSection.add(paymentFieldsPanel, BorderLayout.SOUTH);

        add(paymentSection, BorderLayout.EAST);

        methodSelector.addActionListener(e -> {
            updatePaymentFields((String) methodSelector.getSelectedItem());
        });

        // ===== BUTTONS =====
        JPanel bottom = new JPanel();

        JButton payBtn = new JButton("Pay Now");
        JButton backBtn = new JButton("Back");

        payBtn.addActionListener(e -> processPayment());
        backBtn.addActionListener(e -> goBack());

        bottom.add(payBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);
    }

    // =====================================================
    // UPDATE PAYMENT FIELDS BASED ON SELECTED METHOD
    // =====================================================
    private void updatePaymentFields(String method) {
        paymentFieldsPanel.removeAll();

        if (method.equals("Credit Card")) {
            ccNumberField = new JTextField();
            ccExpiryField = new JTextField();
            ccCVVField = new JTextField();
            ccNameField = new JTextField();

            paymentFieldsPanel.add(new JLabel("Card Number:"));
            paymentFieldsPanel.add(ccNumberField);

            paymentFieldsPanel.add(new JLabel("Expiry (MM/YY):"));
            paymentFieldsPanel.add(ccExpiryField);

            paymentFieldsPanel.add(new JLabel("CVV:"));
            paymentFieldsPanel.add(ccCVVField);

            paymentFieldsPanel.add(new JLabel("Cardholder Name:"));
            paymentFieldsPanel.add(ccNameField);

        } else {
            paypalEmailField = new JTextField();
            paypalPasswordField = new JPasswordField();

            paymentFieldsPanel.add(new JLabel("PayPal Email:"));
            paymentFieldsPanel.add(paypalEmailField);

            paymentFieldsPanel.add(new JLabel("PayPal Password:"));
            paymentFieldsPanel.add(paypalPasswordField);
        }

        paymentFieldsPanel.revalidate();
        paymentFieldsPanel.repaint();
    }

    // =====================================================
    // PROCESS PAYMENT + RESERVATION
    // =====================================================
    private void processPayment() {

        if (customer == null) {
            JOptionPane.showMessageDialog(this, "Guests must log in before booking.");
            return;
        }

        PaymentSystem payment = new PaymentSystem();
        boolean success = false;

        String method = (String) methodSelector.getSelectedItem();

        if (method.equals("Credit Card")) {

            payment.setPaymentStrategy(new CreditCardPayment(ccNumberField.getText(), customer));

            success = payment.pay(customer, flight.getPrice());

        } else {

            payment.setPaymentStrategy(new PayPalPayment(customer, paypalEmailField.getText()));

            success = payment.pay(customer, flight.getPrice());
        }

        if (!success) {
            JOptionPane.showMessageDialog(this, "Payment Error! Please try again.");
            return;
        }

        // ===== If payment succeeded =====
        Reservation reservation = bookingService.makeReservation(flight, flight.getPrice());

        if (reservation == null) {
            JOptionPane.showMessageDialog(this, "Reservation failed, please try later.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Reservation Confirmed!");

        new MainWindow(customer).setVisible(true);
        dispose();
    }

    private void goBack() {
        new FlightManagementPanel(customer, flight).setVisible(true);
        dispose();
    }
}
