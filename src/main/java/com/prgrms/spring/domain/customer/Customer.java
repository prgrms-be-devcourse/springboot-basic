package com.prgrms.spring.domain.customer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;

    public static Customer newInstance(UUID customerId, String name, String email, LocalDateTime createdAt) {
        return new Customer(customerId, name, email, createdAt);
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private static void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank.");
        }
    }
}
