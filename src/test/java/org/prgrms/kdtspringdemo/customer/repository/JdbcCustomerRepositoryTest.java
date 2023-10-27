package org.prgrms.kdtspringdemo.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@EnableAutoConfiguration
@ActiveProfiles("DB")
class JdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan()
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

    @BeforeEach
    void init() {
        jdbcCustomerRepository.deleteAll();
    }

    @Test
    @DisplayName("데이터베이스에 고객을 추가합니다.")
    void insert() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "eugene", true);

        //when
        Customer insertCustomer = jdbcCustomerRepository.insert(customer);

        //then
        assertThat(customer, samePropertyValuesAs(insertCustomer));
    }

    @Test
    @DisplayName("모든 블랙리스트 목록을 반환합니다.")
    void getAllBlackList() throws IOException {
        //given
        jdbcCustomerRepository.insert(new Customer(UUID.randomUUID(), "tester01", true));
        jdbcCustomerRepository.insert(new Customer(UUID.randomUUID(), "tester02", true));

        //when
        List<Customer> customerList = jdbcCustomerRepository.getAllBlackList().get();

        //then
        assertThat(customerList.size(), is(2));
    }

    @Test
    @DisplayName("고객이 없는 경우 NPE 발생 X")
    void getAllCustomersNull() throws IOException {
        //given
        //none

        //when
        List<Customer> customerList = jdbcCustomerRepository.getAllBlackList().get();

        //then
        assertThat(customerList.size(), is(0));
    }
}