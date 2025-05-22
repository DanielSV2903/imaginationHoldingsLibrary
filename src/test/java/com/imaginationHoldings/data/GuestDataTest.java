package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestDataTest {
    private File guestFile;
    private GuestData guestData;
    private File roomFile;
    private RoomData roomData;

    @BeforeEach
    void setUp() throws IOException {
        guestFile = new File("test_guest.dat");
        roomFile = new File("test_room.dat");

        if (guestFile.exists()) guestFile.delete();
        if (roomFile.exists()) roomFile.delete();
        guestData = new GuestData(guestFile);
        roomData = new RoomData(roomFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        guestData.close();
        roomData.close();
        guestFile.delete();
        roomFile.delete();
    }

    @Test
    void testGuestData() throws IOException {
        StayPeriod stayPeriod=new StayPeriod(LocalDate.now(),LocalDate.of(2025,5,23)); String name="ImaginationLand";
        String address="Puerto Viejo";
        Hotel hotel=new Hotel(1,name,address);
        Room room01=new Room(1, RoomType.SUITE,hotel,hotel.getAddress());
        Guest guest1=new Guest("Daniel","Sánchez","M",119310248,"29/03/05",room01,stayPeriod);
        guestData.insert(guest1);
        List<Room> rooms=new ArrayList<>();
        rooms.add(room01);
        List<Guest> guests=guestData.findAll(rooms);
        assertEquals(1, guests.size());
        Guest guest=guests.get(0);
        assertEquals(guest1,guest);
        assertEquals(guest1.getGuestRoom(),guest.getGuestRoom());

    }
}