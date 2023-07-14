package com.devcourse.user.repository;

import com.devcourse.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ContextConfiguration(classes = JdbcUserRepository.class)
class JdbcUserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("지정한 이름으로 유저가 저장된다.")
    void saveTest() {
        // given
        String hejow = "hejow";

        // when
        userRepository.save(hejow);

        // then
        List<User> users = userRepository.findAll();
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).name()).isEqualTo(hejow);
    }

    @Test
    @DisplayName("저장된 유저들의 수만큼 반횐하고 유저들의 이름이 기본 이름을 포함해야 한다.")
    void findAllTest() {
        // given
        int size = 10;
        String basicName = "hejow";
        IntStream.rangeClosed(1, size)
                .mapToObj(i -> basicName + i)
                .forEach(name -> userRepository.save(name));

        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(size);
        assertThat(users).allMatch(user -> user.name().contains(basicName));
    }

}
