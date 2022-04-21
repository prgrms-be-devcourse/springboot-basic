package org.prgms.customer;

import org.prgms.validator.Validators;

import java.util.UUID;

public record Customer(UUID customerId, String name, String email) {
    public Customer {
        Validators.notNullAndEmptyCheck(customerId, name, email);
    }
}
