package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.CustomerDTO;
import com.programmers.springbootbasic.dto.VoucherDTO;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatusJdbcRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = "com.programmers.springbootbasic.repository")
    static class Config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .url("jdbc:mysql://localhost/voucher_mgmt?useUnicode=true&serverTimezone=UTC")
                    .username("test")
                    .password("test")
                    .build();

            return dataSource;
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    StatusJdbcRepository statusJdbcRepository;



}