package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CustomerJdbcRepositoryTest {

    @Autowired
    private CustomerJdbcRepository customerJdbcRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public CustomerJdbcRepository customerJdbcRepository(DataSource dataSource) {
            return new CustomerJdbcRepository(dataSource);
        }
    }

    @Test
    @DisplayName("성공 - customer 단건 저장")
    void save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer");

        //when
        customerJdbcRepository.save(customer);

        //then
        Customer findCustomer = customerJdbcRepository.findById(customer.getCustomerId()).get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("성공 - customer 단건 조회")
    void findById() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer");
        customerJdbcRepository.save(customer);

        //when
        Optional<Customer> optionalCustomer = customerJdbcRepository.findById(customer.getCustomerId());

        //then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void deleteById() {
    }
}