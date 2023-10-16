package org.prgrms.prgrmsspring.entity.user;

import java.util.UUID;

public class User {
    private final UUID userId;
    private final String name;

    public User(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "userId=" + userId + ", name=" + name;
    }
}
