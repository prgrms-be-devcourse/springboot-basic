package com.example.voucherproject.user.service;

import com.example.voucherproject.user.dto.UserDTO;
import com.example.voucherproject.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(User User);

    List<User> findAll();

    Optional<User> findById(UUID id);

    boolean deleteById(UUID id);

    List<User> findByTypeAndDate(UserDTO.Query query);
}
