package org.prgrms.kdtspringdemo.customer.model.dto;

import java.util.UUID;

public class CustomerResponse {
    private final UUID customerId;
    private final String nickname;

    public CustomerResponse(UUID customerId, String nickname) {
        this.customerId = customerId;
        this.nickname = nickname;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getNickname() {
        return nickname;
    }

    public static CustomerResponse toDto(UUID customerId, String nickname) {
        return new CustomerResponse(customerId, nickname);
    }
}
