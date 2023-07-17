package com.prgrms.model.customer;

package com.prgrms.model.customer;

import java.time.LocalDateTime;

public class Customer {

    private final int customerId;
    private final String email;
    private final LocalDateTime createdAt;
    private Name name;
    private LocalDateTime lastLoginAt;

    public Customer(int customerId, Name name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(int customerId, Name name, String email, LocalDateTime lastLoginAt,
            LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void login(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Name getName() {
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
}
