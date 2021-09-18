package org.programmers.customer.service;

import org.junit.jupiter.api.*;
import org.programmers.customer.model.Customer;
import org.programmers.customer.repository.CustomerJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.customer.service"})
    static class Config {
        @Bean
        CustomerService customerService(CustomerJdbcRepository customerJdbcRepository) {
            return new CustomerService(customerJdbcRepository);
        }
    }

    @Autowired
    CustomerService customerService;

    Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer(UUID.randomUUID(), "test", "test10@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerService.create(testCustomer);
    }

    @AfterEach
    void clearUp() {
        customerService.deleteAll();
    }

    @Test
    @DisplayName("고객을 전부 조회할 수 있다.")
    @Order(1)
    void getCustomerList() {
        List<Customer> customerList = customerService.getCustomerList();
        assertThat(customerList, hasSize(1));
        assertThat(customerList.isEmpty(), is(false));
    }

    @Test
    @DisplayName("고객을 생성할 수 있다.")
    @Order(2)
    void create() {
        Customer createTestCustomer = new Customer(UUID.randomUUID(), "craeteTest", "createTest@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerService.create(createTestCustomer);
        Customer byEmail = customerService.findByEmail(createTestCustomer.getEmail());

        assertThat(byEmail, samePropertyValuesAs(createTestCustomer));
    }

    @Test
    @DisplayName("고객을 이메일로 찾을 수 있다.")
    @Order(3)
    void findByEmail() {
        Customer findCustomerByEmail = customerService.findByEmail(testCustomer.getEmail());
        assertThat(findCustomerByEmail, samePropertyValuesAs(testCustomer));
    }

    @Test
    @DisplayName("고객을 이메일로 찾지 못할 경우 예외처리된다.")
    @Order(4)
    void findByEmailException() {
        assertThrows(EmptyResultDataAccessException.class, () -> customerService.findByEmail("no-user@gmail.com"));
    }

    @Test
    @DisplayName("고객 이름을 수정할 수 있다.")
    @Order(5)
    void update() {
        String toChangeName = "change_test";
        customerService.update(testCustomer.getEmail(), toChangeName);
        Customer findCustomerByEmail = customerService.findByEmail(testCustomer.getEmail());
        assertEquals(findCustomerByEmail.getName(), toChangeName);
    }

    @Test
    @DisplayName("고객을 이메일을 통해 삭제할 수 있다.")
    @Order(6)
    void deleteByEmail() {
        Customer toDeleteCustomer = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerService.create(toDeleteCustomer);
        List<Customer> customerList1 = customerService.getCustomerList();
        assertThat(customerList1, hasSize(2));

        customerService.deleteByEmail(testCustomer.getEmail());
        List<Customer> customerList2 = customerService.getCustomerList();
        assertThat(customerList2, hasSize(1));
    }

    @Test
    @DisplayName("고객을 이메일로 삭제하지 못하면 예외처리가 된다.")
    @Order(7)
    void deleteByEmailException() {
        assertThrows(RuntimeException.class, () -> customerService.deleteByEmail("no-user"));
    }

    @Test
    @DisplayName("고객을 전부 삭제할 수 있다.")
    @Order(8)
    void deleteAll() {
        customerService.deleteAll();
        List<Customer> customerList = customerService.getCustomerList();
        assertThat(customerList, hasSize(0));
    }
}