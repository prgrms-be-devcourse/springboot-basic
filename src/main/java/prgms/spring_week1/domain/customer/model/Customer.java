package prgms.spring_week1.domain.customer.model;

import prgms.spring_week1.domain.customer.model.embeddedType.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private Email email;
    
    protected final LocalDateTime createdAt = LocalDateTime.now();
    protected LocalDateTime updatedAt;

    public Customer(String name, Email email) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email.getAddress();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
