package org.prgrms.part1.engine.domain;

import com.opencsv.bean.CsvBindByName;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    @CsvBindByName
    private final UUID customerId;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private final String email;
    @CsvBindByName
    private LocalDateTime lastLoginAt;
    @CsvBindByName
    private final LocalDateTime createdAt;
    private Boolean isBlack;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.isBlack = false;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.isBlack = false;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank.");
        }
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void toBlack() {
        this.isBlack = true;
    }

    public void revokeBlack() {
        this.isBlack = false;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsBlack() {return isBlack;}

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
