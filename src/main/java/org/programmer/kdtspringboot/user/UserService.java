package org.programmer.kdtspringboot.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository UserRepository;

    public UserService(org.programmer.kdtspringboot.user.UserRepository userRepository) {
        UserRepository = userRepository;
    }

    public void createBlackListUser(UUID userId, String userName) {
        User user = new BlackListUser(userId, userName);
        UserRepository.saveUser(user);
    }

    public List<User> findAllUsers() {
        return UserRepository.findAll();
    }
}
