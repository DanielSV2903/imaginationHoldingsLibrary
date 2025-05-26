package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Guest;
import com.imaginationHoldings.domain.Hotel;
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
    public GuestData(String path) throws IOException {
        this.raf = new RandomAccessFile(path, "rw");
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
        if (guest.getGuestRoom() != null)
            raf.writeInt(guest.getGuestRoom().getRoomNumber());
        else raf.writeInt(-1);
        if (guest.getStayPeriod() != null){
            raf.write(toBytes(guest.getStayPeriod().getCheckInDate().toString(), CHECKIN_DATE_SIZE));
            raf.write(toBytes(guest.getStayPeriod().getCheckOutDate().toString(), CHECKOUT_DATE_SIZE));
        }
        else{ raf.write(toBytes("null",CHECKIN_DATE_SIZE));
        raf.write(toBytes("null",CHECKOUT_DATE_SIZE));
        }
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
    public List<Guest> findAll() throws IOException {
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

            Guest guest = new Guest(firstName, lastName, gender, id,birthDateStr);
            guest.setCheckedIn(checkedIn);
            guest.setCheckedOut(checkedOut);
            guests.add(guest);
        }

        return guests;
    }
    public Guest findById(int searchId) throws IOException {
        raf.seek(0);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        while (raf.getFilePointer() < raf.length()) {
            long recordStart = raf.getFilePointer(); // Para retroceder si no es el ID buscado
            int id = raf.readInt();

            if (id == searchId) {
                String firstName = readString(NAME_SIZE);
                String lastName = readString(NAME_SIZE);
                String gender = readString(GENDER_SIZE);
                String birthDateStr = readString(BIRTH_DATE_SIZE);
                int roomNumber = raf.readInt();
                String checkInStr = readString(CHECKIN_DATE_SIZE);
                String checkOutStr = readString(CHECKOUT_DATE_SIZE);
                boolean checkedIn = raf.readBoolean();
                boolean checkedOut = raf.readBoolean();

                StayPeriod stayPeriod = new StayPeriod(
                        LocalDate.parse(checkInStr, dtf),
                        LocalDate.parse(checkOutStr, dtf)
                );

                Guest guest = new Guest(firstName, lastName, gender, id, LocalDate.parse(birthDateStr, dtf), null, stayPeriod);
                guest.setCheckedIn(checkedIn);
                guest.setCheckedOut(checkedOut);
                return guest;
            } else {
                raf.seek(recordStart + RECORD_SIZE);
            }
        }

        return null;
    }
    public boolean update(Guest guest) throws IOException {
        raf.seek(0);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        while (raf.getFilePointer() < raf.length()) {
            long recordStart = raf.getFilePointer();
            int id = raf.readInt();

            if (id == guest.getId()) {
                raf.seek(recordStart); // Regresamos al inicio del registro

                raf.writeInt(guest.getId());
                raf.write(toBytes(guest.getFirstName(), NAME_SIZE));
                raf.write(toBytes(guest.getLastName(), NAME_SIZE));
                raf.write(toBytes(guest.getGender(), GENDER_SIZE));
                raf.write(toBytes(guest.getBirthDate().format(dtf), BIRTH_DATE_SIZE));

                if (guest.getGuestRoom() != null)
                    raf.writeInt(guest.getGuestRoom().getRoomNumber());
                else
                    raf.writeInt(-1);

                if (guest.getStayPeriod() != null) {
                    raf.write(toBytes(guest.getStayPeriod().getCheckInDate().format(dtf), CHECKIN_DATE_SIZE));
                    raf.write(toBytes(guest.getStayPeriod().getCheckOutDate().format(dtf), CHECKOUT_DATE_SIZE));
                } else {
                    raf.write(toBytes("null", CHECKIN_DATE_SIZE));
                    raf.write(toBytes("null", CHECKOUT_DATE_SIZE));
                }

                raf.writeBoolean(guest.isCheckedIn());
                raf.writeBoolean(guest.isCheckedOut());

                return true; // Éxito
            } else {
                // Saltamos el resto del registro
                raf.seek(recordStart + RECORD_SIZE);
            }
        }

        return false; // No se encontró
    }


    public void delete(int idToDelete) throws IOException {
        List<Guest> guestsToKeep = new ArrayList<>();
        raf.seek(0);

        // Cargar todos los huéspedes, excepto el que tiene el ID a eliminar
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

            if (id != idToDelete) {
                Guest guest = new Guest(firstName, lastName, gender, id, birthDateStr);
                guest.setCheckedIn(checkedIn);
                guest.setCheckedOut(checkedOut);
                guestsToKeep.add(guest);
            }
        }

        // Truncar el archivo
        raf.setLength(0);
        raf.seek(0);

        // Reinsertar huéspedes
        for (Guest guest : guestsToKeep) {
            raf.writeInt(guest.getId());
            raf.write(toBytes(guest.getFirstName(), NAME_SIZE));
            raf.write(toBytes(guest.getLastName(), NAME_SIZE));
            raf.write(toBytes(guest.getGender(), GENDER_SIZE));
            raf.write(toBytes(guest.getBirthDate().toString(), BIRTH_DATE_SIZE));
            if (guest.getGuestRoom() != null)
                raf.writeInt(guest.getGuestRoom().getRoomNumber());
            else
                raf.writeInt(-1);
            if (guest.getStayPeriod() != null) {
                raf.write(toBytes(guest.getStayPeriod().getCheckInDate().toString(), CHECKIN_DATE_SIZE));
                raf.write(toBytes(guest.getStayPeriod().getCheckOutDate().toString(), CHECKOUT_DATE_SIZE));
            } else {
                raf.write(toBytes("null", CHECKIN_DATE_SIZE));
                raf.write(toBytes("null", CHECKOUT_DATE_SIZE));
            }
            raf.writeBoolean(guest.isCheckedIn());
            raf.writeBoolean(guest.isCheckedOut());
        }
    }

    public void close() throws IOException {
        if (raf != null) raf.close();
    }
}
