package org.prgrms.java.repository.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class MemoryUserRepositoryTest {
    @Configuration
    static class Config {
        @Bean
        UserRepository userRepository() {
            return new MemoryUserRepository();
        }
    }

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상/블랙 유저를 등록할 수 있다.")
    void testInsert() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com", false);
        User blockedUser = new User(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        userRepository.insert(user, user.isBlocked());
        userRepository.insert(blockedUser, blockedUser.isBlocked());

        assertThat(userRepository.findAll(false).isEmpty(), is(false));
        assertThat(userRepository.findAll(true).isEmpty(), is(false));
    }

    @Test
    @DisplayName("동일한 ID의 유저는 등록할 수 없다.")
    void testInsertSameIdUser() {
        assertThrows(UserException.class, () -> {
            UUID userId = UUID.randomUUID();
            User user = new User(userId, "test", "test@gmail.com", false);
            User user2 = new User(userId, "test2", "test2@gmail.com", false);

            userRepository.insert(user, user.isBlocked());
            userRepository.insert(user2, user2.isBlocked());
        });
    }

    @Test
    @DisplayName("등록한 유저가 정상적으로 반환돼야 한다.")
    void testFindById() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com", false);
        User user2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com", false);

        userRepository.insert(user, user.isBlocked());
        userRepository.insert(user2, user2.isBlocked());

        assertThat(userRepository.findById(user.getUserId(), user.isBlocked()).get(), samePropertyValuesAs(user));
        assertThat(userRepository.findById(user2.getUserId(), user2.isBlocked()).get(), samePropertyValuesAs(user2));
        assertThat(userRepository.findById(user.getUserId(), user.isBlocked()).get(), not(samePropertyValuesAs((user2))));
    }

    @Test
    @DisplayName("등록한 바우처와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com", false);
        User user2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com", false);

        userRepository.insert(user, user.isBlocked());
        userRepository.insert(user2, user2.isBlocked());

        assertThat(userRepository.findAll(user.isBlocked()).isEmpty(), is(false));
        assertThat(userRepository.findAll(user2.isBlocked()), hasSize(2));
    }
}