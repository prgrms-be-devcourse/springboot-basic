package org.prgms.voucherProgram.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final LocalDateTime createdDateTime;
    private Name name;
    private Email email;
    private LocalDateTime updatedDateTime;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdDateTime) {
        this(customerId, name, email, createdDateTime, null);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime) {
        this.customerId = customerId;
        this.name = new Name(name);
        this.email = new Email(email);
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public boolean isNotSameCustomer(Customer customer) {
        return !this.customerId.equals(customer.customerId);
    }

    public void changeInformation(String name, String email, LocalDateTime updatedDateTime) {
        this.name = new Name(name);
        this.email = new Email(email);
        this.updatedDateTime = updatedDateTime;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getName() {
        return name.getName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", createdDateTime=" + createdDateTime +
            ", name=" + name +
            ", email=" + email +
            ", lastLoginTime=" + updatedDateTime +
            '}';
    }
}
