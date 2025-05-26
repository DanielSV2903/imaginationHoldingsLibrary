package com.imaginationHoldings.protocol;

import java.io.Serializable;

public class Response implements Serializable {
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
