package com.programmers.commandline.domain.consumer.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Consumer {

    private String consumerId;
    private String name;
    private String email;
    private String createdAt;
    private String lastLoginAt;

    public Consumer(UUID consumerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.consumerId = consumerId.toString();
        this.name = name;
        this.email = email;
        this.createdAt = createdAt.toString();
    }

    public Consumer(UUID consumerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.consumerId = consumerId.toString();
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt.toString();
        this.createdAt = createdAt != null ? createdAt.toString() : null;

    }
    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return LocalDateTime.parse(lastLoginAt);
    }

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(createdAt);
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}