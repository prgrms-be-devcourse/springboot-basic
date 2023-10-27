package org.programmers.springorder.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;
import org.programmers.springorder.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "command.line.runner.enabled=false")
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 저장에 성공한다.")
    void save() {
        // given
        UUID customerId = UUID.randomUUID();
        String customerName = "홍길동";
        CustomerType customerType = CustomerType.NORMAL;

        Customer customer = Customer.toCustomer(customerId, customerName, customerType);

        // when
        customerRepository.save(customer);
        Customer findCustomer = customerRepository.findById(customerId).get();

        // then
        assertThat(findCustomer.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("전체 회원 조회에 성공한다.")
    void findAll() {
        // given
        Customer customer = Customer.toCustomer(UUID.randomUUID(), "홍길동", CustomerType.NORMAL);
        customerRepository.save(customer);

        // when
        List<Customer> customerList = customerRepository.findAll();

        // then
        assertThat(customerList).hasSize(1);
    }

    @Test
    @DisplayName("블랙리스트 회원 조회에 성공한다.")
    void findAllBlacklist() {
        // given
        Customer customer1 = Customer.toCustomer(UUID.randomUUID(), "홍길동", CustomerType.NORMAL);
        Customer customer2 = Customer.toCustomer(UUID.randomUUID(), "세종대왕", CustomerType.BLACK);
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // when
        List<Customer> blacklist = customerRepository.findAllBlackList();

        // then
        assertThat(blacklist).hasSize(1);
    }

    @Test
    @DisplayName("회원 ID로 회원 조회에 성공한다.")
    void findById() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.toCustomer(customerId, "홍길동", CustomerType.NORMAL);
        customerRepository.save(customer);

        // when
        Optional<Customer> findCustomer = customerRepository.findById(customerId);

        // then
        assertThat(findCustomer).isPresent();
        assertThat(findCustomer.get().getCustomerId()).isEqualTo(customerId);
    }

}