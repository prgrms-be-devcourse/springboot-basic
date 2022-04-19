package com.blessing333.springbasic.customer.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "customerId")
public class Customer {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public static Customer createNewCustomer(UUID id,String name,String email,LocalDateTime createdAt) throws IllegalArgumentException {
        validateName(name);
        Customer customer = new Customer();
        customer.customerId = id;
        customer.name = name;
        customer.email = email;
        customer.createdAt = createdAt;
        return customer;
    }

    public static Customer createNewCustomerWithAllArgument(UUID id,String name,String email,LocalDateTime createdAt,LocalDateTime lastLoginAt) throws IllegalArgumentException {
        validateName(name);
        Customer customer = new Customer();
        customer.customerId = id;
        customer.name = name;
        customer.email = email;
        customer.createdAt = createdAt;
        customer.lastLoginAt = lastLoginAt;
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

    public void changeLastLoginDate(LocalDateTime time) {
        this.lastLoginAt = time;
    }

    public void changeEmail(String email){
        this.email = email;
    }
}
