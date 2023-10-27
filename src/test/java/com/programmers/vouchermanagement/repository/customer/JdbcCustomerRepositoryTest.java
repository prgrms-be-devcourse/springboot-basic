package com.programmers.vouchermanagement.repository.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@SpringBootTest
class JdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"com.programmers.vouchermanagement.repository.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mysql.getJdbcUrl())
                    .username("root")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcCustomerRepository jdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcCustomerRepository(jdbcTemplate);
        }
    }

    static JdbcDatabaseContainer mysql = new MySQLContainer("mysql:8.0.26")
            .withDatabaseName("test_order_mgmt")
            .withInitScript("schema.sql");

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    static void setUp() {
        mysql.start();
    }

    @AfterAll
    static void cleanUp() {
        mysql.stop();
    }

    @Test
    void testFindAll() {
        Assertions.assertEquals(3, jdbcCustomerRepository.findAll().size());
    }
}
