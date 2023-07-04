package com.dev.bootbasic.user.controller;

import com.dev.bootbasic.user.controller.dto.UserCreateRequest;
import com.dev.bootbasic.user.controller.dto.UserCreateResponse;
import com.dev.bootbasic.user.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserCreateResponse createUser(UserCreateRequest request) {
        return userService.createUser(request);
    }
}
