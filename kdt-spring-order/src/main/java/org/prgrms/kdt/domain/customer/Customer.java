package org.prgrms.kdt.domain.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private final boolean isBlacklisted;

    public Customer(UUID id, String name, boolean isBlacklisted) {
        this.id = id;
        this.name = name;
        this.isBlacklisted = isBlacklisted;
        this.createdAt = LocalDateTime.now();
        this.email = "";
    }

    public Customer(UUID id, String name, String email, LocalDateTime createdAt, boolean isBlacklisted) {
        validName(name);

        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.isBlacklisted = isBlacklisted;
    }

    public Customer(UUID id, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt, boolean isBlacklisted) {
        validName(name);

        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.isBlacklisted = isBlacklisted;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
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

    public void changeName(String name){
        validName(name);

        this.name = name;
    }

    private void validName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void login(){
        this.lastLoginAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String formatString = "id : {0}, name : {1}";

        if(isBlacklisted){
            return MessageFormat.format("[BlackList] " + formatString, id, name);
        }

        return MessageFormat.format(formatString, id, name);
    }

}
