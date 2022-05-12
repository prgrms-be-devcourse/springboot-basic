package com.voucher.vouchermanagement.controller.api.v1.request;

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
