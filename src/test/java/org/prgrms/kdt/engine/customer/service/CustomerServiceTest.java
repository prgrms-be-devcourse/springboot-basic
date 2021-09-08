package org.prgrms.kdt.engine.customer.service;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.engine.customer.domain.Customer;
import org.prgrms.kdt.engine.customer.repository.CustomerNamedJdbcRepository;
import org.prgrms.kdt.engine.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@EnableTransactionManagement
class CustomerServiceTest {
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
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerNamedJdbcRepository(jdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerService(customerRepository);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService customerService;

    @BeforeEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객 리스트를 insert 할 수 있다")
    public void testCreateCustomers() {
        var customerList = List.of(
            new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now())
        );

        customerService.createCustomers(customerList);
        var retrievedAllCustomers = customerRepository.findAll();
        assertThat(retrievedAllCustomers.isEmpty(), is(false));
        assertThat(retrievedAllCustomers.get().size(), is(2));
        assertThat(retrievedAllCustomers.get(),
            containsInAnyOrder(
                samePropertyValuesAs(customerList.get(0)),
                samePropertyValuesAs(customerList.get(1))
            ));
    }

    @Test
    @DisplayName("고객 리스트 insert 실패시 롤백한다")
    public void testCreateCustomersRollback() {
        //
        var customerList = List.of(
            new Customer(UUID.randomUUID(), "c", "c@gmail.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "d", "c@gmail.com", LocalDateTime.now())
        );

        try {
            customerService.createCustomers(customerList);
        } catch (DataAccessException ignored) {}

        var retrievedAllCustomers = customerRepository.findAll();
        assertThat(retrievedAllCustomers.isEmpty(), is(false));
        assertThat(retrievedAllCustomers.get().isEmpty(), is(true));
        assertThat(retrievedAllCustomers.get().size(), is(0));
    }
}
