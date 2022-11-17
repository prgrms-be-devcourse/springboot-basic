package org.prgrms.java.repository.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {
    private static final UserRepository userRepository = new MemoryUserRepository();

    @BeforeEach
    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("정상/블랙 유저를 등록할 수 있다.")
    void testInsert() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User blockedUser = new User(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        User insertedUser = userRepository.insert(user);
        User insertedBlockedUser = userRepository.insert(blockedUser);

        assertThat(user, samePropertyValuesAs(insertedUser));
        assertThat(blockedUser, samePropertyValuesAs(insertedBlockedUser));
    }

    @Test
    @DisplayName("동일한 ID의 유저는 등록할 수 없다.")
    void testInsertSameIdUser() {
        assertThrows(UserException.class, () -> {
            UUID userId = UUID.randomUUID();
            User user = new User(userId, "test", "test@gmail.com");
            User user2 = new User(userId, "test2", "test2@gmail.com");

            userRepository.insert(user);
            userRepository.insert(user2);
        });
    }

    @Test
    @DisplayName("등록한 유저가 정상적으로 반환돼야 한다.")
    void testFindById() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User user2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com");

        userRepository.insert(user);
        userRepository.insert(user2);

        assertThat(userRepository.findById(user.getUserId(), user.isBlocked()).get(), samePropertyValuesAs(user));
        assertThat(userRepository.findById(user2.getUserId(), user2.isBlocked()).get(), samePropertyValuesAs(user2));
        assertThat(userRepository.findById(user.getUserId(), user.isBlocked()).get(), not(samePropertyValuesAs((user2))));
    }

    @Test
    @DisplayName("등록한 유저와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User user2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com");

        userRepository.insert(user);
        userRepository.insert(user2);

        assertThat(userRepository.findAll(user.isBlocked()).isEmpty(), is(false));
        assertThat(userRepository.findAll(user2.isBlocked()), hasSize(2));
    }

    @Test
    @DisplayName("등록한 유저와 전체 삭제한 개수가 같다.")
    void testDeleteAll() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User user2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com");
        User blackUser = new User(UUID.randomUUID(), "unknown", "spam@spam.com", true);

        userRepository.insert(user);
        userRepository.insert(user2);
        userRepository.insert(blackUser);

        assertThat(userRepository.deleteAll(), is(3L));
    }
}