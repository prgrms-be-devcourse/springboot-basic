package org.prgrms.kdt.engine.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.engine.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNamedJdbcRepositoryTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdt.engine.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/order_mgmt")
                .username("root")
                .password("1234")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) { return new JdbcTemplate(dataSource); }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    CustomerNamedJdbcRepository repository;
    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    @BeforeAll
    void setup() {
        repository.deleteAll();
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test00@example.com", LocalDateTime.now());
    }

    @Test
    @Order(1)
    @DisplayName("DataSource는 Hikari이다")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 삽입할 수 있다")
    public void testInsertCustomer() {
        repository.insert(newCustomer);

        var retrievedCustomer = repository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다")
    public void testFindAll() {
        var customerList = repository.findAll();
        assertThat(customerList.isEmpty(), is(false));
        assertThat(customerList.get().size(), is(1));
    }

    @Test
    @Order(4)
    @DisplayName("ID로 고객을 조회할 수 있다")
    public void testFindById() {
        var customer = repository.findById(newCustomer.getCustomerId());
        assertThat(customer.isEmpty(), is(false));
        assertThat(customer.get(), samePropertyValuesAs(newCustomer));

        var unknownCustomer = repository.findById(UUID.randomUUID());
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("이름으로 고객을 조회할 수 있다")
    public void testFindByName() {
        var customer = repository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));
        assertThat(customer.get(), samePropertyValuesAs(newCustomer));

        var unknownCustomer = repository.findByName("unknown-customer");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("이메일로 고객을 조회할 수 있다")
    public void testFindByEmail() {
        var customer = repository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));
        assertThat(customer.get(), samePropertyValuesAs(newCustomer));

        var unknownCustomer = repository.findByEmail("unknown@example.com");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }
}
