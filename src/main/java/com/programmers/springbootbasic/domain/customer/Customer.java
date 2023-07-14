package com.programmers.springbootbasic.domain.customer;

import com.programmers.springbootbasic.common.util.Validator;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;

    private String name;

    public Customer(UUID customerId, String email, String name) {
        checkInvalidValue();
        this.customerId = customerId;
        this.email = email;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    private void checkInvalidValue() {
        Validator.checkNullUuid(customerId);
        Validator.checkNullOrBlank(email);
        Validator.checkNullOrBlank(name);
        Validator.checkNullOrWrongEmail(email);
    }
}
