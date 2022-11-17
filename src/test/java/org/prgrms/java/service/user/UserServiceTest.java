package org.prgrms.java.service.user;

import org.junit.jupiter.api.*;
import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.prgrms.java.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("서비스를 통해 정상/블랙 유저를 등록할 수 있다.")
    void testCreateUser() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User blockedUser = new User(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        User insertedUser = userService.createUser(user);
        User insertedBlockedUser = userService.createUser(blockedUser);

        assertThat(user, samePropertyValuesAs(insertedUser));
        assertThat(blockedUser, samePropertyValuesAs(insertedBlockedUser));
    }

    @Test
    @DisplayName("ID 같은 유저를 둘 이상 등록할 수 없다.")
    void testCreateUsersWithDuplicatedId() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test", "test@gmail.com");
        User blockedUser = new User(userId, "test2", "test2@gmail.com", true);

        Assertions.assertThrows(UserException.class, () -> {
            userService.createUser(user);
            userService.createUser(blockedUser);
        });
    }

    @Test
    @DisplayName("서비스를 통해 정상 유저를 조회할 수 있다.")
    void testGetUser() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");

        userService.createUser(user);

        assertThat(userService.getUser(user.getUserId()), samePropertyValuesAs(user));
    }

    @Test
    @DisplayName("서비스를 통해 블랙 유저를 조회할 수 있다.")
    void testGetBlackUser() {
        User blockedUser = new User(UUID.randomUUID(), "test", "test@gmail.com", true);

        userService.createUser(blockedUser);

        assertThat(userService.getBlackUser(blockedUser.getUserId()), samePropertyValuesAs(blockedUser));
    }

    @Test
    @DisplayName("존재하지 않는 정상/블랙 유저 ID로 조회하면 예외가 발생한다.")
    void testGetNonExistUser() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User blockedUser = new User(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        userService.createUser(user);
        userService.createUser(blockedUser);

        Assertions.assertThrows(UserException.class, () -> {
            userService.getUser(UUID.randomUUID());
            userService.getBlackUser(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("유저를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllUserWithNoCreation() {
        assertThat(userService.getAllUser(), hasSize(0));
        assertThat(userService.getAllBlackUser(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 일반 유저를 조회할 수 있다.")
    void testGetAllUser() {
        User user = new User(UUID.randomUUID(), "test", "test@gmail.com");
        User user2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com");

        userService.createUser(user);
        userService.createUser(user2);

        assertThat(userService.getAllUser(), hasSize(2));
        assertThat(userService.getAllUser(), containsInAnyOrder(samePropertyValuesAs(user), samePropertyValuesAs(user2)));
    }

    @Test
    @DisplayName("서비스를 통해 모든 블랙 유저를 조회할 수 있다.")
    void testGetAllBlackUser() {
        User blockedUser = new User(UUID.randomUUID(), "test", "test@gmail.com", true);
        User blockedUser2 = new User(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        userService.createUser(blockedUser);
        userService.createUser(blockedUser2);

        assertThat(userService.getAllBlackUser(), hasSize(2));
        assertThat(userService.getAllBlackUser(), containsInAnyOrder(samePropertyValuesAs(blockedUser), samePropertyValuesAs(blockedUser2)));
    }
}
