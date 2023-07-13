package com.devcourse.user.domain.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<String> findAllBlack();
}
