package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.UserListRequest;
import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.validator.OrderValidator;
import com.prgmrs.voucher.model.validator.UserValidator;
import com.prgmrs.voucher.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final OrderValidator orderValidator;

    public UserService(UserRepository userRepository, UserValidator userValidator, OrderValidator orderValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.orderValidator = orderValidator;
    }

    public UserResponse createUser(UserRequest userRequest) throws WrongRangeFormatException {
        String name = userRequest.username();

        if (!userValidator.isValidNameFormat(name)) {
            throw new WrongRangeFormatException("incorrect name format");
        }

        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, name);

        userRepository.save(user);

        return new UserResponse(user);
    }

    public UserListResponse findAll() {
        List<User> userList = userRepository.findAll();
        return new UserListResponse(userList);
    }

    public UserListResponse getUserListWithVoucherAssigned() {
        List<User> userList = userRepository.getUserListWithVoucherAssigned();
        return new UserListResponse(userList);
    }

    public UserResponse getUserByVoucherId(UserListRequest userListRequest) {
        String order = userListRequest.order();
        List<Voucher> voucherList = userListRequest.voucherList();

        if (!orderValidator.isValidOrder(order, voucherList)) {
            throw new WrongRangeFormatException("possible value out of range");
        }

        int convertedOrder = Integer.parseInt(order);
        Voucher voucher = voucherList.get(convertedOrder - 1);

        User user = userRepository.getUserByVoucherId(voucher);

        return new UserResponse(user);
    }
}
