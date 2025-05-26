package com.imaginationHoldings.data;

import com.imaginationHoldings.domain.Hotel;
import com.imaginationHoldings.domain.Room;
import com.imaginationHoldings.domain.RoomType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HotelData{
    private RandomAccessFile raf;
    private String filePath;
    private static final int HOTEL_ID_SIZE = 4;       // int
    private static final int LOCATION_SIZE = 30;         // String
    private static final int NAME_SIZE = 50;      // String
    private static final int RECORD_SIZE =HOTEL_ID_SIZE +NAME_SIZE+ LOCATION_SIZE;

    public HotelData(File file) throws IOException {
        this.raf = new RandomAccessFile(file, "rw");
    }
    public HotelData(String filePath) throws IOException {
        this.filePath=filePath;
        this.raf = new RandomAccessFile(filePath, "rw");
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

    public void insert(Hotel hotel) throws IOException {
        raf.seek(raf.length());
        raf.writeInt(hotel.getId());
        raf.write(toBytes(hotel.getName(),NAME_SIZE));
        raf.write(toBytes(hotel.getAddress(), LOCATION_SIZE));
    }

    public List<Hotel> findAll() throws IOException {
        List<Hotel> hotels = new ArrayList<>();
        raf.seek(0);

        while (raf.getFilePointer() < raf.length()) {

            int hotelId = raf.readInt();
            String name = readString(NAME_SIZE);
            String location = readString(LOCATION_SIZE);

            Hotel hotel = new Hotel(hotelId, name, location);

            hotels.add(hotel);
        }

        return hotels;
    }

    public Hotel findById(int id) throws IOException {
        List<Hotel> hotels = findAll();
        for (Hotel hotel : hotels) {
            if (hotel.getId() == id) {
                return hotel;
            }
        }

        return null;
    }
    public boolean update(Hotel hotel) throws IOException {
        raf.seek(0);

        while (raf.getFilePointer() < raf.length()) {
            long recordStart = raf.getFilePointer();
            int hotelID = raf.readInt();

            if (hotelID == hotel.getId()) {
                // Encontramos el registro a modificar
                raf.seek(recordStart); // volvemos a inicio de registro

                // Escribimos los datos actualizados
                raf.writeInt(hotel.getId());
                raf.write(toBytes(hotel.getName(), NAME_SIZE));
                raf.write(toBytes(hotel.getAddress(), LOCATION_SIZE));
                return true; // actualización exitosa
            } else {
                // Saltamos el resto del registro ya que sólo leímos el int del roomNumber
                raf.skipBytes(RECORD_SIZE - HOTEL_ID_SIZE);
            }
        }
        return false; // habitación no encontrada
    }
    public void delete(int idToDelete) throws IOException {
        List<Hotel> hotelsToKeep = new ArrayList<>();

        // Leer todos los hoteles y excluir el que se quiere eliminar
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            int hotelId = raf.readInt();
            String name = readString(NAME_SIZE);
            String location = readString(LOCATION_SIZE);

            if (hotelId != idToDelete) {
                hotelsToKeep.add(new Hotel(hotelId, name, location));
            }
        }

        // Truncar archivo y reescribir los hoteles restantes
        raf.setLength(0);
        raf.seek(0);
        for (Hotel h : hotelsToKeep) {
            raf.writeInt(h.getId());
            raf.write(toBytes(h.getName(), NAME_SIZE));
            raf.write(toBytes(h.getAddress(), LOCATION_SIZE));
        }
    }


    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }

}
