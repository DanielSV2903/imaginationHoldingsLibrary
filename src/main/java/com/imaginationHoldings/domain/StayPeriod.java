package com.imaginationHoldings.domain;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class StayPeriod {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public StayPeriod(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out cannot be before check-in");
        }
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "From " + checkInDate + " to " + checkOutDate +
                " (" + getNumberOfNights() + " nights)";
    }
}
