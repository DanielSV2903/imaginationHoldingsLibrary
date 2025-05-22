package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Guest;
import com.imaginationHoldings.domain.Room;
import com.imaginationHoldings.domain.StayPeriod;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GuestData {
    private static final int ID_SIZE = 4;
    private static final int NAME_SIZE = 30;
    private static final int GENDER_SIZE = 1;
    private static final int BIRTH_DATE_SIZE = 10; // yyyy-MM-dd
    private static final int ROOM_NUMBER_SIZE = 4;
    private static final int CHECKIN_DATE_SIZE = 10; // yyyy-MM-dd
    private static final int CHECKOUT_DATE_SIZE = 10; // yyyy-MM-dd
    private static final int CHECKED_IN_SIZE = 1; // boolean
    private static final int CHECKED_OUT_SIZE = 1; // boolean

    private static final int RECORD_SIZE = ID_SIZE + NAME_SIZE * 2 + GENDER_SIZE + BIRTH_DATE_SIZE +
            ROOM_NUMBER_SIZE + CHECKIN_DATE_SIZE + CHECKOUT_DATE_SIZE + CHECKED_IN_SIZE + CHECKED_OUT_SIZE;

    private RandomAccessFile raf;

    public GuestData(File file) throws IOException {
        this.raf = new RandomAccessFile(file, "rw");
    }

    private byte[] toBytes(String value, int length) {
        byte[] bytes = new byte[length];
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(valueBytes, 0, bytes, 0, Math.min(valueBytes.length, length));
        return bytes;
    }

    private String readString(int length) throws IOException {
        byte[] bytes = new byte[length];
        raf.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8).trim();
    }

    public void insert(Guest guest) throws IOException {
        raf.seek(raf.length());
        raf.writeInt(guest.getId());
        raf.write(toBytes(guest.getFirstName(), NAME_SIZE));
        raf.write(toBytes(guest.getLastName(), NAME_SIZE));
        raf.write(toBytes(guest.getGender(), GENDER_SIZE));
        raf.write(toBytes(guest.getBirthDate().toString(), BIRTH_DATE_SIZE));
        raf.writeInt(guest.getGuestRoom().getRoomNumber());
        raf.write(toBytes(guest.getStayPeriod().getCheckInDate().toString(), CHECKIN_DATE_SIZE));
        raf.write(toBytes(guest.getStayPeriod().getCheckOutDate().toString(), CHECKOUT_DATE_SIZE));
        raf.writeBoolean(guest.isCheckedIn());
        raf.writeBoolean(guest.isCheckedOut());
    }

    public List<Guest> findAll(List<Room> roomList) throws IOException {
        List<Guest> guests = new ArrayList<>();
        raf.seek(0);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        while (raf.getFilePointer() < raf.length()) {
            int id = raf.readInt();
            String firstName = readString(NAME_SIZE);
            String lastName = readString(NAME_SIZE);
            String gender = readString(GENDER_SIZE);
            String birthDateStr = readString(BIRTH_DATE_SIZE);
            int roomNumber = raf.readInt();
            String checkInStr = readString(CHECKIN_DATE_SIZE);
            String checkOutStr = readString(CHECKOUT_DATE_SIZE);
            boolean checkedIn = raf.readBoolean();
            boolean checkedOut = raf.readBoolean();

            Room guestRoom = null;
            for (Room r : roomList) {
                if (r.getRoomNumber() == roomNumber) {
                    guestRoom = r;
                    break;
                }
            }

            StayPeriod stayPeriod = new StayPeriod(
                    LocalDate.parse(checkInStr, dtf),
                    LocalDate.parse(checkOutStr, dtf)
            );

            Guest guest = new Guest(firstName, lastName, gender, id, LocalDate.parse(birthDateStr, dtf), guestRoom, stayPeriod);
            guest.setCheckedIn(checkedIn);
            guest.setCheckedOut(checkedOut);
            guests.add(guest);
        }

        return guests;
    }

    public void close() throws IOException {
        if (raf != null) raf.close();
    }
}
