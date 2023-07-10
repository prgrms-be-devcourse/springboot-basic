package com.dev.bootbasic.user.service;

import com.dev.bootbasic.user.controller.dto.UserCreateRequest;
import com.dev.bootbasic.user.controller.dto.UserCreateResponse;
import com.dev.bootbasic.user.domain.User;
import com.dev.bootbasic.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCreateResponse createUser(UserCreateRequest request) {
        User user = User.of(randomUUID(), request.name(), LocalDateTime.now());
        User savedUser = userRepository.create(user);

        return UserCreateResponse.from(savedUser);
    }
}
