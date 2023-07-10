package com.dev.bootbasic.user.repository;

import com.dev.bootbasic.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@JdbcTest
@ActiveProfiles("test")
@Import(JdbcUserRepository.class)
public class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        UUID id = UUID.randomUUID();
        user = User.of(id, "test-user", LocalDateTime.now());
        userRepository.create(user);
    }

    @Test
    public void createTest() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(user, foundUser.get());
    }

    @Test
    public void findByNameTest() {
        Optional<User> foundUser = userRepository.findById(user.getId());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals(user, foundUser.get());
    }

    @Test
    public void deleteByNameTest() {
        userRepository.deleteById(user.getId());
        Optional<User> foundUser = userRepository.findById(user.getId());
        Assertions.assertTrue(foundUser.isEmpty());
    }
}
