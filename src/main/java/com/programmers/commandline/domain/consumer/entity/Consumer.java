package com.programmers.commandline.domain.consumer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Consumer {

    private final UUID consumerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Consumer(UUID consumerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.consumerId = consumerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public Consumer(UUID consumerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.consumerId = consumerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public UUID getConsumerId() {
        return consumerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}