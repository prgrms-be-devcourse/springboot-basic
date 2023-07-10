package com.prgmrs.voucher.dto.response;

import com.prgmrs.voucher.model.User;

import java.util.List;

public class UserListResponse {
    private final List<User> userList;

    public UserListResponse(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }
}
