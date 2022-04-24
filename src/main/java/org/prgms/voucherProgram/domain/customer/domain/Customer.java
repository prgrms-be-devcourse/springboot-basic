package org.prgms.voucherProgram.domain.customer.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final LocalDateTime createdTime;
    private Name name;
    private Email email;
    private LocalDateTime lastLoginTime;

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

    public void changeInformation(String name, String email, LocalDateTime lastLoginTime) {
        this.name = new Name(name);
        this.email = new Email(email);
        this.lastLoginTime = lastLoginTime;
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

    @Override
    public String toString() {
        return "Customer{" +
            "customerId=" + customerId +
            ", createdTime=" + createdTime +
            ", name=" + name +
            ", email=" + email +
            ", lastLoginTime=" + lastLoginTime +
            '}';
    }
}
