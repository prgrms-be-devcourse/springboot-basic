package com.programmers.commandline.domain.consumer.entity;

import com.programmers.commandline.domain.voucher.entity.Voucher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Consumer {
    private String id;
    private String name;
    private String email;
    private List<Voucher> vouchers = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now().withNano(0);
    private LocalDateTime lastLoginAt = LocalDateTime.now().withNano(0);

    public Consumer(UUID id, String name, String email) {
        validateName(name);
        this.id = id.toString();
        this.name = name;
        this.email = email;
    }

    public Consumer(UUID id, String name, String email, LocalDateTime createdAt, LocalDateTime localDateTime) {
        validateName(name);
        this.id = id.toString();
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = localDateTime;
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

    public LocalDateTime getLastLoginAt() {
        return this.lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void login(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s Email: %s CreatedAt: %s LastLoginAt: %s"
                , this.id, this.name, this.email, this.createdAt.toString(), this.lastLoginAt.toString());
    }
}