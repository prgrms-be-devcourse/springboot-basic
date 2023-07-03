package com.programmers.voucher.domain.customer.dto.request;

public class CustomerCreateRequest {
    private final String email;
    private final String name;

    public CustomerCreateRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
