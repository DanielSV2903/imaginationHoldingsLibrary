package com.imaginationHoldings.domain.payment;

public class PayPalPayment implements PaymentMethod{
    private String payerId;
    private double amount;
    private String currency;
    private String description;
    private final PaymentType PAYMENT_TYPE=PaymentType.PAYPAL;

    public PayPalPayment(double amount, String currency, String description) {
//        this.payerId = payerId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
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
        return amount*0.17;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PayPalPayment{" +
                "payerId='" + payerId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                ", PAYMENT_TYPE=" + PAYMENT_TYPE +
                '}';
    }
}
