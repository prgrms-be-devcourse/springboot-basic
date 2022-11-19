package org.prgrms.kdt.dao.repository.customer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.dao.entity.customer.Customer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@ExtendWith(MockitoExtension.class)
class JdbcCustomerRepositoryTest {

    @Container
    static MySQLContainer<?> mySQLContainer = (MySQLContainer<?>) new MySQLContainer("mysql:8.0")
            .withDatabaseName("test-order_mgmt")
            .withUsername("test")
            .withPassword("test1234!")
            .withInitScript("init.sql");

    private static CustomerRepository customerRepository;

    private static Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), false);

    @BeforeAll
    public static void beforeAll() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mySQLContainer.getJdbcUrl());
        config.setUsername(mySQLContainer.getUsername());
        config.setPassword(mySQLContainer.getPassword());

        DataSource ds = new HikariDataSource(config);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
        customerRepository = new JdbcCustomerRepository(jdbcTemplate);
    }

    @Test
    @Order(1)
    @DisplayName("customer를 삽입했을 때 반환된 voucher와 id가 같아야 한다.")
    void customer_삽입하기() {
        // when
        Customer insertCustomer = customerRepository.insert(customer);

        // then
        assertThat(insertCustomer.getCustomerId(), is(customer.getCustomerId()));
    }

    @Test
    @Order(2)
    @DisplayName("저장된 모든 바우처를 조회할 수 있다.")
    void 모든_customer_조회하기() {
        // when
        List<Customer> allStoredCustomer = customerRepository.getAllStoredCustomer();

        // then
        assertThat(allStoredCustomer.size(), is(1));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 Id를 이용해서 특정 바우처를 조회할 수 있다.")
    void customerID로_조회하기() {
        // when
        Customer customerById = customerRepository.findById(customer.getCustomerId()).get();

        // then
        assertThat(customerById.getCustomerId(), is(customer.getCustomerId()));
    }

    @Test
    @Order(4)
    @DisplayName("변경된 Customer의 정보를 넣으면 업데이트할 수 있다.")
    void customer_정보_업데이트하기() {
        // given
        customer.setName("leesuyoung");

        // when
        Customer updateCustomer = customerRepository.update(customer);

        // then
        assertThat(updateCustomer, samePropertyValuesAs(customer));
    }
}