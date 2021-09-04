package com.programmers.voucher.service.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class BasicCustomerSeviceTest {

    @Autowired
    CustomerService basicCustomerService; // currently customer service uses jdbc repository as default.

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("DELETE FROM customers");
    }

    @Test
    @DisplayName("Customer Creation Test")
    void createCustomer() {
        // TODO: mock object to replace repository?
        String username = "usernameCreate";
        String alias = "aliasCreate";
        final Customer customer = basicCustomerService.create(username, alias);
        assertEquals(username, customer.getUsername());
        assertEquals(alias, customer.getAlias());
    }

    @Test
    @DisplayName("Customer Read Test")
    void readCustomer() {
        String username = "usernameRead";
        String alias = "aliasRead";
        final Customer customer = basicCustomerService.create(username, alias);

        final Optional<Customer> byId = basicCustomerService.findById(customer.getId());
        assertTrue(byId.isPresent());
        assertEquals(username, byId.get().getUsername());
        assertEquals(alias, byId.get().getAlias());
    }

    @Test
    @DisplayName("Customer List Test")
    void listAllCustomers() {
        String username = "usernameList";
        String alias = "usernameAlias";
        List<Customer> list = new ArrayList<>(10);
        for(int i=0;i<10;i++) {
            list.add(basicCustomerService.create(username + i, alias + i));
        }

        final List<Customer> customers = basicCustomerService.listAll();
        assertEquals(10, customers.size());
        for(int i=0;i<10;i++) {
            assertEquals(list.get(i).getUsername(), customers.get(i).getUsername());
            assertEquals(list.get(i).getAlias(), customers.get(i).getAlias());
        }
    }
}
