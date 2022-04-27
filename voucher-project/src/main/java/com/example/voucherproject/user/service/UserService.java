package com.example.voucherproject.user.service;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserDTO;
import com.example.voucherproject.user.model.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User User);

    List<User> findAll();

    Optional<UserDTO> findById(UUID id);

    boolean deleteById(UUID id);

    List<User> findByTypeAndDate(UserType valueOf, String from, String to);
}
