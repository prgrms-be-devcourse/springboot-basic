package org.prgrms.kdt.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {

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

    Customer newCustomer = new Customer(UUID.randomUUID(), "길동", CustomerType.NORMAL);

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객이 등록된다.")
    void testInsert() {
        Customer savedCustomer = customerRepository.insert(newCustomer);
        assertThat(savedCustomer, samePropertyValuesAs(newCustomer));

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers.get(0), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("고객 유형으로 조회된다.")
    void testFindByCustomerType() {
        Customer normalCustomer = new Customer(UUID.randomUUID(), "길동", CustomerType.NORMAL);
        Customer blackCustomer = new Customer(UUID.randomUUID(), "둘리", CustomerType.BLACK_LIST);

        customerRepository.insert(normalCustomer);
        customerRepository.insert(blackCustomer);

        List<Customer> blackList = customerRepository.findAllByCustomerType(CustomerType.BLACK_LIST);
        assertThat(blackList.isEmpty(), is(false));
        assertThat(blackList.get(0), samePropertyValuesAs(blackCustomer));
    }
}