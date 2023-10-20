package org.prgrms.prgrmsspring.entity.user;

import java.util.UUID;

public class User {
    private final UUID userId;
    private final String name;
    private final Boolean isBlack;

    public User(UUID userId, String name, Boolean isBlack) {
        this.userId = userId;
        this.name = name;
        this.isBlack = isBlack;
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
