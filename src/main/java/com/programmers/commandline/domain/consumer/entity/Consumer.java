package com.programmers.commandline.domain.consumer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Consumer {
    private String id;
    private String name;
    private String email;
    private String createdAt;
    private String lastLoginAt;

    public Consumer(UUID id, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.id = id.toString();
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.toString();
        this.lastLoginAt = null;
    }

    public Consumer(UUID id, String name, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        validateName(name);
        this.id = id.toString();
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.toString();
        this.lastLoginAt = (lastLoginAt != null) ? lastLoginAt.toString() : null;

    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastLoginAt() {
        return this.lastLoginAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Email: %s CreatedAt: %s LastLoginAt: %s"
                , this.id, this.name, this.email, this.createdAt, this.lastLoginAt);
    }

}