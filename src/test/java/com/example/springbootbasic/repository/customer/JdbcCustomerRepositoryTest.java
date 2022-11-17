package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.domain.customer.Customer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringJUnitConfig
//@ActiveProfiles("dev")
class JdbcCustomerRepositoryTest {

    @Configuration
//    @ComponentScan(basePackages = {"com.example.springbootbasic.repository.customer"})
    static class Config {

        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/spb_basic")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
            return new NamedParameterJdbcTemplate(jdbcTemplate());
        }

        @Bean
        JdbcCustomerRepository jdbcCustomerRepository() {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate());
        }
    }

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @Test
    @DisplayName("손님 전체 검색 성공")
    void whenFindAllCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = jdbcCustomerRepository.findAllCustomers();

        // then
        assertThat(allCustomers, hasSize(3));
    }

    @Test
    @DisplayName("일반 손님 전체 검색 성공")
    void whenFindAllNormalStatusCustomersThenSuccessTest() {
        // when
        List<Customer> normalCustomers = jdbcCustomerRepository.findCustomersByStatus(NORMAL);

        // then
        assertThat(normalCustomers, is(Collections.emptyList()));
    }

    @Test
    @DisplayName("블랙된 손님 전체 검색 성공")
    void whenFindAllBlackStatusCustomersThenSuccessTest() {
        // when
        List<Customer> blackCustomers = jdbcCustomerRepository.findCustomersByStatus(BLACK);

        // then
        assertThat(blackCustomers, hasSize(3));
    }
}