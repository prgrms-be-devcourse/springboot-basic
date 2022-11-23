package com.programmers.commandline.domain.consumer.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Consumer {

    private final UUID consumerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Consumer(UUID consumerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.consumerId = consumerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Consumer(UUID consumerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.consumerId = consumerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }
    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
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

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}