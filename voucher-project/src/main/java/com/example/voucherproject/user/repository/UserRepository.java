package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.model.UserType;
import com.example.voucherproject.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User insert(User user);

    List<User> findAllByUserType(UserType type);

    List<User> findAll();

    Optional<User> findById(UUID userId);

    int deleteById(UUID id);

    int deleteAll();

    long count();

    List<User> findByTypeAndDate(UserType type, String from, String to);
}
