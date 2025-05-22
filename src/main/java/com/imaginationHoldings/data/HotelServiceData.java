package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Hotel;
import com.imaginationHoldings.domain.Room;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HotelServiceData {
    private  HotelData hotelData;
    private  RoomData roomData;

    public HotelServiceData(HotelData hotelData, RoomData roomData) {
        this.hotelData = hotelData;
        this.roomData = roomData;
    }
    public HotelServiceData(File hotelData, File roomData) throws IOException {
        this.hotelData = new HotelData(hotelData);
        this.roomData = new RoomData(roomData);
    }

    public List<Hotel> getHotelWithRooms() throws IOException {
        List<Hotel> hotels = hotelData.findAll();
        List<Room> rooms = roomData.findAll(hotels);

        for (Hotel hotel : hotels) {
            for (Room room : rooms) {
                if (room.getHotel().getId() == hotel.getId()) {
                    hotel.registerRooms(room);
                }
            }
        }
        return hotels;
    }
    public Hotel findHotelById(int id) throws IOException {
        List<Hotel> hotels = hotelData.findAll();
        for (Hotel hotel : hotels) {
            if (hotel.getId() == id) {
                return hotel;
            }
        }
        return null;
    }
    public Room findRoomByID(int roomNumber) throws IOException {
        List<Room> rooms=roomData.findAll(hotelData.findAll());
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
    public void close() throws IOException {
        hotelData.close();
        roomData.close();
    }
}
