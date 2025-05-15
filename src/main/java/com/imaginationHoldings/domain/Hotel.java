package com.imaginationHoldings.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private List<Room> rooms;
    private Company company;
    public Hotel(int id, String name, String address,Company company) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.company = company;
        rooms = new ArrayList<Room>();
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", company=" + company.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel hotel)) return false;
        return id == hotel.id && Objects.equals(name, hotel.name) && Objects.equals(company, hotel.company);
    }
}
