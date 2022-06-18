package org.devcourse.voucher.application.customer.controller.dto;

import org.devcourse.voucher.application.customer.model.Customer;

import java.util.UUID;

public class CustomerResponse {

    private final UUID customerId;

    private final String name;

    private final String email;

    public CustomerResponse(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail().getAddress()
        );
    }
}
