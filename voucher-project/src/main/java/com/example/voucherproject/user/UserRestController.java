package com.example.voucherproject.user;

import com.example.voucherproject.user.dto.UserDTO;
import com.example.voucherproject.user.dto.UserDtoMapper;
import com.example.voucherproject.user.exception.UserNotFoundException;
import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.dto.VoucherDTO;
import com.example.voucherproject.voucher.exception.VoucherNotFoundException;
import com.example.voucherproject.voucher.model.Voucher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.voucherproject.user.dto.UserDtoMapper.asUserModel;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;

    // 유저 전체 조회
    @GetMapping("/users")
    public List<User> usersView(){
        return userService.findAll();
    }

    // 유저 추가
    @PostMapping("/user")
    public User addUserRedirect(@RequestBody UserDTO.Create dto){
        return userService.createUser(asUserModel(dto));
    }

    // 유저 상세 조회
    @GetMapping("/user/{id}")
    public User userDetailView(@PathVariable UUID id){
        var maybeVoucher = userService.findById(id);
        if (maybeVoucher.isPresent()){
            return maybeVoucher.get();
        }
        throw new UserNotFoundException(String.format("%s user not found",id));
    }

    // 유저 삭제
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable UUID id){
        userService.deleteById(id);
    }

    // 유저 검색
    @PostMapping("/user/query")
    public List<User> queryUser(@RequestBody UserDTO.Query queryDTO){
        return userService.findByTypeAndDate(queryDTO);
    }
}
