package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.UserListRequest;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponse createUser(UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    public UserListResponse getAllUsers() {
        return userService.findAll();
    }

    public UserListResponse getUserListWithVoucherAssigned() {
        return userService.getUserListWithVoucherAssigned();
    }

    public UserResponse getUserByVoucherId(UserListRequest userListRequest) {
        return userService.getUserByVoucherId(userListRequest);
    }
}
