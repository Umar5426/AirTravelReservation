package main.java.service;

import main.java.model.Customer;

public class PayPalPayment implements PaymentStrategy {
    private String email;
    private Customer cardHolder;

    public PayPalPayment(Customer cardHolder, String email) {
        this.email = email;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(Customer cardHolder, double amount) {
        System.out.println(cardHolder.fname + "paid " + amount + " using PayPal account: " + email);
    }
}
