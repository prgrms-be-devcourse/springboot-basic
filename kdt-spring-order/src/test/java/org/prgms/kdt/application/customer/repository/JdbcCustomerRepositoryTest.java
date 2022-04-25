package org.prgms.kdt.application.customer.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdt.application.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void beforeEach() {
        customer = new Customer(
            UUID.randomUUID(),
            "sample1",
            "sample1@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerRepository.insert(customer);
    }

    @AfterEach
    void dataCleanup() {
        customerRepository.deleteAll();
    }

    @Test
    void getBlacklist() {
    }

    @Test
    @DisplayName("Customer insert 성공")
    void insert() {
        Customer customer = new Customer(
            UUID.randomUUID(),
            "example",
            "example@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Customer insertCustomer = customerRepository.insert(customer);
        assertThat(customer).isEqualTo(insertCustomer);
    }

    @Test
    @DisplayName("Customer update 성공")
    void update() {
        Customer updateCustomer = new Customer(
            customer.getCustomerId(),
            "update",
            "update@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerRepository.update(customer);
    }

    @Test
    @DisplayName("전체 고객을 조회")
    void findAll() {
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("customerId로 고객 찾기")
    void findById() {
        Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.get()).isEqualTo(customer);
        Optional<Customer> findUnknownCustomer = customerRepository.findById(UUID.randomUUID());
        assertThat(findUnknownCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customerName 으로 고객 찾기")
    void findByName() {
        Optional<Customer> findCustomer = customerRepository.findByName(customer.getName());
        assertThat(findCustomer.get()).isEqualTo(customer);
        Optional<Customer> findUnknownCustomer = customerRepository.findByName("unknownName");
        assertThat(findUnknownCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customerId를 이용해서 customer 삭제")
    void delete() {
        int delete = customerRepository.delete(customer.getCustomerId());
        Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(findCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customers 테이블의 모든 tuple 삭제")
    void deleteAll() {
        customerRepository.deleteAll();
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.isEmpty()).isTrue();
    }
}