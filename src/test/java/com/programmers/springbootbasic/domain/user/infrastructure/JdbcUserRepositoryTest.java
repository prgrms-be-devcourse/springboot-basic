package com.programmers.springbootbasic.domain.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("JdbcUserRepository 테스트")
class JdbcUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Id 값이 null인 유저가 Id 값이 할당 되어 저장 된다.")
    @Test
    void success_save() {
        //given
        User user = new User(null, "user1", false);

        //when
        User result = userRepository.save(user);

        //then
        assertThat(userRepository.findAll()).hasSize(1);
        assertNotNull(result.getId());
        assertThat(result).extracting("nickname", "blocked")
            .containsExactly("user1", false);
    }

    @DisplayName("Id 값이 null이 아닌 유저를 저장해도 Id 값이 DB에 의해 생성 된다.")
    @Test
    void success_save_userHasId() {
        //given
        var previousId = -1L;
        User user = new User(previousId, "user1", false);

        //when
        User result = userRepository.save(user);

        //then
        assertThat(userRepository.findAll()).hasSize(1);
        assertNotNull(result.getId());
        assertNotEquals(result.getId(), previousId);
        assertThat(result).extracting("nickname", "blocked")
            .containsExactly("user1", false);
    }

    @DisplayName("존재하는 User Id 값으로 User를 찾는다.")
    @Test
    void success_findById() {
        //given
        User user1 = new User(null, "user1", false);
        User user2 = new User(null, "user2", false);
        User user3 = new User(null, "user3", true);
        userRepository.save(user1);
        var savedUser2 = userRepository.save(user2);
        userRepository.save(user3);

        //when
        Optional<User> result = userRepository.findById(savedUser2.getId());

        //then
        assertThat(result).isPresent();
        assertThat(result.get()).extracting("nickname", "blocked")
            .containsExactly("user2", false);
    }

    @DisplayName("존재하지 않는 User Id 값으로 User를 찾으면 빈 값이 반환 된다.")
    @Test
    void success_findById_notFound() {
        //given
        User user1 = new User(null, "user1", false);
        User user2 = new User(null, "user2", false);
        User user3 = new User(null, "user3", true);
        var savedUser1 = userRepository.save(user1);
        var savedUser2 = userRepository.save(user2);
        var savedUser3 = userRepository.save(user3);
        List<Long> existedIds = List.of(savedUser1.getId(), savedUser2.getId(), savedUser3.getId());

        Long notExistedId = createNotExistedId(existedIds);

        //when
        Optional<User> result = userRepository.findById(notExistedId);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("모든 User를 찾는다.")
    @Test
    void success_findAll() {
        //given
        User user1 = new User(null, "user1", false);
        User user2 = new User(null, "user2", false);
        User user3 = new User(null, "user3", true);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //when
        var result = userRepository.findAll();

        //then
        assertThat(result).hasSize(3);
        assertThat(result).extracting("nickname", "blocked")
            .containsExactlyInAnyOrder(
                tuple("user1", false),
                tuple("user2", false),
                tuple("user3", true)
            );
    }

    @DisplayName("존재하는 User Id 값으로 User를 삭제할 수 있다.")
    @Test
    void success_deleteById() {
        //given
        User user1 = new User(null, "user1", false);
        var savedUser = userRepository.save(user1);

        //when
        var result = userRepository.deleteById(savedUser.getId());

        //then
        assertThat(userRepository.findAll()).hasSize(0);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("존재하지 않는 User Id 값으로 User를 삭제하면 0을 반환 한다.")
    @Test
    void success_deleteById_notFound() {
        //given
        User user1 = new User(null, "user1", false);
        var savedUser = userRepository.save(user1);
        List<Long> existedIds = List.of(savedUser.getId());

        Long notExistedId = createNotExistedId(existedIds);

        //when
        var result = userRepository.deleteById(notExistedId);

        //then
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("Id 값이 존재하는 User를 수정하면 1을 반환 한다.")
    @Test
    void success_update() {
        //given
        User user1 = new User(null, "user1", false);
        var savedUser = userRepository.save(user1);

        //when
        var result = userRepository.update(new User(savedUser.getId(), "user2", true));

        //then
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(result).isEqualTo(1);
        assertThat(userRepository.findById(savedUser.getId())).isPresent()
            .get().extracting("nickname", "blocked")
            .containsExactly("user2", true);
    }

    @DisplayName("Id 값이 존재하지 않는 User를 수정하면 0을 반환 한다.")
    @Test
    void success_update_notFound() {
        //given
        User user1 = new User(null, "user1", false);
        var savedUser = userRepository.save(user1);
        List<Long> existedIds = List.of(savedUser.getId());

        Long notExistedId = createNotExistedId(existedIds);

        //when
        var result = userRepository.update(new User(notExistedId, "user2", true));

        //then
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("블랙리스트에 등록 된 유저를 찾는다.")
    @Test
    void success_findBlacklistedUsers() {
        //given
        User user1 = new User(null, "user1", false);
        User user2 = new User(null, "user2", false);
        User user3 = new User(null, "user3", true);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //when
        var result = userRepository.findBlacklistedUsers();

        //then
        assertThat(result).hasSize(1);
        assertThat(result).extracting("nickname", "blocked")
            .containsExactly(tuple("user3", true));
    }

    @DisplayName("존재하는 닉네임으로 유저를 찾는다.")
    @Test
    void success_findByNickname() {
        //given
        User user1 = new User(null, "user1", false);
        User user2 = new User(null, "user2", false);
        User user3 = new User(null, "user3", true);
        userRepository.save(user1);
        var savedUser2 = userRepository.save(user2);
        userRepository.save(user3);

        //when
        var result = userRepository.findByNickname("user2");

        //then
        assertThat(result)
            .isPresent()
            .get()
            .extracting("id", "nickname", "blocked")
            .containsExactly(savedUser2.getId(), "user2", false);
    }

    @DisplayName("존재하지 않는 닉네임으로 유저를 찾으면 빈 값이 반환 된다.")
    @Test
    void success_findByNickname_notFound() {
        //given
        User user1 = new User(null, "user1", false);
        User user2 = new User(null, "user2", false);
        User user3 = new User(null, "user3", true);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //when
        var result = userRepository.findByNickname("user4");

        //then
        assertThat(result).isEmpty();
    }

    private static Long createNotExistedId(List<Long> existedIds) {
        Random random = new Random();
        Long notExistedId = null;
        do {
            var randomLong = random.nextLong();
            notExistedId = randomLong & Long.MAX_VALUE;
        } while (existedIds.contains(notExistedId));
        return notExistedId;
    }
}
