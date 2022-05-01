package org.prgms.management.model.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;

    private final UUID customerId;
    private String name;
    private boolean isBlacklist;
    private final LocalDateTime createdAt;

    private Customer(UUID customerId, String name, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.createdAt = createdAt;
    }

    private Customer(UUID customerId, String name, boolean isBlacklist, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.isBlacklist = isBlacklist;
        this.createdAt = createdAt;
    }

    public static Customer getCustomer(UUID customerId, String name, LocalDateTime createdAt) {
        validate(name);
        return new Customer(customerId, name, createdAt);
    }

    public static Customer getCustomer(UUID customerId, String name, boolean isBlacklist, LocalDateTime createdAt) {
        validate(name);
        return new Customer(customerId, name, isBlacklist, createdAt);
    }

    private static void validate(String name) {
        if (name.length() == MIN_LENGTH) {
            throw new IllegalArgumentException(MessageFormat.format("이름은 {0}글자를 넘어야 합니다.", MIN_LENGTH));
        }

        if (name.length() == MAX_LENGTH) {
            throw new IllegalArgumentException(MessageFormat.format("이름은 {0}글자 이상이면 안됩니다.", MAX_LENGTH));
        }
    }

    public void changeName(String name) {
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isBlacklist() {
        return isBlacklist;
    }
}
