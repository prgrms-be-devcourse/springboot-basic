package com.example.springbootbasic.repository.customer;

import com.example.springbootbasic.config.TestConfig;
import com.example.springbootbasic.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class JdbcCustomerRepositoryTest extends TestConfig {

    @Autowired
    private JdbcCustomerRepository customerRepository;

    @Test
    @DisplayName("모든 타입의 고객 검색을 성공한다.")
    void whenFindAllCustomersThenSuccessTest() {
        // when
        List<Customer> allCustomers = customerRepository.findAllCustomers();

        // then
        assertThat(allCustomers).hasSize(3);
    }

    @Test
    @DisplayName("일반 타입으로 등록된 모든 고객 검색을 성공한다.")
    void whenFindAllNormalStatusCustomersThenSuccessTest() {
        // when
        List<Customer> normalCustomers = customerRepository.findCustomersByStatus(NORMAL);

        // then
        assertThat(normalCustomers).isEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("블랙리스트로 등록된 모든 고객 검색을 성공한다.")
    void whenFindAllBlackStatusCustomersThenSuccessTest() {
        // when
        List<Customer> blackCustomers = customerRepository.findCustomersByStatus(BLACK);

        // then
        assertThat(blackCustomers).hasSize(3);
    }

    @Test
    @DisplayName("타입을 통한 고객 검색 중 타입에 null이 들어갔을 경우 Collection.emptyList()를 반환한다.")
    void whenFindCustomersByStatusNullThenSuccessTest() {
        // when
        List<Customer> findAllCustomers = customerRepository.findCustomersByStatus(null);

        // then
        assertThat(findAllCustomers).isEqualTo(Collections.emptyList());
    }
}