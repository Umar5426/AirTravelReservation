package main.java.service;

import main.java.model.Customer;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private Customer cardHolder;

    public CreditCardPayment(String cardNumber, Customer cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(Customer cardHolder, double amount) {
        System.out.println(cardHolder.fname + "Paid " + amount + " using Credit Card: " + cardNumber);
    }
}
