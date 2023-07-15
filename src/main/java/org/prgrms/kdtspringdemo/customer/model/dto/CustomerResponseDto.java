package org.prgrms.kdtspringdemo.customer.model.dto;

import java.util.UUID;

public class CustomerResponseDto {
    private final UUID customerId;
    private final String nickname;

    public CustomerResponseDto(UUID customerId, String nickname) {
        this.customerId = customerId;
        this.nickname = nickname;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getNickname() {
        return nickname;
    }

    public static CustomerResponseDto toDto(UUID customerId, String nickname) {
        return new CustomerResponseDto(customerId, nickname);
    }
}
