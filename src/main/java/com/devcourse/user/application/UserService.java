package com.devcourse.user.application;

import com.devcourse.user.User;
import com.devcourse.user.repository.BlackListRepository;
import com.devcourse.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BlackListRepository blackListRepository;

    public UserService(UserRepository userRepository, BlackListRepository blackListRepository) {
        this.userRepository = userRepository;
        this.blackListRepository = blackListRepository;
    }

    public void create(String name) {
        userRepository.save(name);
    }

    public List<String> findAll() {
        return userRepository.findAll().stream()
                .map(User::toStringResponse)
                .toList();
    }

    public List<String> findAllBlack() {
        return blackListRepository.findAllBlack();
    }
}
