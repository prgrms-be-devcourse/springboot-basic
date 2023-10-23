package org.prgrms.kdtspringdemo.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {
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
    private JdbcCustomerRepository jdbcCustomerRepository;

    @Autowired
    DataSource dataSource;

    @BeforeAll
    void init() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    void insert() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "tester01", true);

        //when
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);

        //then
        assertThat(customer, samePropertyValuesAs(insertCustomer));
    }

    @Test
    void getAllBlackList() throws IOException {
        //given

        //when
        List<Customer> customerList = jdbcCustomerRepository.getAllBlackList().get();

        //then
        assertThat(customerList.size(), is(1));
    }
}