package org.weekly.weekly.customer.dto.request;

import org.weekly.weekly.ui.exception.InputValidator;

public class CustomerUpdateRequest {
    private String email;

    private CustomerUpdateRequest(String email) {
        this.email = email;
    }

    public static CustomerUpdateRequest of(String email) {
        InputValidator.isEmpty(email);
        return new CustomerUpdateRequest(email);
    }

    public String email() {
        return email;
    }
}
