package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Hotel;
import com.imaginationHoldings.domain.Room;
import com.imaginationHoldings.domain.RoomType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomDataTest {

    private File testFile;
    private RoomData roomData;

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File("rooms_test.dat");
        if (testFile.exists()) testFile.delete();
        roomData = new RoomData(testFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        roomData.close();
        if (testFile.exists()) testFile.delete();
    }

    @Test
    void insertAndReadRooms() throws IOException {
        Hotel hotel = new Hotel(1, "ImaginationLand", "Tobosi");

        Room room1 = new Room(101, RoomType.DOUBLE, hotel, "Tobosi");
        Room room2 = new Room(102, RoomType.SUITE, hotel, "Cartago");

        roomData.insert(room1);
        roomData.insert(room2);

        List<Room> rooms = roomData.findAll();

        assertEquals(2, rooms.size());

        Room r1 = rooms.get(0);
        assertEquals(101, r1.getRoomNumber());
        assertEquals(RoomType.DOUBLE, r1.getRoomType());
        assertEquals("Tobosi", r1.getLocation());

        Room r2 = rooms.get(1);
        assertEquals(102, r2.getRoomNumber());
        assertEquals(RoomType.SUITE, r2.getRoomType());
        assertEquals("Cartago", r2.getLocation());

        assertTrue(r1.isAvailable());
        assertTrue(r2.isAvailable());
    }
}