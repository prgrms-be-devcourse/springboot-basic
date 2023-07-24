package org.prgrms.kdtspringdemo.customer.model.dto;

import java.util.UUID;

public class CustomerResponse {
    private final UUID id;
    private final String nickname;

    public CustomerResponse(UUID id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
