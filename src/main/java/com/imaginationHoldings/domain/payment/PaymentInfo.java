package com.imaginationHoldings.domain.payment;

public class PaymentInfo {
    private PaymentType paymentType;
    private int clientID;
    private double amount;

    public PaymentInfo(PaymentType paymentType, int clientID, double amount) {
        this.paymentType = paymentType;
        this.clientID = clientID;
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "paymentType=" + paymentType +
                ", clientID=" + clientID +
                ", amount=" + amount +
                '}';
    }
}
