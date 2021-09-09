package com.prgrms.devkdtorder.customer.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TestCustomerBuilder {
    private UUID customerId = UUID.randomUUID();
    private String name = "maeng";
    private String email = "maeng@gmail.com";
    private LocalDateTime lastLoginAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    private CustomerType customerType = CustomerType.WHITE;

    private TestCustomerBuilder() {
    }

    public TestCustomerBuilder customerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public TestCustomerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TestCustomerBuilder email(String email) {
        this.email = email;
        return this;
    }

    public TestCustomerBuilder lastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public TestCustomerBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TestCustomerBuilder customerType(CustomerType customerType) {
        this.customerType = customerType;
        return this;
    }

    public Customer build() {
        return new Customer(customerId, name, email, lastLoginAt, createdAt, customerType);
    }

    public static TestCustomerBuilder start() {
        return new TestCustomerBuilder();
    }

}
