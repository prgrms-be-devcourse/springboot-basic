package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {
    UUID save(String name);

    List<User> findAll();

    Optional<User> findById(UUID id);
}
