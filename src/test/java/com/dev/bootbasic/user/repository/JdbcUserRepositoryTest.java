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

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@Import(JdbcUserRepository.class)
public class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        setUser();
    }

    @Test
    public void createTest() {
        User savedUser = userRepository.create(user);
        assertThat(savedUser).isEqualTo(user);
    }

    @Test
    public void findByIdTest() {
        userRepository.create(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void deleteByIdTest() {
        userRepository.create(user);
        userRepository.deleteById(user.getId());
        Optional<User> foundUser = userRepository.findById(user.getId());
        Assertions.assertTrue(foundUser.isEmpty());
    }

    private void setUser() {
        UUID id = UUID.randomUUID();
        user = User.of(id, "test-user", LocalDateTime.now());
    }
}
