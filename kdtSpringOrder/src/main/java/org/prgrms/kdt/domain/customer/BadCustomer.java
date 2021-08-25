package org.prgrms.kdt.domain.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class BadCustomer implements Customer{
    private final UUID customerId;
    private final String name;
    private final int age;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    public BadCustomer(UUID customerId, String name, int age) {
        this.customerId = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},\" {1}\",\" {2}", customerId, name, age);
    }

}
