package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.repository.util.CustomerDomainMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.programmers.vouchermanagement.util.DomainMapper.ID_KEY;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerJDBCRepositoryTest {
    NamedParameterJdbcTemplate jdbcTemplate;
    CustomerJDBCRepository customerJDBCRepository;

    @Autowired
    CustomerJDBCRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        customerJDBCRepository = new CustomerJDBCRepository(this.jdbcTemplate);
    }

    @Test
    @DisplayName("🆗 고객 정보를 저장할 수 있다.")
    void insert() {
        Customer customer = new Customer(UUID.randomUUID(), "고객4");
        customerJDBCRepository.insert(customer);
        Customer retrievedCustomer = jdbcTemplate
                .queryForObject("SELECT * FROM customers WHERE id = UUID_TO_BIN(:id)", Collections.singletonMap(ID_KEY, customer.getId().toString().getBytes()), CustomerDomainMapper.customerRowMapper);

        assertThat(retrievedCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("🆗 블랙리스트를 조회할 수 있다. 단, 블랙 고객이 없는 경우 빈 list가 반환된다.")
    void findAllBlackCustomer() {
        insertCustomersWithBlackCustomers();
        List<Customer> customers = customerJDBCRepository.findAllBlackCustomer();
        assertThat(customers).isNotEmpty();
        assertThat(customers.size()).isGreaterThanOrEqualTo(1);
    }

    void insertCustomersWithBlackCustomers() {
        customerJDBCRepository.insert(new Customer(UUID.randomUUID(), "고객1", false));
        customerJDBCRepository.insert(new Customer(UUID.randomUUID(), "고객2", true));
        customerJDBCRepository.insert(new Customer(UUID.randomUUID(), "고객3", false));
    }


    @Test
    @DisplayName("🚨 블랙 고객이 없는 경우 빈 list가 반환된다.")
    void findAllBlackCustomerAndReturnEmpty() {
        insertCustomersWithNonBlackCustomers();
        List<Customer> customers = customerJDBCRepository.findAllBlackCustomer();
        assertThat(customers).isEmpty();
    }

    void insertCustomersWithNonBlackCustomers() {
        customerJDBCRepository.insert(new Customer(UUID.randomUUID(), "고객1", false));
        customerJDBCRepository.insert(new Customer(UUID.randomUUID(), "고객2", false));
        customerJDBCRepository.insert(new Customer(UUID.randomUUID(), "고객3", false));
    }
}