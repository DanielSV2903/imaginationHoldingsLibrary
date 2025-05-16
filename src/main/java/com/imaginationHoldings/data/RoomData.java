package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Hotel;
import com.imaginationHoldings.domain.Room;
import com.imaginationHoldings.domain.RoomType;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RoomData {
    private RandomAccessFile raf;
    private static final int ROOM_NUMBER_SIZE = 4;       // int
    private static final int ROOM_TYPE_SIZE = 20;        // String (enum name)
    private static final int HOTEL_ID_SIZE = 4;          // int (Hotel ID)
    private static final int LOCATION_SIZE = 30;         // String
    private static final int DESCRIPTION_SIZE = 50;      // String
    private static final int CAPACITY_SIZE = 4;          // int
    private static final int AVAILABILITY_SIZE = 1;      // boolean
    private static final int RECORD_SIZE = ROOM_NUMBER_SIZE + ROOM_TYPE_SIZE + HOTEL_ID_SIZE +
            LOCATION_SIZE + DESCRIPTION_SIZE + CAPACITY_SIZE + AVAILABILITY_SIZE;

    public RoomData(File file) throws IOException {
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

    public void insert(Room room) throws IOException {
        raf.seek(raf.length());

        raf.writeInt(room.getRoomNumber());
        raf.write(toBytes(room.getRoomType().name(), ROOM_TYPE_SIZE)); // usamos name()
        raf.writeInt(room.getHotel().getId()); // asume que Hotel tiene getId()
        raf.write(toBytes(room.getLocation(), LOCATION_SIZE));
        raf.write(toBytes(room.getDescription(), DESCRIPTION_SIZE));
        raf.writeInt(room.getCapacity());
        raf.writeBoolean(room.isAvailable());
    }

    public List<Room> findAll() throws IOException {
        List<Room> rooms = new ArrayList<>();
        raf.seek(0);

        while (raf.getFilePointer() < raf.length()) {
            int roomNumber = raf.readInt();
            String typeName = readString(ROOM_TYPE_SIZE);
            RoomType roomType = RoomType.valueOf(typeName);

            int hotelId = raf.readInt();
            String location = readString(LOCATION_SIZE);
            String description = readString(DESCRIPTION_SIZE);
            int capacity = raf.readInt();
            boolean availability = raf.readBoolean();
            //TODO llamar a hotel data para crear el hotel
            Hotel hotel = new Hotel(hotelId, "", "");
            Room room = new Room(roomNumber, roomType, hotel, location);
            room.setDescription(description);
            room.setCapacity(capacity);
            room.setAvailability(availability);

            rooms.add(room);
        }

        return rooms;
    }

    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }
}


