package com.programmers.voucher.repository.customer;

import com.programmers.voucher.entity.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearDB() {
        jdbcTemplate.execute("DELETE FROM vouchers");
        jdbcTemplate.execute("DELETE FROM customers");
    }

    @Test
    @DisplayName("Customer Creation Test")
    void createCustomer() {
        assertTrue(customerRepository.listAll().isEmpty());

        String username = "customer1";
        String alias = "alias-of-customer1";
        final Customer customer = customerRepository.save(new Customer(username, alias));
        assertEquals(customer.getUsername(), username);
        assertEquals(customer.getAlias(), alias);
    }

    @Test
    @DisplayName("Customer Read Test")
    void readCustomer() {
        String username = "username1";
        String alias = "alias1";
        final Customer save = customerRepository.save(new Customer(username, alias));

        final Optional<Customer> byId = customerRepository.findById(save.getId());
        assertTrue(byId.isPresent());
        assertEquals(username, byId.get().getUsername());
        assertEquals(alias, byId.get().getAlias());
    }

}
