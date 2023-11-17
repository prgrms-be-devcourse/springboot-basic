package org.prgrms.kdtspringdemo.customer.service;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("DB")
class CustomerServiceTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/kdt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
    @Autowired
    private CustomerService customerService;

    @Test
    void insert() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "eugene2", false);

        //when
        Customer insertCustomer = customerService.insert(customer);

        //then
        assertThat(customer.getCustomerId(), is(insertCustomer.getCustomerId()));
    }

    @Test
    void findAll() {
    }

    @Test
    void getBlackListCustomers() {
    }
}