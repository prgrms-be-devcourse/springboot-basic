package org.prgrms.kdtspringdemo.customer;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    @NonNull
    private final UUID customerId;
    @NonNull
    private final String name;
    @NonNull
    private final LocalDate birth;
    @NonNull
    private final String email;
    @NonNull
    private final LocalDateTime createdAt;
    @Nullable
    private LocalDateTime lastLoginAt;
    @NonNull
    private boolean blackList;


    public Customer(UUID customerId, String name, LocalDate birth, String email, LocalDateTime createdAt, boolean blackList) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.birth = birth;
        this.blackList = blackList;
    }

    public Customer(UUID customerId, String name, LocalDate birth, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.birth = birth;
        this.blackList = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
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

    public boolean isBlackList() {
        return blackList;
    }

}
