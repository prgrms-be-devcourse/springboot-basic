package com.programmers.voucher.entity.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Customer {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private final UUID id;
    private String nickname;

    public Customer(UUID id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static Customer create(String nickname) {
        return new Customer(UUID.randomUUID(), nickname);
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
