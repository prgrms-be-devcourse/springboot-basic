package org.prgrms.kdt.service.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class CustomerServiceTest {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.26")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("root1234!");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();

        Customer customer1 = new Customer(UUID.randomUUID(), "길동", CustomerType.NORMAL);
        Customer customer2 = new Customer(UUID.randomUUID(), "둘리", CustomerType.BLACK_LIST);
        Customer customer3 = new Customer(UUID.randomUUID(), "또치", CustomerType.BLACK_LIST);

        customerRepository.insert(customer1);
        customerRepository.insert(customer2);
        customerRepository.insert(customer3);
    }

    @Test
    @DisplayName("고객들 모두 조회된다.")
    void testFindAll() {
        List<Customer> customers = customerService.findAll();
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers.size(), is(3));
    }

    @Test
    @DisplayName("고객 유형으로 조회된다.")
    void testFindByCustomerType() {
        List<Customer> blackList = customerService.findAllByCustomerType(CustomerType.BLACK_LIST);
        assertThat(blackList.isEmpty(), is(false));
        assertThat(blackList.size(), is(2));

        List<Customer> normalCustomer = customerService.findAllByCustomerType(CustomerType.NORMAL);
        assertThat(normalCustomer.isEmpty(), is(false));
        assertThat(normalCustomer.size(), is(1));
    }

    @Test
    @DisplayName("고객 유형으로 조회 실패한다.")
    void testError_FindByCustomerType() {
        assertThrows(IllegalArgumentException.class, () -> {
            customerService.findAllByCustomerType(null);
        });
    }
}