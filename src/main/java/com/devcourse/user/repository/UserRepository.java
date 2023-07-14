package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    void save(String name);

    List<User> findAll();
}
