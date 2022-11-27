package com.programmers.commandline.domain.consumer.repository.impl;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.programmers.commandline.global.config.MyDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("jdbc")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsumerNamedJdbcRepositoryTest {
    private final String dbUrl = "jdbc:mysql://localhost/voucherApplication";
    private final String dbUser = "root";
    private final String dbPassword = "root1234!";
    private final MyDataSource myDataSource = new MyDataSource(dbUrl, dbUser, dbPassword);

    private ConsumerNamedJdbcRepository consumerNamedJdbcRepository = new ConsumerNamedJdbcRepository(myDataSource);

    @BeforeEach
    void setup() {
        consumerNamedJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("소비자를 추가를 검증하라")
    public void Insert() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerNamedJdbcRepository.insert(consumer);
        Optional<Consumer> optionalConsumer = consumerNamedJdbcRepository.findById(consumer.getId());

        //then
        assertThat(optionalConsumer.isEmpty(), is(false));
    }

    @Test
    @DisplayName("소비자를 저장하고 해당 소비자를 업데이트 합니다, 그리고 업데이트 한 이름과 이메일을 검증하라")
    void update() {
        //given
        String updateName = "update_user";
        String updateEmail = "update_user@navr.com";
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerNamedJdbcRepository.insert(consumer);
        consumer.update(updateName, updateEmail);

        Consumer updateConsumer = consumerNamedJdbcRepository.update(consumer);

        //then
        assertAll(
                () -> assertThat(updateConsumer.getName(), is(updateName)),
                () -> assertThat(updateConsumer.getEmail(), is(updateEmail))
        );

    }

    @Test
    @DisplayName("리스트의 소비자를 전부 저장하고 저장된 소비자의 갯수를 count 매서드로 반환 받아서 리스트의 길이와 검증하라")
    void count() {
        //given
        List<Consumer> consumers = new ArrayList<>();
        Consumer consumer = new Consumer(
                UUID.randomUUID(),
                "test_user",
                "test_user@naver.com",
                LocalDateTime.now());
        consumers.add(consumer);
        //when
        consumers.forEach(consumerInList -> {
            consumerNamedJdbcRepository.insert(consumerInList);
        });

        int count = consumerNamedJdbcRepository.count();

        //then
        assertThat(count, is(consumers.size()));
    }

    @Test
    @DisplayName("소비자를 저장하고 모든 소비자를 찾습니다. 그리고 소비자의 ID를 검증하라")
    void findAll() {
        //given
        List<Consumer> consumerList = new ArrayList<>();
        Consumer consumer = new Consumer(
                UUID.randomUUID(),
                "test_user",
                "test_user@naver.com",
                LocalDateTime.now());

        consumerList.add(consumer);

        //when
        consumerNamedJdbcRepository.insert(consumer);
        List<Consumer> consumers = consumerNamedJdbcRepository.findAll();

        //then
        assertThat(consumers, samePropertyValuesAs(consumerList));

    }

    @Test
    @DisplayName("소비자를 저장하고 저장할 때 사용한 id값을 가지고 조회하고 검증하라")
    void findById() {
        //given
        UUID uuid = UUID.randomUUID();
        Consumer consumer = new Consumer(uuid, "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerNamedJdbcRepository.insert(consumer);
        Consumer foundConsumer = consumerNamedJdbcRepository.findById(uuid.toString()).get();

        //then
        assertThat(foundConsumer.getId(), is(uuid.toString()));
    }

    @Test
    @DisplayName("소비자를 저장하고 저장할 때 사용한 name값을 가지고 조회하라")
    void findByName() {
        //given
        String name = "test_user";
        Consumer consumer = new Consumer(UUID.randomUUID(), name, "test_user@naver.com", LocalDateTime.now());

        //when
        consumerNamedJdbcRepository.insert(consumer);
        Consumer foundConsumer = consumerNamedJdbcRepository.findByName(name).get();

        //then
        assertThat(foundConsumer.getName(), is(name));
    }

    @Test
    @DisplayName("소비자를 저장하고 저장할 때 사용한 email값을 가지고 조회하라")
    void findByEmail() {
        //given
        String email = "test_user@naver.com";
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", email, LocalDateTime.now());

        //when
        consumerNamedJdbcRepository.insert(consumer);
        Consumer foundConsumer = consumerNamedJdbcRepository.findByEmail(email).get();

        //then
        assertThat(foundConsumer.getEmail(), is(email));
    }

    @Test
    @DisplayName("소비자를 저장하고 삭제하라 그리고 조회한 결과를 검증하라")
    void deleteAll() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerNamedJdbcRepository.insert(consumer);
        consumerNamedJdbcRepository.deleteAll();
        List<Consumer> consumers = consumerNamedJdbcRepository.findAll();

        //then
        assertThat(consumers.isEmpty(), is(true));

    }
}