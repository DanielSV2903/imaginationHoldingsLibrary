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

class HotelDataTest {
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
        hotelData.close();
        hotelFile.delete();
    }

    @Test
    void insert_test() {
        try {
            Hotel hotel = new Hotel(1, "ImaginacionLand Tobosi", "Tobosi");
            Hotel hotel2 = new Hotel(12, "ImaginacionLand Paraiso", "Paraiso");

            hotelData.insert(hotel);
            hotelData.insert(hotel2);

            List<Hotel> hotels = hotelData.findAll();

            Hotel firstHotel = hotels.get(0);
            Hotel secondHotel = hotels.get(1);

            assertEquals(2, hotelData.findAll().size());
            assertEquals(1, firstHotel.getId());
            assertEquals("Paraiso", secondHotel.getAddress());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void delete_test() {
        try {
            Hotel hotel = new Hotel(1, "ImaginacionLand Tobosi", "Tobosi");
            Hotel hotel2 = new Hotel(12, "ImaginacionLand Paraiso", "Paraiso");
            Hotel hotel3 = new Hotel(5, "ImaginacionLand California", "Los Angeles");

            hotelData.insert(hotel);
            hotelData.insert(hotel2);
            hotelData.insert(hotel3);

            List<Hotel> hotels = hotelData.findAll();

            hotelData.delete(hotels.getLast().getId());

            assertEquals(2, hotelData.findAll().size());
            assertEquals(1, hotels.get(0).getId());
            assertEquals("Paraiso", hotels.get(1).getAddress());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}