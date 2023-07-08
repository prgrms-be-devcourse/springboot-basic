package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.UserRequest;
import com.prgmrs.voucher.dto.UserResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.validator.UserValidator;
import com.prgmrs.voucher.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    public UserResponse createUser(UserRequest userRequest) throws WrongRangeFormatException {
        String name = userRequest.getUsername();

        if (!userValidator.isValidNameFormat(name)) {
            throw new WrongRangeFormatException("incorrect name format");
        }

        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, name);

        userRepository.save(user);

        return new UserResponse(user);
    }

}
