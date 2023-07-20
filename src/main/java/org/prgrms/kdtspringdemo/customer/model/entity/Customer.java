package org.prgrms.kdtspringdemo.customer.model.entity;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private String nickname;

    public Customer(String nickname) {
        this.id = UUID.randomUUID();
        this.nickname = nickname;
    }

    public Customer(UUID id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }
}
