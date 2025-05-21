package com.imaginationHoldings.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {
    @Test
    void instanceHotelTest(){

        String name="ImaginationLand";
        String address="Puerto Viejo";
        Hotel hotel=new Hotel(1,name,address);
        int expectedHotelID=1;
        String expectedName="ImaginationLand";
        String expectedAddress="Puerto Viejo";
        assertEquals(expectedHotelID,hotel.getId());
        assertEquals(expectedName,hotel.getName());
        assertEquals(expectedAddress,hotel.getAddress());

    }

}