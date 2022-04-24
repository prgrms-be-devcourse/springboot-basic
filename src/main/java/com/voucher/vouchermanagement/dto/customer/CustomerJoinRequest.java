package com.voucher.vouchermanagement.dto.customer;

public class CustomerJoinRequest {
    private final String name;
    private final String email;

    public CustomerJoinRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
