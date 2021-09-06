package org.prgrms.orderApp.customer;

import org.prgrms.orderApp.util.library.Validate;

import java.time.LocalDateTime;
import java.util.UUID;

// Entity
public class Customer implements CustomerModel{
    private final UUID customerId;
    private final String email;
    private String name;
    private LocalDateTime lastLoginAt, createdAt;

    public Customer(UUID customerId, String email){
        this.customerId = customerId;
        this.email = email;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt){
        Validate.checkBlankForString("Name",name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt){
        Validate.checkBlankForString("Name",name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }


    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeName(String name){
        Validate.checkBlankForString("Name", name);
        this.name = name;
    }

    public void login(){
        this.lastLoginAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerId=" + customerId +
                ", name=" + name +
                ", email=" + email +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                "}";
    }
}
