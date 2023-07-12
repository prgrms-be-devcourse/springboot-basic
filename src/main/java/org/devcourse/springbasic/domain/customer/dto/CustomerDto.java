package org.devcourse.springbasic.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {

    @AllArgsConstructor
    @Getter
    public static class SaveRequestDto {
        private final UUID customerId;
        private final String name;
        private final String email;
        private final LocalDateTime createdAt;
    }

    @AllArgsConstructor
    @Getter
    public static class LoginRequestDto {
        private final LocalDateTime lastLoginAt;
    }

    @AllArgsConstructor
    @Getter
    public static class UpdateRequestDto {
        private final UUID customerId;
        private final String name;
        private final String email;
    }

    @AllArgsConstructor
    @Getter
    public static class ResponseDto {
        private final String name;
        private final String email;
        private final LocalDateTime lastLoginAt;
        private final LocalDateTime createdAt;

    }
}
