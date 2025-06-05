package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingDataTest {

    private File hotelFile;
    private File roomFile;
    private File bookingFile;
    private File guestFile;
    private RoomData roomData;
    private HotelData hotelData;
    private BookingData bookingData;
    private GuestData guestData;
    private HotelServiceData hotelServiceData;

    @BeforeEach
    void setUp() throws IOException {
        hotelFile = new File("test_hotels.dat");
        roomFile = new File("test_rooms.dat");
        bookingFile = new File("test_books.dat");
        guestFile = new File("test_guests.dat");

        if (hotelFile.exists()) hotelFile.delete();
        if (roomFile.exists()) roomFile.delete();
        if (bookingFile.exists()) bookingFile.delete();

        hotelData = new HotelData(hotelFile);
        roomData = new RoomData(roomFile);
        bookingData=new BookingData(bookingFile);
        guestData=new GuestData(guestFile);
        hotelServiceData=new HotelServiceData(hotelFile,roomFile,guestFile,bookingFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        hotelServiceData.close();
        hotelFile.delete();
        roomFile.delete();
        roomData.close();
        bookingFile.delete();
        bookingData.close();
        guestFile.delete();
        guestData.close();
    }

    @Test
    void insert_test() {
        try {
            Hotel hotel = new Hotel(1, "ImaginacionLand Tobosi", "Tobosi");

            Room room = new Room(505, RoomType.SUITE, hotel, "Tobosi");
            Room room2 = new Room(191, RoomType.FAMILY, hotel, "Tobosi");

            LocalDate startDate = LocalDate.of(2020, 1, 1);
            LocalDate endDate = LocalDate.of(2020, 2, 1);

            StayPeriod stp = new StayPeriod(startDate, endDate);
            Guest guest = new Guest("Aidan", "Murillo Corrales", "Hombre", 123, "20/07/2006", room, stp);
            Guest guest2 = new Guest("Javier", "Moreno Sibaja", "Hombre", 231, "11/09/2001", room2, stp);

            Booking booking = new Booking(12, room, guest, 2, stp);
            Booking booking2 = new Booking(13, room2, guest2, 5, stp);

            bookingData.insert(booking);
            bookingData.insert(booking2);

            List<Booking> bookings = bookingData.findAll();

            Booking firstBooking = bookings.getFirst();
            Booking secondBooking = bookings.get(1);

            assertEquals(2, bookingData.findAll().size());
            assertEquals(505, firstBooking.getRoom().getRoomNumber());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void delete_test() {
        try {
            Hotel hotel = new Hotel(1, "ImaginacionLand Tobosi", "Tobosi");

            Room room = new Room(505, RoomType.SUITE, hotel, "Tobosi");
            Room room2 = new Room(191, RoomType.FAMILY, hotel, "Tobosi");

            LocalDate startDate = LocalDate.of(2020, 1, 1);
            LocalDate endDate = LocalDate.of(2020, 2, 1);

            StayPeriod stp = new StayPeriod(startDate, endDate);
            Guest guest = new Guest("Aidan", "Murillo Corrales", "Hombre", 123, "20/07/2006", room, stp);
            Guest guest2 = new Guest("Javier", "Moreno Sibaja", "Hombre", 231, "11/09/2001", room2, stp);

            Booking booking = new Booking(12, room, guest, 2, stp);
            Booking booking2 = new Booking(13, room2, guest2, 5, stp);

            bookingData.insert(booking);
            bookingData.insert(booking2);

            List<Booking> bookings = bookingData.findAll();

            Booking firstBooking = bookings.getFirst();
            Booking secondBooking = bookings.get(1);

            int secondRoomHotelId = secondBooking.getRoom().getHotel().getId();

            bookingData.delete(secondBooking.getId());

            assertEquals(505, firstBooking.getRoom().getRoomNumber());
            assertEquals(1, secondRoomHotelId);
            assertEquals(1, bookingData.findAll().size());


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RoomException e) {
            throw new RuntimeException(e);
        }
    }
}