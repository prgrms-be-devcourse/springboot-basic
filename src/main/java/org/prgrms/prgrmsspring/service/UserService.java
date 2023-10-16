package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.user.User;
import org.prgrms.prgrmsspring.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
