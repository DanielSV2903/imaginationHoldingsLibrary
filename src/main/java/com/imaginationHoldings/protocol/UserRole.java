package com.imaginationHoldings.protocol;

public enum UserRole {
    ADMIN("Admin",3),
    CLIENT("Client",1),
    FRONTDESK("Front Desk",2);

    private String name;
    private int priority;
    private UserRole(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "User Role{" + name +","+ priority + '}';
    }
}
