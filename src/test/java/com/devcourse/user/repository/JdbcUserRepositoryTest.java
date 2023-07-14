package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("지정한 이름으로 유저가 저장된다.")
    void saveTest() {
        // given

        // when
        userRepository.save(name);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).name()).isEqualTo(name);
    }

    @Test
    @DisplayName("저장된 유저들의 수만큼 반횐하고 유저들의 이름이 기본 이름을 포함해야 한다.")
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

    @Test
    @DisplayName("id를 통해서 저장된 유저를 조회하면 이름과 아이디가 동일해야 한다.")
    void findByIdSuccessTest() {
        // given
        UUID id = userRepository.save(name);

        // when
        Optional<User> optionalUser = userRepository.findById(id);

        // then
        assertThat(optionalUser).isNotEmpty();

        User user = optionalUser.get();
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo(name);
    }

    @Test
    @DisplayName("id로 저장된 유저를 지우면 아무것도 조회되지 않아야 한다.")
    void deleteByIdTest() {
        // given
        UUID id = userRepository.save(name);

        // when
        userRepository.deleteById(id);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("아이디를 통해서 새로운 이름으로 변경할 수 있고 조회 시 문제가 없어야 한다.")
    void updateTest() {
        // given
        String newName = "Jo-hejow";
        UUID id = userRepository.save(name);

        // when
        userRepository.update(id, newName);

        // then
        Optional<User> optionalUser = userRepository.findById(id);
        assertThat(optionalUser).isNotEmpty();

        User user = optionalUser.get();
        assertThat(user.name()).isEqualTo(newName);
    }
}
