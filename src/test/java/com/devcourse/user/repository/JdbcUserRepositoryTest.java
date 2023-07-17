package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = JdbcUserRepository.class)
class JdbcUserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final String name = "hejow";

    @Test
    @DisplayName("지정한 이름으로 유저가 잘 저장되고 id가 잘 부여되어야 한다.")
    void saveTest() {
        // given

        // when
        userRepository.save(name);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();

        User user = users.get(0);
        assertThat(user.id()).isNotNull();
        assertThat(user.name()).isEqualTo(name);
    }

    @Test
    @DisplayName("저장한 유저들의 수만큼 반횐하고 이름에 기본값이 포함되어야 한다.")
    void findAllTest() {
        // given
        int size = 10;
        IntStream.rangeClosed(1, size)
                .mapToObj(i -> name + i)
                .forEach(newName -> userRepository.save(newName));

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(size);
        assertThat(users).allMatch(user -> user.name().contains(name));
    }

    @Nested
    @DisplayName("아이디로 조회하기 테스트")
    class findByIdTest {
        private final UUID id = userRepository.save(name);

        @Test
        @DisplayName("id로 조회한 유저는 저장된 아이디와 이름이 동일해야 한다.")
        void findByIdSuccessTest() {
            // given

            // when
            Optional<User> optionalUser = userRepository.findById(id);

            // then
            assertThat(optionalUser).isNotEmpty();

            User user = optionalUser.get();
            assertThat(user.id()).isEqualTo(id);
            assertThat(user.name()).isEqualTo(name);
        }

        @Test
        @DisplayName("저장되지 않은 id로 조회하면 빈값이 반환된다.")
        void findByIdFailTest() {
            // given
            UUID noneId = UUID.randomUUID();

            // when
            Optional<User> optionalUser = userRepository.findById(noneId);

            // then
            assertThat(optionalUser).isEmpty();
        }
    }

    @Test
    @DisplayName("id로 저장된 유저를 지우면 삭제된 유저는 조회되지 않아야 한다.")
    void deleteByIdTest() {
        // given
        UUID id = userRepository.save(name);

        // when
        userRepository.deleteById(id);

        // then
        Optional<User> optionalUser = userRepository.findById(id);
        assertThat(optionalUser).isEmpty();
    }

    @Test
    @DisplayName("유저의 이름을 변경한 후 id로 문제 없이 조회되고 변경내용이 반영되어야 한다.")
    void updateTest() {
        // given
        String newName = "Jo-hejow";
        UUID id = userRepository.save(name);

        // when
        userRepository.update(id, newName);

        // then
        Optional<User> optionalUser = userRepository.findById(id);
        assertThat(optionalUser).isNotEmpty();
        assertThat(optionalUser.get().name()).isEqualTo(newName);
    }
}
