package com.programmers.vouchermanagement.customer.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Customer not found.");
    }

    public CustomerNotFoundException(UUID wrongId) {
        super("Customer not found. Input : " + wrongId);
    }
}
