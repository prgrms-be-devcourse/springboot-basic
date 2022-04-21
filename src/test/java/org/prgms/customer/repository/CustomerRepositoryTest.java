package org.prgms.customer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.prgms.TestConfig;
import org.prgms.TestContextInitializer;
import org.prgms.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

@SpringJUnitConfig(value = TestConfig.class, initializers = TestContextInitializer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @AfterEach
    void deleteAll() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("모두 조회 기능 테스트")
    void findAllTest() {
        List<Customer> customers = jdbcCustomerRepository.findAll();
        Assertions.assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("이름으로 조회 테스트")
    void findByNameTest() {
        List<Customer> customers = jdbcCustomerRepository.findByName("user-test");
        Assertions.assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("메일로 조회 테스트")
    void findByEmailTest() {
        List<Customer> customers = jdbcCustomerRepository.findByEmail("user-test2@gmail.com");
        Assertions.assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("ID로 조회 테스트")
    void findByIdTest() {
        List<Customer> customers = jdbcCustomerRepository.findById(newCustomer.customerId());
        Assertions.assertThat(customers.get(0).customerId()).isEqualTo(newCustomer.customerId());
    }

    @Test
    @DisplayName("고객 데이터 insert 테스트")
    void insertTest() {
        Customer customer = new Customer(UUID.randomUUID(), "new-insert", "insert@gmail.com");
        jdbcCustomerRepository.save(customer);
        List<Customer> customers = jdbcCustomerRepository.findByName("new-insert");
        Assertions.assertThat(customers).extracting("customerId").contains(customer.customerId());
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    public void updateTest() {
        Customer updateUser = new Customer(newCustomer.customerId(), "update-user", newCustomer.email());
        jdbcCustomerRepository.update(updateUser);
        Assertions.assertThat(
                jdbcCustomerRepository.findByName("update-user")
                        .get(0).customerId()).isEqualTo(newCustomer.customerId());
    }
}