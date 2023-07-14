package com.devcourse.user.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    void save(String name);
}
