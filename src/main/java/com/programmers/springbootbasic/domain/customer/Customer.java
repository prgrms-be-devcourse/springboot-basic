package com.programmers.springbootbasic.domain.customer;

import com.programmers.springbootbasic.common.util.Validator;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;

    private String name;

    public Customer(UUID customerId, String email, String name) {
        checkInvalidValue(customerId, email, name);
        this.customerId = customerId;
        this.email = email;
        this.name = name;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    private void checkInvalidValue(UUID customerId, String email, String name) {
        Validator.checkNullUuid(customerId);
        Validator.checkNullOrBlank(name);
        Validator.checkNullOrWrongEmail(email);
    }
}
