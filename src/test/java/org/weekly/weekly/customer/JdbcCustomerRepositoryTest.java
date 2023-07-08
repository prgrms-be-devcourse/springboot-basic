package org.weekly.weekly.customer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.customer.repository.JdbcCustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest
class JdbcCustomerRepositoryTest {
    @Autowired
    private JdbcCustomerRepository jdbcCustomerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.of(UUID.randomUUID(), "tester", "test@naver.com");
    }

    @AfterEach
    void deleteCustomer() {
        assertThatCode(() -> jdbcCustomerRepository.deleteByEmail(customer.getEmail()));
    }

    @Test
    void 전체_회원_검색_테스트() {
        // Given
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);

        // When + Then
        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    void 회원_등록성공_테스트() {
        // Given
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);

        // When
        Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail(insertCustomer.getEmail());

        // Then
        assertThat(findCustomer.isEmpty(), is(false));
        assertThat(findCustomer.get(), samePropertyValuesAs(insertCustomer));
    }

    @Test
    void 회원_등록실패_테스트() {
        // Given
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);
        Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail(insertCustomer.getEmail());

        // When + Then
        assertThatThrownBy(()->jdbcCustomerRepository.insert(customer)).isInstanceOf(CustomerException.class);
    }



    @Test
    void 이메일_회원_검색_성공_테스트() {
        // Given
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);

        // When
        Optional<Customer> customers = jdbcCustomerRepository.findByEmail(insertCustomer.getEmail());

        // Then
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    void 이메일_회원_검색_실패_테스트() {
        Optional<Customer> customers = jdbcCustomerRepository.findByEmail(customer.getEmail());
        assertThat(customers.isEmpty(), is(true));
    }

    @Test
    void 회원_삭제_성공_테스트() {
        // Given
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);
        Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail(insertCustomer.getEmail());
        assertThat(findCustomer.isPresent(), is(true));

        // When
        jdbcCustomerRepository.deleteByEmail(findCustomer.get().getEmail());

        // THen
        Optional<Customer> deleteCustomer = jdbcCustomerRepository.findByEmail(insertCustomer.getEmail());
        assertThat(deleteCustomer.isEmpty(), is(true));
    }

    @Test
    void 회원_삭제_실패_테스트() {
        // when
        jdbcCustomerRepository.deleteByEmail(customer.getEmail());

    }

    @Test
    void 전체_회원_삭제_테스트() {
        // Given
        jdbcCustomerRepository.deleteAll();

        List<Customer> customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }

    @Test
    void 회원_닉네임_업데이트_테스트() {
        // Given
        Customer insertCusomter = jdbcCustomerRepository.insert(customer);

        // When
        insertCusomter.updateName("newName");
        jdbcCustomerRepository.update(insertCusomter);

        // Then
        Optional<Customer> updateCustomer = jdbcCustomerRepository.findByEmail(insertCusomter.getEmail());
        assertThat(updateCustomer.isPresent(), is(true));
        assertThat(updateCustomer.get(), samePropertyValuesAs(insertCusomter));
    }
}
