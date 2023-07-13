package com.programmers.voucher.domain.customer.entity;

import java.util.UUID;

public class Customer {
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
