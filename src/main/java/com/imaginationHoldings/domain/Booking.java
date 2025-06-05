package com.imaginationHoldings.domain;


import java.io.Serializable;
import java.util.Objects;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private Room room;
    private Guest guest;
    private StayPeriod stayPeriod;
    private int id;
    private int guestAmount;

    public Booking(int id, Room room, Guest guest, StayPeriod stayPeriod) throws RoomException {
        if(room.isAvailable()){
            this.id = id;
            this.room = room;
            this.guest = guest;
            this.stayPeriod = stayPeriod;
        }else{
            throw new RoomException("Room is not available, book later");
        }
    }

    public Booking( int id,Room room, Guest guest,int guestAmount,StayPeriod stayPeriod) {
            this.room = room;
            this.guest = guest;
            this.stayPeriod = stayPeriod;
            this.id = id;
            this.guestAmount = guestAmount;
    }

    public int getGuestAmount() {
        return guestAmount;
    }

    public void setGuestAmount(int guestAmount) {
        this.guestAmount = guestAmount;
    }

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public long getNumberOfNightsPeriod() {
        return this.stayPeriod.getNumberOfNights();
    }
    public StayPeriod getStayPeriod() {
        return this.stayPeriod;
    }

    public void setStayPeriod(StayPeriod stayPeriod) {
        this.stayPeriod = stayPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;
        return id == booking.id && Objects.equals(room, booking.room) && Objects.equals(guest, booking.guest);
    }

    @Override
    public String toString() {
        return "Booking{" +
                room +
                ","+guest +
                ","+ stayPeriod +
                ", id=(" + id +
                "),"+
                '}';
    }
}
