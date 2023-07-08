package com.prgmrs.voucher.dto;

import com.prgmrs.voucher.model.User;

public class UserResponse {
    private final User user;

    public UserResponse(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
