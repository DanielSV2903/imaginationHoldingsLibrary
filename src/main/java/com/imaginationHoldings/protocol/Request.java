package com.imaginationHoldings.protocol;

    import java.io.Serializable;

    public class Request implements Serializable {
        private String command;
        private String[] parameters;
        private Object data;

        public Request(String command, String... parameters) {
            this.command = command;
            this.parameters = parameters;
        }
        public Request(String command, Object data, String... parameters) {
            this.command = command;
            this.parameters = parameters;
            this.data=data;
        }

        public String getCommand() { return command; }
        public Object getData() { return data; }
        public String[] getParameters() { return parameters; }
    }

