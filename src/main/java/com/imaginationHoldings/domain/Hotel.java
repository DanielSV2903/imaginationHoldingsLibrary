package com.imaginationHoldings.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private List<Room> rooms;

    public Hotel(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        rooms = new ArrayList<Room>();
    }

    public Hotel() {
    }

    public void registerRooms(Room room){
        rooms.add(room);
    }
    public Room findRoomByNumber(int roomNumber) throws HotelException {
        if (rooms.isEmpty())
            throw new HotelException("El hotel no tiene cuartos registrados");
        for (Room room:rooms){
            if (room.getRoomNumber()==roomNumber)
                return room;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
            return"Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel hotel)) return false;
        return id == hotel.id;
    }
}
