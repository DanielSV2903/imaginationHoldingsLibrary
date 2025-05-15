package com.imaginationHoldings.domain;

public enum RoomType {
    SINGLE("Single Room", 1),
    DOUBLE("Double Room", 2),
    TRIPLE("Triple Room", 3),
    SUITE("Luxury Suite", 4),
    FAMILY("Family Room", 5);

    private final String description;
    private final int capacity;

    RoomType(String description, int capacity) {
        this.description = description;
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return formattedName();
    }

    public String formattedName() {
        return description + " (Capacity: " + capacity + ")";
    }
}
