package com.programmers.application.domain.customer;

import com.programmers.application.util.Patterns;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final LocalDateTime createdAt;

    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(UUID customerId, String name, String email) {
        validateCustomerId(customerId);
        validateName(name);
        validateEmail(email);

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    private static void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("고객 이름이 비어있습니다.");
        }
    }

    private static void validateEmail(String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new IllegalArgumentException("고객 이메일이 비어있습니다.");
        }
        if (!Patterns.EMAIL_PATTERN.matcher(email).matches()) {
            String errorMessage = String.format("옳바른 이메일 형식을 입력해주세요. 입력값: %s", email);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static void validateCustomerId(UUID customerId) {
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("고객 아이디가 비어있습니다.");
        }
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public String getName() {
        return name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
