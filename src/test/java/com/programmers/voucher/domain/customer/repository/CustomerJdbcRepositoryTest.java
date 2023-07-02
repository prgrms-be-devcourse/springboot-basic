package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("성공: customer 단건 저장")
    void save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");

        //when
        customerJdbcRepository.save(customer);

        //then
        Customer findCustomer = customerJdbcRepository.findById(customer.getCustomerId()).get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("성공: customer 단건 조회")
    void findById() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        customerJdbcRepository.save(customer);

        //when
        Optional<Customer> optionalCustomer = customerJdbcRepository.findById(customer.getCustomerId());

        //then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("성공: customer 단건 조회 - 존재하지 않는 customer")
    void findById_ButEmpty() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");

        //when
        Optional<Customer> optionalCustomer = customerJdbcRepository.findById(customer.getCustomerId());

        //then
        assertThat(optionalCustomer).isEmpty();
    }

    @Test
    @DisplayName("성공: customer 목록 조회")
    void findAll() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA@gmail.com", "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB@gmail.com", "customerB");
        customerJdbcRepository.save(customerA);
        customerJdbcRepository.save(customerB);

        //when
        List<Customer> findCustomers = customerJdbcRepository.findAll();

        //then
        assertThat(findCustomers).usingRecursiveFieldByFieldElementComparator()
                .contains(customerA, customerB);
    }

    @Test
    @DisplayName("성공: customer 업데이트")
    void update() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        customerJdbcRepository.save(customer);

        customer.changeName("updatedName");

        //when
        customerJdbcRepository.update(customer);

        //then
        Customer findCustomer = customerJdbcRepository.findById(customer.getCustomerId()).get();
        assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("예외: customer 업데이트 - 존재하지 않는 customer")
    void update_ButNoSuchElement_Then_Exception() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        customer.changeName("updatedName");

        //when
        //then
        assertThatThrownBy(() -> customerJdbcRepository.update(customer))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }

    @Test
    @DisplayName("성공: customer 전체 삭제")
    void deleteAll() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA@gmail.com", "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB@gmail.com", "customerB");
        customerJdbcRepository.save(customerA);
        customerJdbcRepository.save(customerB);

        //when
        customerJdbcRepository.deleteAll();

        //then
        List<Customer> findCustomers = customerJdbcRepository.findAll();
        assertThat(findCustomers).isEmpty();
    }

    @Test
    @DisplayName("성공: customer 단건 삭제")
    void deleteById() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");
        customerJdbcRepository.save(customer);

        //when
        customerJdbcRepository.deleteById(customer.getCustomerId());

        //then
        Optional<Customer> optionalCustomer = customerJdbcRepository.findById(customer.getCustomerId());
        assertThat(optionalCustomer).isEmpty();
    }

    @Test
    @DisplayName("예외: customer 단건 삭제 - 존재하지 않는 customer")
    void deleteById_ButNoSuchElement_Then_Exception() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "customer@gmail.com", "customer");

        //when
        //then
        assertThatThrownBy(() -> customerJdbcRepository.deleteById(customer.getCustomerId()))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }
}