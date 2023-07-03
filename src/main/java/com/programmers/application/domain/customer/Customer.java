package com.programmers.application.domain.customer;

import com.programmers.application.util.Patterns;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    private Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public static Customer of(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("고객 아이디가 비어있습니다.");
        }
        if (!Patterns.EMAIL_PATTERN.matcher(email).matches()) {
            String errorMessage = String.format("옳바른 이메일 형식을 입력해주세요. 입력값: %s", email);
            throw new IllegalArgumentException(errorMessage);
        }
        return new Customer(customerId, name, email, lastLoginAt, createdAt);
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("고객 이름이 비었습니다.");
        }
        this.name = name;
    }
}
