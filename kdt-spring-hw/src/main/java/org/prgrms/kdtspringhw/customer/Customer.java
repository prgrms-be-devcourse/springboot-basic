package org.prgrms.kdtspringhw.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createAt;


    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = createAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createAt = createAt;
    }

    public void changeName(String name){
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
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
        return createAt;
    }
}
