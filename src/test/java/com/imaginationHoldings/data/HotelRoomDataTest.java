package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Hotel;
import com.imaginationHoldings.domain.Room;
import com.imaginationHoldings.domain.RoomType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HotelRoomDataTest {

    private File hotelFile;
    private File roomFile;
    private HotelData hotelData;
    private RoomData roomData;

    @BeforeEach
    void setUp() throws IOException {
        hotelFile = new File("test_hotels.dat");
        roomFile = new File("test_rooms.dat");

        if (hotelFile.exists()) hotelFile.delete();
        if (roomFile.exists()) roomFile.delete();

        hotelData = new HotelData(hotelFile);
        roomData = new RoomData(roomFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        hotelData.close();
        roomData.close();
        hotelFile.delete();
        roomFile.delete();
    }

    @Test
    void testInsertAndReadHotelsAndRooms() throws IOException {

        Hotel hotel1 = new Hotel(1, "ImaginaciónLand Tobosi", "Tobosi");
        Hotel hotel2 = new Hotel(2, "ImaginaciónLand Cartago", "Cartago");
        hotelData.insert(hotel1);
        hotelData.insert(hotel2);


        Room room1 = new Room(101, RoomType.SUITE, hotel1, "Tobosi");
        Room room2 = new Room(102, RoomType.DOUBLE, hotel1, "Tobosi");
        Room room3 = new Room(201, RoomType.FAMILY, hotel2, "Cartago");

        roomData.insert(room1);
        roomData.insert(room2);
        roomData.insert(room3);




        List<Hotel> hotels = hotelData.findAll();

        assertEquals(2, hotels.size());

        Hotel h1 = hotels.get(0);
        Hotel h2 = hotels.get(1);

        assertEquals("ImaginaciónLand Tobosi", h1.getName());
        assertEquals("ImaginaciónLand Cartago", h2.getName());

        assertEquals(2, h1.getRooms().size());
        assertEquals(room1,h1.getRooms().get(0));
        assertEquals(room2,h1.getRooms().get(1));
        assertEquals(1, h2.getRooms().size());


        List<Room> rooms = roomData.findAll();
        assertEquals(3, rooms.size());

        for (Room r : rooms) {
            assertNotNull(r.getHotel());
            assertTrue(r.getHotel().getName().contains("ImaginaciónLand"));
        }
    }
}
