package com.dev.bootbasic.user.repository;

import com.dev.bootbasic.user.domain.User;
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

    private static final String TEST_USER_NAME = "test-user";
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = createUser();
    }

    @Test
    public void createTest() {
        userRepository.create(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser.get()).isEqualTo(user);
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
        assertThat(foundUser).isEmpty();
    }

    private User createUser() {
        UUID id = UUID.randomUUID();
        return User.of(id, TEST_USER_NAME, LocalDateTime.now());
    }
}
