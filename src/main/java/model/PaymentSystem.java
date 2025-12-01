package main.java.model;

import main.java.service.*;

public class PaymentSystem {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean pay(Customer cardHolder, double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment method selected!");
        } else {
            if(paymentStrategy.pay(cardHolder, amount) == true){
                return true;
            }

            return false;
        }

        return false;
    }
}

// Using Credit Card
//       system.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "Umar Khan"));
//     system.pay(100.0);

        // Using PayPal
//       system.setPaymentStrategy(new PayPalPayment("umar@example.com"));
//       system.pay(50.0);
