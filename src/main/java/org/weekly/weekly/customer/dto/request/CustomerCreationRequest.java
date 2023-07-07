package org.weekly.weekly.customer.dto.request;

import org.weekly.weekly.ui.exception.InputValidator;

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
}
