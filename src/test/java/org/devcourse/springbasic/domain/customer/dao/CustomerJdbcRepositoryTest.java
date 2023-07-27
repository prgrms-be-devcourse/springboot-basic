package org.devcourse.springbasic.domain.customer.dao;

import org.devcourse.springbasic.domain.customer.domain.Customer;
import org.devcourse.springbasic.global.exception.custom.DuplicateEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerJdbcRepositoryTest {

    @Autowired
    private CustomerJdbcRepository customerRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public CustomerJdbcRepository customerRepository(NamedParameterJdbcTemplate template) {
            return new CustomerJdbcRepository(template);
        }
    }

    private Customer getetCustomer() {
        UUID customerId = UUID.randomUUID();
        String name = "lee";
        String email = "lee@example.com";
        LocalDateTime createdAt = LocalDateTime.now();
        Customer customer = new Customer(customerId, name, email, null, createdAt);
        return customer;
    }

    @Test
    @DisplayName("새로운 회원 추가")
    void savedValidCustomer() {
        // given
        Customer customer = getetCustomer();
        // when
        UUID savedCustomerId = customerRepository.save(customer);
        // then
        assertThat(savedCustomerId).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("중복된 이메일 가입 시, 예외가 발생")
    void saveDuplicateEmail() {
        // given
        Customer customer = getetCustomer();
        customerRepository.save(customer);
        Customer customer2 = new Customer(UUID.randomUUID(), "kim", customer.getEmail(), null, LocalDateTime.now());
        // when
        assertThatThrownBy(() -> customerRepository.save(customer2))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("회원 업데이트")
    void updateExistingCustomer() {
        // given
        Customer customer = getetCustomer();
        customerRepository.save(customer);

        // when
        String updatedName = "kim";
        String updatedEmail = "kim@example.com";
        Customer updatedCustomer = new Customer(
                customer.getCustomerId(),
                updatedName,
                updatedEmail,
                null,
                customer.getCreatedAt());
        UUID updatedCustomerId = customerRepository.update(updatedCustomer);

        // then
        assertThat(updatedCustomerId).isEqualTo(customer.getCustomerId());
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getName()).isEqualTo(updatedName);
        assertThat(foundCustomer.get().getEmail()).isEqualTo(updatedEmail);
    }

    @Test
    @DisplayName("없는 회원 수정 시도, 예외 발생")
    void updateNonExistingCustomer() {
        // given
        Customer customer = getetCustomer();
        // when
        assertThatThrownBy(() -> customerRepository.update(customer))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("id를 통한 고객 조회")
    void findByIdExistingCustomerId() {
        // given
        Customer customer = getetCustomer();
        customerRepository.save(customer);
        // when
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
        // then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get())
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("없는 고객 id조회 시, 빈 Optional 반환")
    void findById_NonExistingCustomerId_ReturnsEmptyOptional() {
        UUID noId = UUID.randomUUID();
        Optional<Customer> foundCustomer = customerRepository.findById(noId);
        assertThat(foundCustomer).isEmpty();
    }

    @Test
    @DisplayName("동일한 이름을 가진 회원, 전부 조회")
    void findByNameExistingName() {
        // given
        List<Customer> expectedCustomers = new ArrayList<>();
        String name = "lee";

        Customer customer1 = new Customer(
                        UUID.randomUUID(),
                        name,
                        "lee@example.com",
                        null,
                        LocalDateTime.now());
        expectedCustomers.add(customer1);
        customerRepository.save(customer1);
        Customer customer2 = new Customer(
                UUID.randomUUID(),
                name,
                "lee2@example.com",
                null,
                LocalDateTime.now());
        expectedCustomers.add(customer2);
        customerRepository.save(customer2);

        // when
        List<Customer> foundCustomers = customerRepository.findByName(name);
        //then
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).hasSize(2);
        expectedCustomers.sort(Comparator.comparing(Customer::getEmail));
        foundCustomers.sort(Comparator.comparing(Customer::getEmail));
        assertThat(foundCustomers)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expectedCustomers);
    }

    @Test
    @DisplayName("없는 이름 조회 시, 빈 리스트 반환")
    void findByNameNonExistingName() {
        // given
        String nonExistingName = "NoName";
        // when
        List<Customer> foundCustomers = customerRepository.findByName(nonExistingName);
        // then
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).isEmpty();
    }

    @Test
    @DisplayName("이메일로 고객 조회")
    void findByEmail() {
        // given
        Customer customer = getetCustomer();
        // when
        customerRepository.save(customer);
        Optional<Customer> foundCustomer = customerRepository.findByEmail(customer.getEmail());
        // then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get())
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("없는 이메일 조회 시, 빈 Optional 반환")
    void findByEmailNonExistingEmail() {
        // given
        String nonExistingEmail = "nonMail@example.com";
        // when
        Optional<Customer> foundCustomer = customerRepository.findByEmail(nonExistingEmail);
        // then
        assertThat(foundCustomer).isEmpty();
    }

    @Test
    @DisplayName("모든 고객 조회")
    void findAll() {
        // given
        List<Customer> expectedCustomers = new ArrayList<>();
        Customer customer1 = getetCustomer();
        customerRepository.save(customer1);
        expectedCustomers.add(customer1);

        Customer customer2 = new Customer(
                UUID.randomUUID(),
                "kim",
                "kim@example.com",
                null,
                LocalDateTime.now()
        );
        customerRepository.save(customer2);
        expectedCustomers.add(customer2);

        // when
        List<Customer> foundCustomers = customerRepository.findAll();
        // then
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).hasSize(2);
        expectedCustomers.sort(Comparator.comparing(Customer::getEmail));
        foundCustomers.sort(Comparator.comparing(Customer::getEmail));
        assertThat(foundCustomers)
                .usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(expectedCustomers);
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteById() {

        // given
        Customer customer = getetCustomer();
        // when
        customerRepository.save(customer);
        customerRepository.deleteById(customer.getCustomerId());
        // then
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(foundCustomer).isEmpty();
    }

    @Test
    @DisplayName("없는 고객 Id로 삭제 시, 예외 발생")
    void deleteByIdNonExistingId() {
        UUID nonExistingId = UUID.randomUUID();
        assertThatThrownBy(() -> customerRepository.deleteById(nonExistingId))
                .isInstanceOf(NoSuchElementException.class);
    }
}
