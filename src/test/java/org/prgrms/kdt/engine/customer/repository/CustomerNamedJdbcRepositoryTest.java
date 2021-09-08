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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@Transactional
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
    CustomerNamedJdbcRepository customerNamedJdbcRepository;
    @Autowired
    DataSource dataSource;

    Customer customer = new Customer(UUID.randomUUID(), "test-user", "test00@example.com", LocalDateTime.now());

    @BeforeEach
    void setup() {
        customerNamedJdbcRepository.insert(customer);
    }

    @Test
    @DisplayName("DataSource는 Hikari이다")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 삽입할 수 있다")
    public void testInsertCustomer() {
        var newCustomer = new Customer(UUID.randomUUID(), "test-user", "test01@example.com", LocalDateTime.now());
        customerNamedJdbcRepository.insert(newCustomer);

        var retrievedCustomer = customerNamedJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다")
    public void testFindAll() {
        var customerList = customerNamedJdbcRepository.findAll();
        assertThat(customerList.isEmpty(), is(false));
        assertThat(customerList.get().size(), is(1));
    }

    @Test
    @DisplayName("ID로 고객을 조회할 수 있다")
    public void testFindById() {
        var customer = customerNamedJdbcRepository.findById(this.customer.getCustomerId());
        assertThat(customer.isEmpty(), is(false));
        assertThat(customer.get(), samePropertyValuesAs(this.customer));

        var unknownCustomer = customerNamedJdbcRepository.findById(UUID.randomUUID());
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다")
    public void testFindByName() {
        var customer = customerNamedJdbcRepository.findByName(this.customer.getName());
        assertThat(customer.isEmpty(), is(false));
        assertThat(customer.get(), samePropertyValuesAs(this.customer));

        var unknownCustomer = customerNamedJdbcRepository.findByName("unknown-customer");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다")
    public void testFindByEmail() {
        var customer = customerNamedJdbcRepository.findByEmail(this.customer.getEmail());
        assertThat(customer.isEmpty(), is(false));
        assertThat(customer.get(), samePropertyValuesAs(this.customer));

        var unknownCustomer = customerNamedJdbcRepository.findByEmail("unknown@example.com");
        assertThat(unknownCustomer.isEmpty(), is(true));
    }
}
