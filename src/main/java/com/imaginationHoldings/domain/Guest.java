package com.imaginationHoldings.domain;

import java.util.Date;

public class Guest extends Person {
    private Room guestRoom;
    private StayPeriod stayPeriod;
    boolean checkedOut,checkedIn;

    public Guest(String firstName, String lastName, String gender, int id, String birthDate, Room guestRoom, StayPeriod stayPeriod) {
        super(firstName, lastName, gender, id, birthDate);
        this.guestRoom = guestRoom;
        this.stayPeriod = stayPeriod;
    }

    public Guest(String firstName, String lastName, String gender, int passportID, Date birthDate, Room guestRoom, StayPeriod stayPeriod) {
        super(firstName, lastName, gender, passportID, birthDate);
        this.guestRoom = guestRoom;
        this.stayPeriod = stayPeriod;
    }

    public Guest(String firstName, String lastName, int age, String gender, int id, Room guestRoom, StayPeriod stayPeriod) {
        super(firstName, lastName,gender,age, id);
        this.guestRoom = guestRoom;
        this.stayPeriod = stayPeriod;
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
        return "Guest info: "+super.toString()+"\n"+guestRoom+"\nStay peridod: "+stayPeriod;
    }
}
