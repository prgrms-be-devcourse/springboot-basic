package com.prgrms.vouchermanager.dto.customer;

import lombok.Builder;

import java.util.UUID;


public class CustomerResponse {
    public record CustomerDetailResponse(UUID customerId, String name, int yearOfBirth, boolean isBlacklist) {
        @Builder
        public CustomerDetailResponse {
        }
    }
}
