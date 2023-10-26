package com.programmers.vouchermanagement.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;
    private boolean isBanned;

    public Customer(UUID id, String name, LocalDateTime createdAt, boolean isBanned) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.isBanned = isBanned;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void updateToBan() {
        this.isBanned = true;
    }

    public void updateToUnban() {
        this.isBanned = false;
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                "#######################" + System.lineSeparator() +
                "Customer Id:   " + id + System.lineSeparator() +
                "Customer Name: " + name + System.lineSeparator() +
                "Created At:    " + createdAt + System.lineSeparator() +
                "isBanned:      " + isBanned + System.lineSeparator();
    }

    public String joinInfo(String separator) {
        return String.join(separator, id.toString(), name, createdAt.toString(), String.valueOf(isBanned));
    }
}
