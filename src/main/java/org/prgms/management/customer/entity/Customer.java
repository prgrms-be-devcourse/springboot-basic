package org.prgms.management.customer.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 0;

    private final UUID customerId;
    private String name;
    private final LocalDateTime createdAt;

    private Customer(UUID customerId, String name, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Customer getCustomer(UUID customerId, String name, LocalDateTime createdAt) {
        if (!validate(name)) {
            return null;
        }
        return new Customer(customerId, name, createdAt);
    }

    private static boolean validate(String name) {
        if (name.length() == MIN_LENGTH) {
            logger.error("이름은 {}글자를 넘어야 합니다.", MIN_LENGTH);
            return false;
        }

        if (name.length() == MAX_LENGTH) {
            logger.error("이름은 {}글자 이상이면 안됩니다.", MAX_LENGTH);
            return false;
        }

        return true;
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
}
