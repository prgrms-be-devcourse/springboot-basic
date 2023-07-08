package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.UserRequest;
import com.prgmrs.voucher.dto.UserResponse;
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

}
