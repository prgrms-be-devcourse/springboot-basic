package com.devcourse.user.domain;

import java.util.UUID;

public class User {
    public enum Status { NORMAL, BANNED }

    private final UUID id;
    private final String name;
    private Status status;

    public User(UUID id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
