package org.prgrms.application.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private final String createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, String createdAt) {
        validate(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String createdAt) {validate(name);
        validate(name);
        this.customerId = customerId;
        this.createdAt = createdAt;
    }

    public void changeName(String name){
        validate(name);
        this.name = name;
    }

    public void login(){ //로그인에 따른 로그인 시간 변경
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

    public String getCreatedAt() {
        return createdAt;
    }

    private void validate(String name) {
        if(name.isBlank()){
            throw new RuntimeException("name should not be blank");
        }
    }
}
