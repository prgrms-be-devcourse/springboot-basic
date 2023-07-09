package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan("com.programmers.customer")
    static class Config {
    }

    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    @DisplayName("DB에 고객 정보를 저장할 수 있다.")
    @Test
    void insertVoucherTest() {

        Customer testCustomer = new Customer(UUID.randomUUID(), "고객", LocalDateTime.now());

        Customer storedCustomer = jdbcCustomerRepository.save(testCustomer);

        assertThat(testCustomer.getCustomerId()).isEqualTo(storedCustomer.getCustomerId());
    }
}
