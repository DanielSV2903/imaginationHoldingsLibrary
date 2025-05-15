package com.imaginationHoldings.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


class PersonTest {

    @Test
    void testToString() {
        Person p1=new Person("Daniel","Sánchez","M",20,119310248);
        System.out.println(p1);
        Company imagination=new Company(1,"Imagination Holdings","Costa Rica","email.com");
        Hotel hotel=new Hotel(1,"ImaginacionLand Tobosi","Tobosi",imagination);
        Room room01=new Room(1,RoomType.SUITE,hotel,hotel.getAddress());

        StayPeriod stayPeriod=new StayPeriod(LocalDate.now(),LocalDate.of(2025,5,18));

        Guest guest=new Guest(p1.getFirstName(),p1.getLastName(),p1.getGender(),p1.getId(),"29/03/05",room01,stayPeriod);
        System.out.println(guest);
    }
}