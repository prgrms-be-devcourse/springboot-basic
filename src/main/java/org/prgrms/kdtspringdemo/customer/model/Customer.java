package org.prgrms.kdtspringdemo.customer.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String nickname;

    public Customer(String nickname) {
        this.customerId = UUID.randomUUID();
        this.nickname = nickname;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getNickname() {
        return nickname;
    }
}
