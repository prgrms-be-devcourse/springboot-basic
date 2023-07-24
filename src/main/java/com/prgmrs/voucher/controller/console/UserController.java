package com.prgmrs.voucher.controller.console;

import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.enums.StatusCode;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseDTO<?> createUser(UserRequest userRequest) {
        try {
            return new ResponseDTO<>(userService.createUser(userRequest), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> getAllUsers() {
        try {
            return new ResponseDTO<>(userService.findAll(), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> getUserListWithVoucherAssigned() {
        try {
            return new ResponseDTO<>(userService.getUserListWithVoucherAssigned(), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> getUserByVoucherId(VoucherIdRequest voucherIdRequest) {
        try {
            return new ResponseDTO<>(userService.getUserByVoucherId(voucherIdRequest), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }
}
