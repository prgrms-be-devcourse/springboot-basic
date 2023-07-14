package com.programmers.voucher.domain.customer.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID id;
    private String nickname;

    @Builder
    private Customer(UUID id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static Customer create(String nickname) {
        return new Customer(UUID.randomUUID(), nickname);
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }
}
