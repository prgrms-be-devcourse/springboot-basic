package com.programmers.vouchermanagement.customer.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.programmers.vouchermanagement.configuration.TestConfig;
import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig(TestConfig.class)
@Testcontainers(disabledWithoutDocker = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class JdbcCustomerRepositoryTest {
    @Container
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withUsername("test")
            .withPassword("1234")
            .withInitScript("init.sql");

    DataSource dataSource;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    CustomerRepository customerRepository;
    @Autowired
    FileProperties fileProperties;

    @BeforeAll
    void setUp() {
        dataSource = DataSourceBuilder.create()
                .url(mySQLContainer.getJdbcUrl())
                .username(mySQLContainer.getUsername())
                .password(mySQLContainer.getPassword())
                .type(HikariDataSource.class)
                .build();

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        customerRepository = new JdbcCustomerRepository(namedParameterJdbcTemplate, fileProperties);
    }

    @Test
    @Order(1)
    @DisplayName("테스트 컨테이너 생성을 성공한다.")
    void testMySQLContainerRunning() {
        assertThat(mySQLContainer.isRunning(), is(true));
        assertThat(mySQLContainer.getUsername(), is("test"));
    }

    @Test
    @Order(2)
    @DisplayName("JdbcCustomerRepository가 템플릿을 주입 받아 생성된다.")
    void testJdbcCustomerRepositoryCreation() {
        assertThat(customerRepository, notNullValue());
    }

    @Test
    @DisplayName("고객 저장을 성공한다.")
    void testCustomerCreationSuccessful() {
        //given
        final Customer customer = new Customer(UUID.randomUUID(), "test-user");

        //when
        final Customer createdCustomer = customerRepository.save(customer);

        //then
        assertThat(createdCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("저장된 블랙리스트 csv파일을 성공적으로 읽고 저장 후, 조회를 성공한다.")
    void testLoadingBlacklistFileOnInit() {
        assertThat(fileProperties.getCSVCustomerFilePath(), is("src/test/resources/blacklist-test.csv"));

        //when
        final List<Customer> blacklist = customerRepository.findBlackCustomers();

        //then
        assertThat(blacklist, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("저장된 고객이 있으면 고객들의 리스트를 반환한다.")
    void testFindCustomersSuccessful_ReturnList() {
        //given
        final Customer firstCustomer = new Customer(UUID.randomUUID(), "first-customer");
        final Customer secondCustomer = new Customer(UUID.randomUUID(), "second-customer");
        customerRepository.save(firstCustomer);
        customerRepository.save(secondCustomer);

        //when
        final List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.isEmpty(), is(false));
        assertThat(customers, hasSize(greaterThanOrEqualTo(1)));
    }
}
