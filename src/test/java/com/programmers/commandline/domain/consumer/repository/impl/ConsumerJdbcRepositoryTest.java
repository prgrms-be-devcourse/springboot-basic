package com.programmers.commandline.domain.consumer.repository.impl;

import com.programmers.commandline.domain.consumer.entity.Consumer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsumerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.commandline.domain.consumer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/order_mgmt")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
//            dataSource.setMaximumPoolSize(1000);
//            dataSource.setMinimumIdle(100);

            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

    }

    @Autowired
    ConsumerJdbcRepository consumerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Consumer newConsumer;

    @BeforeAll
    void setup() {
        newConsumer = new Consumer(UUID.randomUUID(), "test_user", "test_user@naver.com", LocalDateTime.now());
        consumerJdbcRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    @Order(2)
    public void testFindAll() throws InterruptedException {
        List<Consumer> consumers = consumerJdbcRepository.findAll();
        assertThat(consumers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    @Order(3)
    public void testFindByName() throws InterruptedException {
        Optional<Consumer> consumers = consumerJdbcRepository.findByName(newConsumer.getName());
        assertThat(consumers.isEmpty(), is(false));

        Optional<Consumer> unknown = consumerJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    @Order(4)
    public void testFindByEmail() throws InterruptedException {
        Optional<Consumer> consumers = consumerJdbcRepository.findByEmail(newConsumer.getEmail());
        assertThat(consumers.isEmpty(), is(false));

        Optional<Consumer> unknown = consumerJdbcRepository.findByEmail("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(5)
    public void testInsert() {
        consumerJdbcRepository.deleteAll();

        consumerJdbcRepository.insert(newConsumer);

        Optional<Consumer> retrievedConsumer = consumerJdbcRepository.findById(newConsumer.getConsumerId());
        assertThat(retrievedConsumer.isEmpty(), is(false));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(6)
    public void testUpdate() {
        newConsumer.changeName("updated-user");
        consumerJdbcRepository.update(newConsumer);

        List<Consumer> all = consumerJdbcRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newConsumer)));

        Optional<Consumer> retrievedConsumer = consumerJdbcRepository.findById(newConsumer.getConsumerId());
        assertThat(retrievedConsumer.isEmpty(), is(false));
        assertThat(retrievedConsumer.get(), samePropertyValuesAs(newConsumer));

    }
}