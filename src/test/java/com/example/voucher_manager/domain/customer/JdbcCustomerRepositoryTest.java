package com.example.voucher_manager.domain.customer;

import com.example.voucher_manager.domain.voucher.*;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class JdbcCustomerRepositoryTest {

    @Container
    protected static final MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.0.19");

    @Configuration
    @ComponentScan(basePackages = "com.example.voucher_manager.domain.customer")
    static class Config {

        @Bean
        public DataSource dataSource() {
            mysqlContainer.withInitScript("init.sql").start();

            return DataSourceBuilder.create()
                    .driverClassName(mysqlContainer.getDriverClassName())
                    .url(mysqlContainer.getJdbcUrl())
                    .username(mysqlContainer.getUsername())
                    .password(mysqlContainer.getPassword())
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }

    }

    @Autowired
    JdbcCustomerRepository customerRepository;

    @AfterEach
    void clear() {
        customerRepository.clear();
    }

    @Test
    @DisplayName("고객 정보가 database에 저장된다.")
    void insertTest() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        assertThat(customerRepository.insert(customer), not(Optional.empty()));
    }

    @Test
    @DisplayName("동일한 ID를 가지는 고객정보는 저장될 수 없다.")
    void duplicatedIdTest() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        customerRepository.insert(customer);

        Customer customer2 = new Customer(customer.getCustomerId(), "yoonoh2", "yoonoh2@naver.com");
        assertThrows(DuplicateKeyException.class, () -> customerRepository.insert(customer2));
    }

    @Test
    @DisplayName("동일한 이메일의 고객은 존재할 수 없다.")
    void duplicatedInsertTest() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        customerRepository.insert(customer);

        Customer customer2 = new Customer(customer.getCustomerId(), "yoonoh2", "yoonoh@naver.com");
        assertThrows(DuplicateKeyException.class, () -> customerRepository.insert(customer2));
    }

    @Test
    @DisplayName("CustomerId를 통해 고객정보를 찾는다.")
    void findById() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        customerRepository.insert(customer);

        assertThat(customerRepository.findById(customer.getCustomerId()).get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객의 email을 통해 고객정보를 찾는다.")
    void findByEmail() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        customerRepository.insert(customer);

        assertThat(customerRepository.findByEmail(customer.getEmail()).get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("Database에 저장된 모든 고객정보를 반환한다.")
    void findAll() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        customerRepository.insert(customer);

        Customer customer2 = new Customer(UUID.randomUUID(), "yoonoh2", "yoonoh2@naver.com");
        customerRepository.insert(customer2);

        assertThat(customerRepository.findAll(), containsInAnyOrder(
                samePropertyValuesAs(customer),
                samePropertyValuesAs(customer2)
        ));
    }

    @Test
    @DisplayName("고객의 정보를 갱신할 수 있다.")
    void update() {
        Customer customer = new Customer(UUID.randomUUID(), "yoonoh", "yoonoh@naver.com");
        customerRepository.insert(customer);

        customer.changeName("another-name"); // 이름 변경
        customerRepository.update(customer);

        var find = customerRepository.findById(customer.getCustomerId()).get();

        assertThat(find.getName(), not(customer.getName())); // 이전 이름과 달라졌어야함
    }
}