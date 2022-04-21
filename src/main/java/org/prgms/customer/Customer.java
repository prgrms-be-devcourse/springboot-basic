package org.prgms.customer;

import org.prgms.validator.DomainValidators;

import java.util.UUID;

public record Customer(UUID customerId, String name, String email) {
    public Customer {
        DomainValidators.notNullAndEmptyCheck(customerId, name, email);
    }
}
