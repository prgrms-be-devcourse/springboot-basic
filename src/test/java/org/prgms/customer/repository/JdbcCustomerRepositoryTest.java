package org.prgms.customer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.TestConfig;
import org.prgms.TestContextInitializer;
import org.prgms.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

@SpringJUnitConfig(value = TestConfig.class, initializers = TestContextInitializer.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository jdbcCustomerRepository;

    private final Customer newCustomer = new Customer(UUID.randomUUID(), "user-test", "user-test@gmail.com");

    @BeforeEach
    void deleteAll() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("모두 조회 기능 테스트")
    void findAllTest() {
        jdbcCustomerRepository.insert(newCustomer);
        var anotherCustomer = new Customer(UUID.randomUUID(), "anotherUser", "anonymous@gmail.com");
        jdbcCustomerRepository.insert(anotherCustomer);
        List<Customer> customers = jdbcCustomerRepository.findAll();
        Assertions.assertThat(customers).hasSize(2).contains(anotherCustomer, newCustomer);

    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByNameTest() {
        jdbcCustomerRepository.insert(newCustomer);
        List<Customer> customers = jdbcCustomerRepository.findByName("user-test");
        Assertions.assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("고객 데이터 insert 테스트")
    void inserTest() {
        jdbcCustomerRepository.insert(newCustomer);
        Assertions.assertThat(jdbcCustomerRepository.findByName("user-test").get(0).customerId())
                .isEqualTo(newCustomer.customerId());
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    public void updateTest() {
        jdbcCustomerRepository.insert(newCustomer);
        Customer updateUser = new Customer(newCustomer.customerId(), "update-user", newCustomer.email());
        jdbcCustomerRepository.update(updateUser);
        Assertions.assertThat(
                jdbcCustomerRepository.findByName("update-user")
                        .get(0).customerId()).isEqualTo(newCustomer.customerId());
    }
}