package com.imaginationHoldings.domain;


import java.util.Objects;

public class Booking {
    private Room room;
    private Guest guest;
    private StayPeriod stayPeriod;
    private int id;

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
    public long getStayPeriod() {
        return this.stayPeriod.getNumberOfNights();
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
