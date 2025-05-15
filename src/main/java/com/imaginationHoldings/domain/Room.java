package com.imaginationHoldings.domain;

import java.util.Objects;

public class Room{
    private int id;
    private RoomType roomType;
    private String location;
    private String description;
    private int capacity;
    private Hotel hotel;
    private boolean availability;

    public Room(int id, RoomType roomType, Hotel hotel, String location) {
        this.id = id;
        this.roomType = roomType;
        this.hotel = hotel;
        this.location = location;
        this.availability = true;
        this.capacity = roomType.getCapacity();
        this.description = roomType.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
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

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", hotel=" + hotel +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return id == room.id && roomType == room.roomType && Objects.equals(location, room.location)
                && Objects.equals(hotel, room.hotel);
    }
}
