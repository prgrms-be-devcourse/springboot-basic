package org.prgrms.kdtspringdemo.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String email;
    protected String type;
    private LocalDateTime last_loginAt;
    private final LocalDateTime createdAt;


    private void validate(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public Customer(UUID customerId, String name, String email, String type, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, String type, LocalDateTime last_loginAt, LocalDateTime createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.type = type;
        this.last_loginAt = last_loginAt;
        this.createdAt = createdAt;
    }
    public void changeName(String name) {
        validate(name);
        this.name = name;
    }
    public void login() {
        this.last_loginAt = LocalDateTime.now();
    }
    public UUID getId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public LocalDateTime getLast_loginAt() {
        return last_loginAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public String getType() {
        return type;
    }
    public String toString() {
        return MessageFormat.format("id : {0}, name : {1}, {2}", customerId, name, this.getClass().getSimpleName());
    }
    public void setType(String type) {
        this.type = type;
    }
}
