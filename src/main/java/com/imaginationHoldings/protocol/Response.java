package com.imaginationHoldings.protocol;

import java.io.Serializable;

public class Response implements Serializable {
    public static final String HOTEL_REGISTERED = "HOTEL_REGISTERED";
    public static final String HOTEL_UPDATED = "HOTEL_UPDATED";
    public static final String HOTEL_DELETED = "HOTEL_DELETED";
    public static final String HOTEL_NOT_FOUND = "HOTEL_NOT_FOUND";

    public static final String ROOM_REGISTERED = "ROOM_REGISTERED";
    public static final String ROOM_UPDATED = "ROOM_UPDATED";
    public static final String ROOM_DELETED = "ROOM_DELETED";
    public static final String ROOM_NOT_FOUND = "ROOM_NOT_FOUND";

    public static final String GUEST_REGISTERED = "GUEST_REGISTERED";
    public static final String GUEST_NOT_FOUND = "GUEST_NOT_FOUND";
    public static final String GUEST_UPDATED = "GUEST_UPDATED";
    public static final String GUEST_DELETED = "GUEST_DELETED";
    public static final String GUEST_ALREADY_EXISTS = "GUEST_ALREADY_EXISTS";

    public static final String BOOKING_DONE = "BOOKING_DONE";
    public static final String BOOKING_NOT_FOUND = "BOOKING_NOT_FOUND";
    public static final String BOOKING_DELETED = "BOOKING_DELETED";

    public static final String UNKNOWN_COMMAND = "UNKNOWN_COMMAND";
    public static final String SERVER_ERROR = "SERVER_ERROR";


    private String command;
    private String[] parameters;
    Object data;
    public Response(String command, String... parameters) {
        this.command = command;
        this.parameters = parameters;
        this.data = null;
    }
    public Response(String command, Object data, String... parameters) {
        this.command = command;
        this.data = data;
        this.parameters = parameters;

    }

    public String getCommand() { return command; }
    public String[] getParameters() { return parameters; }
    public Object getData() { return data; }
}
