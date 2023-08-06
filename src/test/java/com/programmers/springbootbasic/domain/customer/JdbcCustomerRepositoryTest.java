package com.programmers.springbootbasic.domain.customer;

import com.programmers.springbootbasic.domain.customer.Repository.JdbcCustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcCustomerRepository.class)
class JdbcCustomerRepositoryTest {
    @Autowired
    private JdbcCustomerRepository repository;

    @Test
    @DisplayName("고객을 저장 할 수 있다")
    void save() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test@gmail.com", "test");
        // when
        UUID id = repository.save(customer);
        // then
        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("고객을 조회 할 수 있다")
    void findByEmail() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test@gmail.com", "test");
        repository.save(customer);
        // when
        Optional<Customer> result = repository.findByEmail(customer.getEmail());
        // then
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("고객을 수정 할 수 있다")
    void update() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "test@gmail.com", "test");
        repository.save(customer);
        Customer forUpdate = new Customer(customer.getCustomerId(), "update@gmail.com", "update");
        // when
        repository.update(forUpdate);
        // then
        Optional<Customer> updated = repository.findByEmail(forUpdate.getEmail());
        assertThat(updated).get().usingRecursiveComparison().isEqualTo(forUpdate);
    }
}
