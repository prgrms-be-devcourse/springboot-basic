package com.blessing333.springbasic.customer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public static Customer createNewCustomer(String name, String email) throws IllegalArgumentException {
        validateName(name);
        Customer customer = new Customer();
        customer.name = name;
        customer.customerId = UUID.randomUUID();
        customer.email = email;
        customer.createdAt = LocalDateTime.now();
        return customer;
    }

    private static void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
    }


    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }
}
