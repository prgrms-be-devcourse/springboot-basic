package org.prgrms.prgrmsspring.entity.user;

import java.util.UUID;

public class Customer {
    private final UUID userId;
    private final String name;
    private final Boolean isBlack;

    public Customer(UUID userId, String name, Boolean isBlack) {
        this.userId = userId;
        this.name = name;
        this.isBlack = isBlack;
    }

    public Customer(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
        this.isBlack = false;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Boolean checkIsBlack() {
        return isBlack;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", isBlack=" + isBlack +
                '}';
    }
}
