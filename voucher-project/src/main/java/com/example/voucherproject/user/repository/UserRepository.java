package com.example.voucherproject.user.repository;

import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/* User CRUD */
public interface UserRepository {

    //create
    User insert(User user);

    //read
    List<User> findHavingTypeAll(UserType type);
    List<User> findAll();
    Optional<User> findById(UUID userId);
    //update

    //delete

    long count();

}
