package org.programmers.springbootbasic.customer;

import org.junit.jupiter.api.*;
import org.programmers.springbootbasic.config.DBConfig;
import org.programmers.springbootbasic.customer.model.Customer;
import org.programmers.springbootbasic.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.programmers.springbootbasic.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Autowired
    CustomerRepository jdbcCustomerRepository;

    @BeforeAll
    void setup() {
        dbSetup();
    }

    @AfterEach
    void cleanup() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        jdbcCustomerRepository.insert(customer);

        var retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @Order(2)
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void testFindAll() {
        var customers = jdbcCustomerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("이름으로 고객을 조회 할 수 있다.")
    void testFindByName() {
        Customer customer = new Customer(UUID.randomUUID(), "new-customer", LocalDateTime.now());

        var customers = jdbcCustomerRepository.findByName(customer.getCustomerName());
        assertThat(customers.isEmpty(), is(false));

        var unknown = jdbcCustomerRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }
}
