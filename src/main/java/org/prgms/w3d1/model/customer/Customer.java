package org.prgms.w3d1.model.customer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = convertLocalDateForWindows(createdAt);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = convertLocalDateForWindows(lastLoginAt);
        this.createdAt = convertLocalDateForWindows(createdAt);
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

    public void changeName(String name){
        validateName(name);
        this.name = name;
    }


    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", lastLoginAt=" + lastLoginAt +
            ", createdAt=" + createdAt +
            '}';
    }
    private void validateName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
    }

    private LocalDateTime convertLocalDateForWindows(LocalDateTime localDateTime){
        if(localDateTime == null){
            return null;
        }
        return localDateTime.truncatedTo(ChronoUnit.MILLIS);
    }
}
