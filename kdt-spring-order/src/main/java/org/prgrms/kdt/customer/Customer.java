package org.prgrms.kdt.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void setName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
        this.name = name;
    }

    public void login(){
        this.lastLoginAt = LocalDateTime.now();
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeName(String s) {
        name = s;
    }
}
