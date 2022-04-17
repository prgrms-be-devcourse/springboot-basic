package org.prgms.voucherProgram.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final Name name;
    private final Email email;
    private final LocalDateTime createdTime;
    private final LocalDateTime lastLoginTime;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdTime) {
        this(customerId, name, email, createdTime, null);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdTime,
        LocalDateTime lastLoginTime) {
        this.customerId = customerId;
        this.name = new Name(name);
        this.email = new Email(email);
        this.createdTime = createdTime;
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isNotSameCustomer(Customer customer) {
        return !this.customerId.equals(customer.customerId);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getName() {
        return name.getName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }
}
