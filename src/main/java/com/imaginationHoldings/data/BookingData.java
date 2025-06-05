package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingData {
    private RandomAccessFile raf;
    private static final int ID_SIZE = 4;              // int
    private static final int ROOM_ID_SIZE = 4;         // int
    private static final int ROOM_HOTEL_ID_SIZE = 4;         // int
    private static final int GUEST_ID_SIZE = 4;        // int
    private static final int DATE_SIZE = 12;           // "yyyy-MM-dd" (10 chars + 2 padding)
    private static final int RECORD_SIZE = ID_SIZE + ROOM_ID_SIZE + ROOM_HOTEL_ID_SIZE + GUEST_ID_SIZE + 4 + 2 * DATE_SIZE;

    public BookingData(String filePath) throws IOException {
        this.raf = new RandomAccessFile(filePath, "rw");
    }
    public BookingData(File file) throws IOException {
        this.raf = new RandomAccessFile(file, "rw");
    }

    public void insert(Booking booking) throws IOException {
        raf.seek(raf.length());
        raf.writeInt(booking.getId());
        if (booking.getRoom() != null){
            raf.writeInt(booking.getRoom().getRoomNumber());
            raf.writeInt(booking.getRoom().getHotel().getId());
        } else {
            raf.writeInt(-1);
//            raf.writeInt(-1);
        }
        raf.writeInt(booking.getGuest().getId());
        raf.writeInt(booking.getGuestAmount());
        writeDate(booking.getStayPeriod().getCheckInDate().toString());
        writeDate(booking.getStayPeriod().getCheckOutDate().toString());
    }

    private void writeDate(String date) throws IOException {
        byte[] dateBytes = date.getBytes(StandardCharsets.UTF_8);
        byte[] padded = new byte[DATE_SIZE];
        System.arraycopy(dateBytes, 0, padded, 0, Math.min(dateBytes.length, DATE_SIZE));
        raf.write(padded);
    }

    private String readDate() throws IOException {
        byte[] bytes = new byte[DATE_SIZE];
        raf.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8).trim();
    }

    public List<Booking> findAll() throws IOException {
        List<Booking> bookings = new ArrayList<>();
        raf.seek(0);

        while (raf.getFilePointer() < raf.length()) {
            int id = raf.readInt();
            int roomId = raf.readInt();
            int hotelId = raf.readInt(); // ← leer el hotelId
            int guestId = raf.readInt();
            int guestAmount = raf.readInt(); // ← leer el guestAmount
            String startDate = readDate();
            String endDate = readDate();

            // Reconstrucción del objeto Room y Guest (parcialmente)
            Room room = new Room(roomId);
            Hotel hotel = new Hotel(hotelId);
            room.setHotel(hotel);

            Booking booking = new Booking(
                    id,
                    room,
                    new Guest(guestId),
                    guestAmount,
                    new StayPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate))
            );
            bookings.add(booking);
        }

        return bookings;
    }

    public Booking findById(int id) throws IOException {
        List<Booking> bookings = findAll();
        for (Booking booking : bookings) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    public boolean update(Booking booking) throws IOException {
        raf.seek(0);

        while (raf.getFilePointer() < raf.length()) {
            long recordStart = raf.getFilePointer();
            int currentId = raf.readInt();

            if (currentId == booking.getId()) {
                raf.seek(recordStart);  // Volver al inicio del registro

                raf.writeInt(booking.getId());
                if (booking.getRoom() != null) {
                    raf.writeInt(booking.getRoom().getRoomNumber());
                    raf.writeInt(booking.getRoom().getHotel().getId()); // ← guardar hotelId
                } else {
                    raf.writeInt(-1); // roomId
                    raf.writeInt(-1); // hotelId
                }
                raf.writeInt(booking.getGuest().getId());
                raf.writeInt(booking.getGuestAmount());
                writeDate(booking.getStayPeriod().getCheckInDate().toString());
                writeDate(booking.getStayPeriod().getCheckOutDate().toString());

                return true;
            } else {
                raf.seek(recordStart + RECORD_SIZE);
            }
        }

        return false;
    }

    public boolean delete(int bookingId) throws IOException, RoomException {
        List<Booking> toKeep = new ArrayList<>();

        boolean found = false;
        for (Booking b : findAll()) {
            if (b.getId() != bookingId) {
                toKeep.add(b);
            } else {
                found = true;
            }
        }

        if (found) {
            raf.setLength(0); // Truncar archivo
            for (Booking b : toKeep) {
                insert(b);
            }
        }

        return found;
    }

    public boolean checkAvailabilityOnSP(int roomNumber,int hotelID,StayPeriod stayPeriod) throws IOException {
            List<Booking> allBookings = findAll();
            for (Booking b : allBookings) {
                if (b.getRoom() != null && b.getRoom().getRoomNumber() == roomNumber&&b.getRoom().getHotel().getId() == hotelID) {
                    StayPeriod bookedPeriod = b.getStayPeriod();

                    boolean overlaps = !stayPeriod.getCheckOutDate().isBefore(bookedPeriod.getCheckInDate()) &&
                            !stayPeriod.getCheckInDate().isAfter(bookedPeriod.getCheckOutDate());

                    if (overlaps) {
                        return false;
                    }
                }
            }
        return true;
    }


    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }
}
