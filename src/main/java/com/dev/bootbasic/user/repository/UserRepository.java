package com.dev.bootbasic.user.repository;

import com.dev.bootbasic.user.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User create(User user);
    Optional<User> findById(UUID id);
    void deleteById(UUID id);
}
