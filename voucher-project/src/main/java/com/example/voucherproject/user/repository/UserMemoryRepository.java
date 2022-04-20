package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.user.domain.User;

import java.util.*;

public class UserMemoryRepository implements UserRepository{
    private final Map<UUID, User> userMap = new HashMap<>();

    @Override
    public User insert(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> findHavingTypeAll(UserType type) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

}
