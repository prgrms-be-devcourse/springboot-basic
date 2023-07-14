package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {
    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Test
    @DisplayName("고객 생성에 성공한다.")
    void 고객_생성() {
        // given
        Customer customer = Customer.builder().id(UUID.randomUUID()).nickname("test").build();

        // when
        customerRepository.insert(customer);

        // then
        Customer result = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(result.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("모든 고객 조회에 성공한다.")
    void 모든_고객_조회() {
        // given
        Customer customer1 = Customer.builder().id(UUID.randomUUID()).nickname("test1").build();
        customerRepository.insert(customer1);
        Customer customer2 = Customer.builder().id(UUID.randomUUID()).nickname("test2").build();
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
        Customer customer = Customer.builder().id(UUID.randomUUID()).nickname("test").build();
        customerRepository.insert(customer);

        // when
        Customer result = customerRepository.findById(customer.getId()).orElseThrow();

        // then
        assertThat(result.getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("고객 수정에 성공한다.")
    void 고객_수정() {
        // given
        Customer customer = Customer.builder().id(UUID.randomUUID()).nickname("test").build();
        customerRepository.insert(customer);

        // when
        Customer expected = Customer.builder().id(customer.getId()).nickname("new").build();
        customerRepository.update(expected);

        // then
        Customer result = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(result.getNickname()).isEqualTo(expected.getNickname());
    }

    @Test
    @DisplayName("고객 삭제에 성공한다.")
    void 고객_삭제() {
        // given
        Customer customer = Customer.builder().id(UUID.randomUUID()).nickname("test").build();
        customerRepository.insert(customer);

        // when
        customerRepository.delete(customer.getId());

        // then
        List<Customer> result = customerRepository.findAll();
        assertThat(result).isEmpty();
    }
}
