package com.prgmrs.voucher.model;

import java.util.UUID;

public class User {
    private final UUID userId;
    private final String username;

    public User(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
