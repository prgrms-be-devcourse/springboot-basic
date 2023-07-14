package com.devcourse.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

@JdbcTest
@ContextConfiguration(classes = JdbcUserRepository.class)
class JdbcUserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

}
