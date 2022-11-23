package com.programmers.commandline.domain.consumer.repository.impl;

import com.moandjiezana.toml.Toml;
import com.programmers.commandline.domain.consumer.entity.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsumerFileRepositoryTest {

    private String filePath;

    ConsumerFileRepositoryTest(@Value("${file.consumerBlacklistPath}") String filePath) {
        this.filePath = filePath;
    }

    @Autowired
    private ConsumerFileRepository consumerFileRepository;

    @BeforeEach
    void setup() {
        File[] consumerFiles = new File(filePath).listFiles();

        for (File consumerFile : consumerFiles) {
            consumerFile.delete();
        }
    }


    @Test
    @DisplayName("소비자를 파일로 저장하고 저장된 파일명과 소비자의 ID를 검증하라")
    void insert() {
        //given
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        File consumerFile = new File(filePath + consumer.getConsumerId());

        //then
        assertThat(consumerFile.getName(), is(consumer.getConsumerId()));
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

        File consumerFile = new File(filePath + consumer.getConsumerId());

        //then
        assertThat(toml.read(consumerFile).getString("name"), is(updateUsername));
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
        consumers.forEach(answer -> {
            assertThat(answer, isA(Consumer.class));
        });
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
        Optional<Consumer> optionalConsumer = consumerFileRepository.findById(uuid.toString());

        //then
        assertThat(optionalConsumer.get().getConsumerId(), is(consumer.getConsumerId()));

    }

    @Test
    @DisplayName("소비자를 이름을 활용해서 찾고 검증하라")
    void findByName() {
        //given
        String name = "test_user";
        Consumer consumer = new Consumer(UUID.randomUUID(), name, "test_user@naver.com", LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        Optional<Consumer> foundConsumer  = consumerFileRepository.findByName(name);

        //then
        assertThat(foundConsumer.get().getConsumerId(), is(consumer.getConsumerId()));
    }

    @Test
    @DisplayName("소비자를 이메일을 활용해서 찾고 검증하라")
    void findByEmail() {
        //given
        String email = "test_user@naver.com";
        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", email, LocalDateTime.now());

        //when
        consumerFileRepository.insert(consumer);
        Optional<Consumer> foundConsumer  = consumerFileRepository.findByEmail(email);

        //then
        assertThat(foundConsumer.get().getConsumerId(), is(consumer.getConsumerId()));
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