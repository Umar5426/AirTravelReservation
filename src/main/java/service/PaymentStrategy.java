package main.java.service;

import main.java.model.Customer;

public interface PaymentStrategy {
    void pay(Customer cardHolder, double amount);
}

