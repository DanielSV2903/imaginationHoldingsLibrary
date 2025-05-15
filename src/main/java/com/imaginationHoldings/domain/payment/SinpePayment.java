package com.imaginationHoldings.domain.payment;

public class SinpePayment implements PaymentMethod {
    private int idTransaction;
    private int amount;
    private String description;
    private final PaymentType PAYMENT_TYPE=PaymentType.SINPE;

    public SinpePayment(int amount, String description) {
//        this.idTransaction = idTransaction;
        this.amount = amount;
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
        return 0;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SinpePayment{" +
                "idTransaction=" + idTransaction +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", PAYMENT_TYPE=" + PAYMENT_TYPE +
                '}';
    }
}
