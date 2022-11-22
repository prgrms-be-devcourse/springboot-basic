package com.programmers.commandline.domain.consumer.repository.impl;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
class ConsumerNamedJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerNamedJdbcRepositoryTest.class);

    @Autowired
    ConsumerNamedJdbcRepository consumerNamedJdbcRepository;
    @Autowired
    DataSource dataSource;

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {

        Consumer consumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());
        consumerNamedJdbcRepository.insert(consumer);

        var retrievedCustomer = consumerNamedJdbcRepository.findById(consumer.getConsumerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(consumer));
    }

    @Test
    void update() {
    }

    @Test
    void count() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void toUUID() {
    }
}