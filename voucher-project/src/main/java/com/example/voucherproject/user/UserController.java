package com.example.voucherproject.user;


import com.example.voucherproject.user.dto.UserDTO;
import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.voucherproject.user.dto.UserDtoMapper.asUserModel;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 생성 (View)
    @GetMapping("/user")
    public String addUserView(){
        return "user/new-user";
    }

    // 유저 생성
    @PostMapping("/user")
    public String addUser(@ModelAttribute UserDTO.Create dto){
        userService.createUser(asUserModel(dto));
        return "redirect:/users";
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public String getUsersView(Model model){
        var users = userService.findAll();
        model.addAttribute("users",  users);
        return "user/users";
    }

    // 유저 상세 조회
    @GetMapping("/user/{id}")
    public String getUserDetail(@PathVariable UUID id, Model model){
        var maybeUser = userService.findById(id);
        if (maybeUser.isPresent()){
            model.addAttribute("user", maybeUser.get());
            return "user/user-detail";
        }
        else{
            return "basic/404";
        }
    }

    // 유저 삭제
    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable UUID id){
        userService.deleteById(id);
        return "redirect:/users";
    }

    // 유저 검색
    @PostMapping("/user/query")
    public String queryUser(@ModelAttribute UserDTO.Query query, Model model){
        System.out.println(query);
        List<User> users = userService.findByTypeAndDate(query);
        model.addAttribute("users", users);
        return "user/users";
    }
}