package com.tangerine.voucher_system.application.customer.controller;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.model.Name;

import java.text.MessageFormat;
import java.util.UUID;

public record CustomerDto(
        UUID customerId,
        Name name,
        boolean isBlack
) {
    public static CustomerDto of(Customer entity) {
        return new CustomerDto(
                entity.customerId(),
                entity.name(),
                entity.isBlack()
        );
    }

    public Customer to() {
        return new Customer(customerId(), name(), isBlack());
    }

    public String toString() {
        return MessageFormat.format(
                "Customer(id: {0}, name: {1}, isBlack: {2})",
                customerId, name.getValue(), isBlack
        );
    }
}
