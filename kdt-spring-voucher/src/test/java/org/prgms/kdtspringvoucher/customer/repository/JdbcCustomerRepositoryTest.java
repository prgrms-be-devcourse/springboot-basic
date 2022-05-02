package org.prgms.kdtspringvoucher.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    @Configuration
    @ComponentScan
    static class testConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/test_db")
                    .username("root")
                    .password("a123456789")
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
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void cleanup(){
        customerRepository.deleteAll();
    }

    @Test
    void save() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@email.com", CustomerType.BASIC, LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer.getCustomerId(), is(customer.getCustomerId()));
    }

    @Test
    @DisplayName("이메일과 고객 타입을 수정할 수 있다.")
    void update() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@email.com", CustomerType.BASIC, LocalDateTime.now());
        customerRepository.save(customer);

        customer.changeName("update_name");
        customer.changeCustomerType(CustomerType.BLACKLIST);
        Customer updatedCustomer = customerRepository.update(customer);

        assertThat(updatedCustomer.getName(),is("update_name"));
        assertThat(updatedCustomer.getCustomerType(), is(CustomerType.BLACKLIST));
    }

    @Test
    @DisplayName("고객 Id로 조회할 수 있다.")
    void findById() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@email.com", CustomerType.BASIC, LocalDateTime.now());
        customerRepository.save(customer);

        Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(findCustomer.isPresent(), is(true));
        assertThat(findCustomer.get().getCustomerId(), is(customer.getCustomerId()));
    }

    @Test
    @DisplayName("고객 email로 조회할 수 있다.")
    void findByEmail() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@email.com", CustomerType.BASIC, LocalDateTime.now());
        customerRepository.save(customer);

        Optional<Customer> findCustomer = customerRepository.findByEmail(customer.getEmail());

        assertThat(findCustomer.isPresent(), is(true));
        assertThat(findCustomer.get().getCustomerId(), is(customer.getCustomerId()));
    }


    @Test
    @DisplayName("고객 타입으로 조회할 수 있다.")
    void findBlackListByCustomerType() {
        Customer basicCustomer = new Customer(UUID.randomUUID(), "basic", "basic@email.com", CustomerType.BASIC, LocalDateTime.now());
        Customer blacklistCustomer = new Customer(UUID.randomUUID(), "blacklist", "black@email.com", CustomerType.BLACKLIST, LocalDateTime.now());
        customerRepository.save(basicCustomer);
        customerRepository.save(blacklistCustomer);

        List<Customer> findBlacklist = customerRepository.findByCustomerType(CustomerType.BLACKLIST);
        List<Customer> findBasicCustomers = customerRepository.findByCustomerType(CustomerType.BASIC);

        assertThat(findBasicCustomers.isEmpty(), is(false));
        assertThat(findBasicCustomers.size(), is(1));

        assertThat(findBlacklist.isEmpty(), is(false));
        assertThat(findBlacklist.size(), is(1));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    void findAll() {
        Customer basicCustomer = new Customer(UUID.randomUUID(), "basic", "basic@email.com", CustomerType.BASIC, LocalDateTime.now());
        Customer blacklistCustomer = new Customer(UUID.randomUUID(), "blacklist", "black@email.com", CustomerType.BLACKLIST, LocalDateTime.now());
        customerRepository.save(basicCustomer);
        customerRepository.save(blacklistCustomer);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.isEmpty(), is(false));
        assertThat(customers.size(), is(2));
    }

    @Test
    @DisplayName("해당 Id를 가진 고객을 삭제할 수 있다.")
    void deleteById() {
        Customer basicCustomer = new Customer(UUID.randomUUID(), "basic", "basic@email.com", CustomerType.BASIC, LocalDateTime.now());
        Customer blacklistCustomer = new Customer(UUID.randomUUID(), "blacklist", "black@email.com", CustomerType.BLACKLIST, LocalDateTime.now());
        customerRepository.save(basicCustomer);
        customerRepository.save(blacklistCustomer);

        customerRepository.deleteById(basicCustomer.getCustomerId());
        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.isEmpty(), is(false));
        assertThat(customers.size(), is(1));
    }
}