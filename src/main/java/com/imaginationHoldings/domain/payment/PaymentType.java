package com.imaginationHoldings.domain.payment;

public enum PaymentType {
    CARD("Card"),
    PAYPAL("PayPal"),
    SINPE("SINPE"),
    CASH("Cash");

    private final String name;

    private PaymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }
}
