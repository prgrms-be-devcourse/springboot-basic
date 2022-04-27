package com.example.voucherproject.user;


import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserDTO;
import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.voucherproject.user.model.UserDTO.asUserModel;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 생성
    @GetMapping("/user/new")
    public String addUserView(){
        return "user/new-user";
    }
    //,consumes = "application/json"
    @PostMapping(value = "/user/new")
    public String addUser(@RequestParam String name, @RequestParam String type){
        var userDTO = new UserDTO(UUID.randomUUID(), name, UserType.valueOf(type));
        userService.createUser(asUserModel(userDTO));
        // Validation
        if(!StringUtils.hasText(userDTO.getName())){
            log.info("이름 null");
//            bindingResult.addError(new FieldError("userDTO", "userName","이름값은 공백을 허용하지 않습니다."));
        }
        if(userDTO.getType() == null){
            log.info("타입 null");
//            bindingResult.addError(new FieldError("userDTO", "userType","유저 타입을 선택해 주세요."));
        }

//        if(bindingResult.hasErrors()){
//            log.info("errors = {} redirect to 404 page",bindingResult);
//            return "basic/404";
//        }

        return "redirect:../users/";
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
            model.addAttribute("user", asUserModel(maybeUser.get()));
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
        return "redirect:../users/";
    }

    @PostMapping("/user/query")
    public String queryUser2(@RequestParam("type") String type,
                            @Nullable @RequestParam("startDate") String from,
                            @Nullable @RequestParam("endDate") String to, Model model){
        if(from.isEmpty()) { from = "2022-01-01"; }
        if(to.isEmpty()) { to = "2222-12-29"; }
//        List<User> users = userService.findByTypeAndDate(userFilterDTO.userType(),userFilterDTO.from(),userFilterDTO.to());
        List<User> users = userService.findByTypeAndDate(UserType.valueOf(type),from, to);
        model.addAttribute("users", users);
        return "user/users";
    }
}