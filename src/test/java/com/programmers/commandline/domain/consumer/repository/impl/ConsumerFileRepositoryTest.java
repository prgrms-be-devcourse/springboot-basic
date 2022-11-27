package com.programmers.commandline.domain.consumer.repository.impl;

import com.moandjiezana.toml.Toml;
import com.programmers.commandline.domain.consumer.entity.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

@ActiveProfiles("file")
class ConsumerFileRepositoryTest {

    private final String filePath = "./src/test/resources/consumerResources/";
    private final ConsumerFileRepository consumerFileRepository = new ConsumerFileRepository(filePath);

    @BeforeEach
    void setup() {
        File[] consumerFiles = new File(filePath).listFiles();

        assert consumerFiles != null;
        for (File consumerFile : consumerFiles) {
            consumerFile.delete();
        }
    }

    @Test
    @DisplayName("소비자를 파일로 저장하고 저장된 파일명과 소비자의 ID를 검증하자")
    void insert() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        File consumerFile = new File(filePath + consumer.getId());

        //then
        assertThat(consumerFile.getName(), is(consumer.getId()));
    }

    @Test
    @DisplayName("소비자를 업데이트하고 userName 을 검증하라")
    void update() {

        //given
        Toml toml = new Toml();
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());
        String updateUsername = "update_user";
        String updateEmail = "update_user@naver.com";

        //when
        consumerFileRepository.insert(consumer);
        consumer.update(updateUsername, updateEmail);
        consumerFileRepository.update(consumer);

        File consumerFile = new File(filePath + consumer.getId());

        String name = toml.read(consumerFile).getString("name");
        //then
        assertThat(name, is(updateUsername));
    }


    @Test
    @DisplayName("소비자를 생성하고 생성된 파일 갯수를 검증하라")
    void count() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        File[] consumerFiles = new File(filePath).listFiles();

        //then
        assert consumerFiles != null;
        assertThat(consumerFiles.length, is(1));

    }

    @Test
    @DisplayName("파일로 저장된 모든 소비자의 아이디, 비어있는지?, 길이를 검증하라")
    void findAll() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());
        Consumer consumer1 = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        consumerFileRepository.insert(consumer1);
        List<Consumer> consumers = consumerFileRepository.findAll();

        //then
        consumers.forEach(answer -> assertThat(answer, isA(Consumer.class)));
        assertThat(consumers.isEmpty(), is(false));
        assertThat(consumers.size(), is(2));
    }

    @Test
    @DisplayName("소비자를 아이디를 활용해서 찾고 검증하라")
    void findById() {
        //given
        UUID uuid = UUID.randomUUID();
        Consumer consumer = new Consumer(uuid, "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        Consumer foundConsumer = consumerFileRepository.findById(uuid.toString()).get();

        //then
        assertThat(foundConsumer.getId(), is(consumer.getId()));

    }

    @Test
    @DisplayName("소비자를 이름을 활용해서 찾고 검증하라")
    void findByName() {
        //given
        String name = "test_user";
        Consumer consumer = new Consumer(UUID.randomUUID(), name, "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        Consumer foundConsumer = consumerFileRepository.findByName(name).get();

        //then
        assertThat(foundConsumer.getId(), is(consumer.getId()));
    }

    @Test
    @DisplayName("소비자를 이메일을 활용해서 찾고 검증하라")
    void findByEmail() {
        //given
        String email = "test_user@naver.com";
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", email, LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        Consumer foundConsumer = consumerFileRepository.findByEmail(email).get();

        //then
        assertThat(foundConsumer.getId(), is(consumer.getId()));
    }

    @Test
    @DisplayName("소비자 전체를 삭제하고 검증하라")
    void deleteAll() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        consumerFileRepository.deleteAll();
        List<Consumer> consumers = consumerFileRepository.findAll();

        //then
        assertThat(consumers.size(), is(0));
    }
}