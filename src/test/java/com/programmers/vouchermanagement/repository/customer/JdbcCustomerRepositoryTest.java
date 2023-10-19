package com.programmers.vouchermanagement.repository.customer;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"com.programmers.vouchermanagement.repository.customer"})
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/order_mgmt")
                    .username("root")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    @Test
    public void testHikariConnection() {
        Assertions.assertEquals(dataSource.getClass().getName(), "com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    public void testFindByName() {
        jdbcCustomerRepository.findByName("test").forEach(System.out::println);
    }
}
