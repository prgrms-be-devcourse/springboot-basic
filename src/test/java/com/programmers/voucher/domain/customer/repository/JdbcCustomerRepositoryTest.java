package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {
    @Autowired
    private JdbcCustomerRepository customerRepository;

    @AfterEach
    void afterEach() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객 생성에 성공한다.")
    void 고객_생성() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test");
        // when
        Customer result = customerRepository.insert(customer);
        // then
        assertThat(result.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("모든 고객 조회에 성공한다.")
    void 모든_고객_조회() {
        // given
        Customer customer1 = new Customer(UUID.randomUUID(), "test1");
        customerRepository.insert(customer1);
        Customer customer2 = new Customer(UUID.randomUUID(), "test2");
        customerRepository.insert(customer2);

        // when
        List<Customer> result = customerRepository.findAll();
        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("고객 조회에 성공한다.")
    void 고객_조회() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test");
        customerRepository.insert(customer);

        // when
        Customer result = customerRepository.findById(customer.getId()).orElse(null);

        // then
        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("고객 수정에 성공한다.")
    void 고객_수정() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test");
        customerRepository.insert(customer);

        // when
        Customer result = customerRepository.update(new Customer(customer.getId(), "new"));

        // then
        assertThat(result.getNickname()).isEqualTo("new");
    }

    @Test
    @DisplayName("고객 삭제에 성공한다.")
    void 고객_삭제() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test");
        customerRepository.insert(customer);

        // when
        customerRepository.delete(customer.getId());

        // then
        List<Customer> result = customerRepository.findAll();
        assertThat(result).isEmpty();
    }
}
