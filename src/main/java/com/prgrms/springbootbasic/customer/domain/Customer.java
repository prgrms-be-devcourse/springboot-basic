package com.prgrms.springbootbasic.customer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private static final int NAME_LENGTH_MAX = 50;
    private static final int NAME_LENGTH_MIN = 1;
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    private final UUID id;
    private final LocalDateTime createdAt;
    private String name;

    public Customer(UUID id, String name) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public Customer(UUID id, LocalDateTime createdAt, String name) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    private void validateName(String name){
        if (name.length() < NAME_LENGTH_MIN || name.length() > NAME_LENGTH_MAX) {
            logger.warn("AmountOutOfBoundException occurred when creating new Customer. Customer name out of boundary.");
            throw new IllegalArgumentException("IllegalArgumentException occurred when creating new Customer. Customer name out of boundary" + NAME_LENGTH_MIN + " ~ " + NAME_LENGTH_MAX);
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void update(String name) {
        validateName(name);
        this.name = name;
    }
}
