package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelServiceData {
    private HotelData hotelData;
    private RoomData roomData;
    private GuestData guestData;
    private BookingData bookingData;


    public HotelServiceData(File hotelFile, File roomFile, File guestFile, File bookingFile) throws IOException {
        this.hotelData = new HotelData(hotelFile);
        this.roomData = new RoomData(roomFile);
        this.guestData = new GuestData(guestFile);
        this.bookingData = new BookingData(bookingFile);
    }
    public HotelServiceData(HotelData hotelFile, RoomData roomFile, GuestData guestFile, BookingData bookingFile) throws IOException {
        this.hotelData = hotelFile;
        this.roomData = roomFile;
        this.guestData = guestFile;
        this.bookingData =bookingFile;
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
        List<Room> rooms = roomData.findAll(hotels);
        List<Room> aux=new ArrayList<>();
        for (Room room : rooms) {
            if (room.getHotel().getId() == id) {
                aux.add(room);
            }
        }
        Hotel hotel =hotelData.findById(id);
        hotel.setRooms(aux);
        return hotel;
    }

    public Room findRoomByID(int roomNumber) throws IOException {
        List<Room> rooms = roomData.findAll(hotelData.findAll());
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public List<Booking> getAllBookings() throws IOException, RoomException {
        List<Booking> bookings = new ArrayList<>();
        List<Room> rooms = roomData.findAll(hotelData.findAll());

        for (Booking partial : bookingData.findAll()) {
            Room fullRoom = findRoomByIdFromList(rooms,partial.getRoom().getRoomNumber());
            Hotel hotel=hotelData.findById(fullRoom.getHotel().getId());
            fullRoom.setHotel(hotel);
            Guest fullGuest = guestData.findById(partial.getGuest().getId());

            if (fullRoom != null ) {
                Booking booking = new Booking(
                        partial.getId(),
                        fullRoom,
                        fullGuest,
                        partial.getGuestAmount(),
                        partial.getStayPeriod()
                );
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public Room findRoomByIdFromList(List<Room> rooms, int id) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == id) {
                return room;
            }
        }
        return null;
    }
    public Room findAvaibleRoom(RoomType type) throws IOException {
        List<Room>rooms=roomData.findAll(hotelData.findAll());
        for (Room room : rooms) {
            if (room.getRoomType().equals(type)&&room.isAvailable()) {
                return room;
            }
        }
        return null;//no hay cuartos del tipo type disponibles
    }
    public Guest findGuestById(int id) throws IOException {
        List<Guest> guests= guestData.findAll();
        for (Guest guest : guests) {
            if (guest.getId() == id) {
                return guest;
            }
        }
        return null;//not found
    }

    public void close() throws IOException {
        hotelData.close();
        roomData.close();
        guestData.close();
        bookingData.close();
    }
}
