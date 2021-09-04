package org.programmers.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private String name;
    private final Gender gender;
    private final String email;
    private final LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private CustomerType customerType;

    public Customer(UUID customerId, String name, Gender gender, String email, LocalDateTime createdAt) {
        validateName(name);

        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.createdAt = createdAt;
        this.customerType = CustomerType.GENERAL;
    }

    public Customer(UUID customerId, String name, Gender gender, String email, LocalDateTime createdAt, CustomerType customerType) {
        validateName(name);

        this.customerId = customerId;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.createdAt = createdAt;
        this.customerType = customerType;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            new RuntimeException("Name should not be blank");
        }
    }

    private void changeName(String name) {
        validateName(name);

        this.name = name;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void changeCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                ", customerType=" + customerType +
                '}';
    }

}
