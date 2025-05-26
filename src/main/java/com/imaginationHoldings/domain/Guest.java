
package com.imaginationHoldings.domain;

import java.time.LocalDate;
import java.util.Date;

public class Guest extends Person {
    private Room guestRoom;
    private StayPeriod stayPeriod;
    boolean checkedOut, checkedIn;

    public Guest(String firstName, String lastName, String gender, int id, String birthDate, Room guestRoom, StayPeriod stayPeriod) {
        super(firstName, lastName, gender, id, birthDate);
        this.guestRoom = guestRoom;
        this.stayPeriod = stayPeriod;
        checkedOut=checkedIn= false;
    }

    public Guest(String firstName, String lastName, String gender, int passportID, LocalDate birthDate, Room guestRoom, StayPeriod stayPeriod) {
        super(firstName, lastName, gender, passportID, birthDate);
        this.guestRoom = guestRoom;
        this.stayPeriod = stayPeriod;
        checkedOut=checkedIn= false;
    }

    public Guest(String firstName, String lastName,String gender, int id, String birthDate) {
        super(firstName, lastName, gender, id,birthDate);
        this.guestRoom = null ;
        this.stayPeriod = null;
        checkedOut=checkedIn= false;
    }

    public Room getGuestRoom() {
        return guestRoom;
    }

    public void setGuestRoom(Room guestRoom) {
        this.guestRoom = guestRoom;
    }

    public StayPeriod getStayPeriod() {
        return stayPeriod;
    }

    public void setStayPeriod(StayPeriod stayPeriod) {
        this.stayPeriod = stayPeriod;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    @Override
    public String toString() {
        return "Guest info: " + super.toString() + "\n" + guestRoom + "\nStay peridod: " + stayPeriod;
    }
}
