package com.dev.bootbasic.user.repository;

import com.dev.bootbasic.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User create(User user);
    Optional<User> findByName(String name);
    void deleteByName(String name);
}
