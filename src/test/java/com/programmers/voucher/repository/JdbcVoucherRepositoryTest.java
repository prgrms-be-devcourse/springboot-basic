package com.programmers.voucher.repository;

import com.programmers.voucher.domain.FixedDiscount;
import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8")
            .withDatabaseName("spring_basic")
            .withUsername("root")
            .withPassword("root33")
            .withInitScript("schema.sql");

    @Configuration
    @ComponentScan(basePackages = "com.programmers")
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url(mySQLContainer.getJdbcUrl())
                    .username(mySQLContainer.getUsername())
                    .password(mySQLContainer.getPassword())
                    .driverClassName(mySQLContainer.getDriverClassName())
                    .build();
        }
    }

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @Test
    void insertTest() {
        Voucher testVoucehr = new Voucher(UUID.randomUUID(), new FixedDiscount(100), LocalDateTime.now());

        jdbcVoucherRepository.save(testVoucehr);
    }
}