package com.programmers.springbootbasic.domain.user.presentation;

import com.programmers.springbootbasic.domain.user.application.UserService;
import com.programmers.springbootbasic.domain.user.presentation.dto.CreateUserRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserThymeController {

    private final UserService userService;

    public UserThymeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "user/register";
    }

    @PostMapping("/register")
    public String createUser(@Valid  @ModelAttribute CreateUserRequest request, Model model) {
        Long id = userService.create(request);

        model.addAttribute("userNickname", request.getNickname());
        model.addAttribute("userId", id);

        return "user/registrationComplete";
    }

    @GetMapping
    public String getBlackList(Model model) {
        var blacklistedUsers = userService.findBlacklistedUsers();
        model.addAttribute("blacklistedUsers", blacklistedUsers);
        return "user/blacklistedUsers";
    }
}
