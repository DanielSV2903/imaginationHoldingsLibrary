package com.imaginationHoldings.domain;

import java.util.Objects;

public class Room{
    private int roomNumber;
    private RoomType roomType;
    private String location;
    private String description;
    private int capacity;
    private Hotel hotel;
    private boolean availability;

    public Room(int roomNumber, RoomType roomType, Hotel hotel, String location) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.hotel = hotel;
        this.location = location;
        this.availability = true;
        this.capacity = roomType.getCapacity();
        this.description = roomType.getDescription();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }
    public String getType() {
        return roomType.getDescription();
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Room{" +
                roomNumber +
                ",Type:" + roomType +
                ",location:" + location + '\'' +
                ", hotel:" + hotel.getName()+","+hotel.getAddress() +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return roomNumber == room.roomNumber && roomType == room.roomType && Objects.equals(location, room.location)
                && Objects.equals(hotel, room.hotel);
    }
}
