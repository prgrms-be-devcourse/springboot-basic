package org.prgms.customer.repository;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
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
        val customers = jdbcCustomerRepository.findAll();

        Assertions.assertThat(customers).containsExactlyInAnyOrder(newCustomer, newCustomer2);
    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByNameTest() {
        val customers = jdbcCustomerRepository.findByName("user-test");

        Assertions.assertThat(customers).extracting(Customer::name).contains("user-test");
    }

    @Test
    @DisplayName("메일로 조회 테스트")
    void findByEmailTest() {
        val maybeCustomer = jdbcCustomerRepository.findByEmail("user-test2@gmail.com");

        Assertions.assertThat(maybeCustomer.orElseThrow()).extracting(Customer::email).isEqualTo("user-test2@gmail.com");
    }

    @Test
    @DisplayName("ID로 조회 테스트")
    void findByIdTest() {
        val maybeCustomer = jdbcCustomerRepository.findById(newCustomer.customerId());

        Assertions.assertThat(maybeCustomer.orElseThrow()).isEqualTo(newCustomer);
    }

    @Test
    @DisplayName("고객 데이터 insert 테스트")
    void insertTest() {
        val customer = new Customer(UUID.randomUUID(), "new-insert", "insert@gmail.com");

        jdbcCustomerRepository.save(customer);
        val customers = jdbcCustomerRepository.findByName("new-insert");

        Assertions.assertThat(customers).contains(customer);
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    public void updateTest() {
        val updateUser = new Customer(newCustomer.customerId(), "update-user", newCustomer.email());

        jdbcCustomerRepository.update(updateUser);
        var customers = jdbcCustomerRepository.findByName("update-user");

        Assertions.assertThat(customers).contains(updateUser);
    }
}