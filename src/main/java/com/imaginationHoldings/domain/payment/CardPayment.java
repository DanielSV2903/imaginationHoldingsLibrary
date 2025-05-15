package com.imaginationHoldings.domain.payment;

public class CardPayment implements PaymentMethod{
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private final PaymentType PAYMENT_TYPE=PaymentType.CARD;

    public CardPayment(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void processPayment() {//TODO
    }

    @Override
    public PaymentType getType() {
        return PAYMENT_TYPE;
    }

    @Override
    public double getTax() {
        return 1.5;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "CardPayment{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", PAYMENT_TYPE=" + PAYMENT_TYPE +
                '}';
    }
}
