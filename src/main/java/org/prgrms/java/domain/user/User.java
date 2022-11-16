package org.prgrms.java.domain.user;

import java.util.UUID;

public class User {
    private final UUID userId;
    private String name;
    private String email;
    private boolean isBlocked;

    public User(UUID userId, String name, String email, boolean isBlocked) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.isBlocked = isBlocked;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", userId, name, email);
    }
}
