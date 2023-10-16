package devcourse.springbootbasic.controller;

import devcourse.springbootbasic.dto.UserFindResponse;
import devcourse.springbootbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    public List<UserFindResponse> findAllBlacklistedUsers() {
        return this.userService.findAllBlacklistedUsers();
    }
}
