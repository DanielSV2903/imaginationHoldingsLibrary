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
    private static final int GUEST_ID_SIZE = 4;        // int
    private static final int DATE_SIZE = 12;           // "yyyy-MM-dd" (10 chars + 2 padding)
    private static final int RECORD_SIZE = ID_SIZE + ROOM_ID_SIZE + GUEST_ID_SIZE + 2 * DATE_SIZE;

    public BookingData(String filePath) throws IOException {
        this.raf = new RandomAccessFile(filePath, "rw");
    }
    public BookingData(File file) throws IOException {
        this.raf = new RandomAccessFile(file, "rw");
    }

    public void insert(Booking booking) throws IOException {
        raf.seek(raf.length());
        raf.writeInt(booking.getId());
        if (booking.getRoom() != null)
            raf.writeInt(booking.getRoom().getRoomNumber());
        else raf.writeInt(-1);
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
            int amount = raf.readInt();
            int guestId = raf.readInt();
            String startDate = readDate();
            String endDate = readDate();

            // Reconstrucción parcial del objeto (Room y Guest pueden ser recuperados con sus respectivos DAOs)
            Booking booking = new Booking(id,
                    new Room(roomId),
                    new Guest(guestId),
                    amount,
                    new StayPeriod(LocalDate.parse(startDate),LocalDate.parse(endDate))
            );
            bookings.add(booking);
        }

        return bookings;
    }
    public boolean update(Booking booking) throws IOException {
        raf.seek(0);

        while (raf.getFilePointer() < raf.length()) {
            long recordStart = raf.getFilePointer();
            int currentId = raf.readInt();

            if (currentId == booking.getId()) {
                raf.seek(recordStart);  // Volver al inicio del registro

                raf.writeInt(booking.getId());
                raf.writeInt(booking.getRoom() != null ? booking.getRoom().getRoomNumber() : -1);
                raf.writeInt(booking.getGuest().getId());
                raf.writeInt(booking.getGuestAmount());
                writeDate(booking.getStayPeriod().getCheckInDate().toString());
                writeDate(booking.getStayPeriod().getCheckOutDate().toString());

                return true;
            } else {
                raf.seek(recordStart + RECORD_SIZE);
            }
        }

        return false; // No encontrado
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

    public boolean checkAvailabilityOnSP(int roomNumber,StayPeriod stayPeriod) throws IOException {
            List<Booking> allBookings = findAll();
            for (Booking b : allBookings) {
                if (b.getRoom() != null && b.getRoom().getRoomNumber() == roomNumber) {
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
