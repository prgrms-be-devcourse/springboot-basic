package com.prgmrs.voucher.dto.request;

public class UserRequest {
    private final String username;

    public UserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
