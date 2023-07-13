package com.devcourse.user.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<String> findAllBlack();
}
