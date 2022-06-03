package org.devcourse.voucher.application.customer.controller.dto;

public class CreateCustomerRequest {
    private final String name;

    private final String email;

    public CreateCustomerRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
