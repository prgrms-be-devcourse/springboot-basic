package com.voucher.vouchermanagement.domain.customer.dto;

public class CustomerJoinRequest {
    private final String name;
    private final String email;

    public CustomerJoinRequest(String name, String email) {
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
