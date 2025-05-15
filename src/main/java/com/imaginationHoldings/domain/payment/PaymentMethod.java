package com.imaginationHoldings.domain.payment;

public interface PaymentMethod {
    void processPayment();
    PaymentType getType();
    double getTax();
}
