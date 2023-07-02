package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;
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
    @DisplayName("성공 - customer 단건 조회 - 존재하지 않는 customer")
    void findById_ButEmpty() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer");

        //when
        Optional<Customer> optionalCustomer = customerJdbcRepository.findById(customer.getCustomerId());

        //then
        assertThat(optionalCustomer).isEmpty();
    }

    @Test
    @DisplayName("성공 - customer 목록 조회")
    void findAll() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB");
        customerJdbcRepository.save(customerA);
        customerJdbcRepository.save(customerB);

        //when
        List<Customer> findCustomers = customerJdbcRepository.findAll();

        //then
        assertThat(findCustomers).usingRecursiveFieldByFieldElementComparator()
                .contains(customerA, customerB);
    }

    @Test
    void update() {
    }

    @Test
    @DisplayName("성공 - customer 전체 삭제")
    void deleteAll() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB");
        customerJdbcRepository.save(customerA);
        customerJdbcRepository.save(customerB);

        //when
        customerJdbcRepository.deleteAll();

        //then
        List<Customer> findCustomers = customerJdbcRepository.findAll();
        assertThat(findCustomers).isEmpty();
    }

    @Test
    @DisplayName("성공 - customer 단건 삭제")
    void deleteById() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer");
        customerJdbcRepository.save(customer);

        //when
        customerJdbcRepository.deleteById(customer.getCustomerId());

        //then
        Optional<Customer> optionalCustomer = customerJdbcRepository.findById(customer.getCustomerId());
        assertThat(optionalCustomer).isEmpty();
    }
}