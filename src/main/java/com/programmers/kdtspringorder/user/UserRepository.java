package com.programmers.kdtspringorder.user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
}
