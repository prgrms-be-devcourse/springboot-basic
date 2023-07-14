package org.weekly.weekly.customer.dto.request;

import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.ui.exception.InputValidator;

import java.util.UUID;

public class CustomerCreationRequest {
    private String email;
    private String name;

    public CustomerCreationRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static CustomerCreationRequest of(String email, String name) {
        InputValidator.isEmpty(email);
        InputValidator.isEmpty(name);
        return new CustomerCreationRequest(email, name);
    }

    public Customer toCustomer() {
        return Customer.of(UUID.randomUUID(), name, email);
    }

    public String getEmail() {
        return email;
    }
}
