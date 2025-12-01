package main.java.service;

import main.java.model.Customer;

public interface PaymentStrategy {
    boolean pay(Customer cardHolder, double amount);
}

