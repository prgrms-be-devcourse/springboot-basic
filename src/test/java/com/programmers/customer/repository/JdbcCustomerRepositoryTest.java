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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("저장된 고객의 정보를 수정할 수 있다.")
    @Test
    void updateCustomerTest() {
        UUID id = UUID.randomUUID();
        Customer oldCustomer = new Customer(id, "고객", LocalDateTime.now());
        jdbcCustomerRepository.save(oldCustomer);
        Customer newCustomer = new Customer(id, "손님", LocalDateTime.now(), LocalDateTime.now());

        Customer updatedCustomer = jdbcCustomerRepository.update(newCustomer);

        assertThat(oldCustomer.getCustomerId()).isEqualTo(updatedCustomer.getCustomerId());
        assertThat(oldCustomer.getName()).isNotEqualTo(updatedCustomer.getName());
    }

    @DisplayName("저장된 모든 고객을 조회할 수 있다.")
    @Test
    void findAllCustomersTest() {
        Customer testCustomer = new Customer(UUID.randomUUID(), "고객", LocalDateTime.now());
        Customer testCustomer2 = new Customer(UUID.randomUUID(), "고객", LocalDateTime.now());
        Customer testCustomer3 = new Customer(UUID.randomUUID(), "고객", LocalDateTime.now());
        jdbcCustomerRepository.save(testCustomer);
        jdbcCustomerRepository.save(testCustomer2);
        jdbcCustomerRepository.save(testCustomer3);

        List<Customer> customers = jdbcCustomerRepository.findAll();

        assertThat(customers.size()).isEqualTo(3);
    }

    @DisplayName("저장된 고객을 ID로 조회할 수 있다.")
    @Test
    void findCustomerByIdTest() {
        UUID id = UUID.randomUUID();
        Customer testCustomer = new Customer(id, "고객", LocalDateTime.now());
        jdbcCustomerRepository.save(testCustomer);

        Customer storedCustomer = jdbcCustomerRepository.findById(id);

        assertThat(testCustomer.getCustomerId()).isEqualTo(storedCustomer.getCustomerId());
    }

    @DisplayName("저장된 고객을 ID를 통해 삭제할 수 있다.")
    @Test
    void deleteCustomerByIdTest() {
        UUID id = UUID.randomUUID();
        Customer testCustomer = new Customer(id, "고객", LocalDateTime.now());
        jdbcCustomerRepository.save(testCustomer);

        jdbcCustomerRepository.deleteById(id);

        assertThatThrownBy(()-> jdbcCustomerRepository.findById(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("잘못된 ID로 삭제 요청을 하면 에러를 반환한다.")
    @Test
    void deleteByWrongIdValueTest() {
        UUID id = UUID.randomUUID();
        UUID wrongId = UUID.randomUUID();
        Customer testCustomer = new Customer(id, "고객", LocalDateTime.now());
        jdbcCustomerRepository.save(testCustomer);

        assertThatThrownBy(() -> jdbcCustomerRepository.deleteById(wrongId))
                .isInstanceOf(NoSuchElementException.class);
    }
}
