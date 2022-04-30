package org.prgms.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository jdbcCustomerRepository;

    private final Customer newCustomer = new Customer(UUID.randomUUID(), "user-test", "user-test@gmail.com");
    private final Customer newCustomer2 = new Customer(UUID.randomUUID(), "user-test2", "user-test2@gmail.com");

    @BeforeEach
    void insertData() {
        jdbcCustomerRepository.save(newCustomer);
        jdbcCustomerRepository.save(newCustomer2);
    }

    @Test
    @DisplayName("모두 조회 기능 테스트")
    void findAllTest() {
        List<Customer> customers = jdbcCustomerRepository.findAll();

        assertThat(customers).containsExactlyInAnyOrder(newCustomer, newCustomer2);
    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByNameTest() {
        List<Customer> customers = jdbcCustomerRepository.findByName("user-test");

        assertThat(customers).extracting(Customer::name).containsExactly("user-test");
    }

    @Test
    @DisplayName("메일로 조회 테스트")
    void findByEmailTest() {
        Optional<Customer> maybeCustomer = jdbcCustomerRepository.findByEmail("user-test2@gmail.com");

        assertThat(maybeCustomer.orElseThrow()).extracting(Customer::email).isEqualTo("user-test2@gmail.com");
    }

    @Test
    @DisplayName("ID로 조회 테스트")
    void findByIdTest() {
        Optional<Customer> maybeCustomer = jdbcCustomerRepository.findById(newCustomer.customerId());

        assertThat(maybeCustomer.orElseThrow()).isEqualTo(newCustomer);
    }

    @Test
    @DisplayName("없는 ID로 조회 테스트")
    void findByNotExistingIdTest() {
        Optional<Customer> maybeCustomer = jdbcCustomerRepository.findById(UUID.randomUUID());

        assertThat(maybeCustomer.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("고객 데이터 insert 테스트")
    void insertTest() {
        Customer customer = new Customer(UUID.randomUUID(), "new-insert", "insert@gmail.com");

        jdbcCustomerRepository.save(customer);
        List<Customer> customers = jdbcCustomerRepository.findByName("new-insert");

        assertThat(customers).containsExactly(customer);
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    public void updateTest() {
        Customer updateUser = new Customer(newCustomer.customerId(), "update-user", newCustomer.email());

        jdbcCustomerRepository.update(updateUser);
        List<Customer> customers = jdbcCustomerRepository.findByName("update-user");

        assertThat(customers).containsExactly(updateUser);
    }
}