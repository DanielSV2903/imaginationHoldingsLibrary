package com.imaginationHoldings.domain.payment;

public class CashPayment implements PaymentMethod{
    private final PaymentType PAYMENT_TYPE=PaymentType.CASH;
    private double amount;
    public CashPayment(double amount) {
        this.amount=amount;
    }

    @Override
    public void processPayment() {

    }

    @Override
    public PaymentType getType() {
        return PAYMENT_TYPE;
    }

    @Override
    public double getTax() {
        return 0;
    }

    public double getAmount() {
        return amount;
    }
}
