package com.devcourse.user;

import java.util.UUID;

import static com.devcourse.global.common.Constant.DELIMITER;

public class User {
    private final UUID id;
    private final String name;

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String toStringResponse() {
        return id + DELIMITER + name;
    }
}
