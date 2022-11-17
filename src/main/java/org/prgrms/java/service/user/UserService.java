package org.prgrms.java.service.user;

import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.prgrms.java.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.insert(user);
    }

    public User getUser(UUID userId) {
        return userRepository.findById(userId, false)
                .orElseThrow(() -> new UserException(String.format("Can not find a user for %s", userId)));
    }

    public User getBlackUser(UUID userId) {
        return userRepository.findById(userId, true)
                .orElseThrow(() -> new UserException(String.format("Can not find a black user for %s", userId)));
    }

    public Collection<User> getAllUser() {
        return userRepository.findAll(false);
    }

    public Collection<User> getAllBlackUser() {
        return userRepository.findAll(true);
    }
}
