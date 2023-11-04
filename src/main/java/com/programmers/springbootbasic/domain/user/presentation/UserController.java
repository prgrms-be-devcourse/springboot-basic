package com.programmers.springbootbasic.domain.user.presentation;

import com.programmers.springbootbasic.domain.user.application.UserService;
import com.programmers.springbootbasic.domain.user.presentation.dto.CreateUserRequest;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(@Valid CreateUserRequest request) {
        userService.create(request);
    }

    public List<UserResponse> getBlackList() {
        return userService.findBlacklistedUsers();
    }

}
