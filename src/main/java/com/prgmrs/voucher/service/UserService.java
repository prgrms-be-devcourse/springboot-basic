package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.UserRequest;
import com.prgmrs.voucher.dto.request.VoucherIdRequest;
import com.prgmrs.voucher.dto.response.UserListResponse;
import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.repository.UserRepository;
import com.prgmrs.voucher.util.IdGenerator;
import com.prgmrs.voucher.util.UUIDConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final IdGenerator idGenerator;

    public UserService(UserRepository userRepository, IdGenerator idGenerator) {
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
    }

    public UserResponse createUser(UserRequest userRequest) { // 유저 생성
        UUID uuid = idGenerator.generate();
        User user = new User(uuid, new Username(userRequest.username()));
        userRepository.save(user);

        return new UserResponse(uuid, user.username().value());
    }

    public UserListResponse findAll() {
        List<User> userList = userRepository.findAll();
        return new UserListResponse(userList);
    }

    public UserListResponse getUserListWithVoucherAssigned() {
        List<User> userList = userRepository.getUserListWithVoucherAssigned();
        return new UserListResponse(userList);
    }

    public UserResponse getUserByVoucherId(VoucherIdRequest voucherIdRequest) {
        UUID uuid = UUIDConverter.fromString(voucherIdRequest.voucherUuid());
        User user = userRepository.getUserByVoucherId(uuid);

        return new UserResponse(user.userId(), user.username().value());
    }
}
