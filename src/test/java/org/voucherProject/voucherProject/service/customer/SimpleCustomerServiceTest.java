package org.voucherProject.voucherProject.service.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.customer.service.CustomerService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SimpleCustomerServiceTest {

    @Autowired
    CustomerService customerService;

    UUID customerId;
    Customer saveCustomer;
    @BeforeEach
    void setUp() {
        customerService.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@.com", "1234");
        saveCustomer = customerService.save(customer);
        customerId = saveCustomer.getCustomerId();
    }

    @Test
    void findById() {
        Customer byId = customerService.findById(customerId);
        assertThat(byId).isEqualTo(saveCustomer);
    }

    @Test
    void findByName() {
        Customer byName = customerService.findByName("aaa");
        assertThat(byName).isEqualTo(saveCustomer);
    }

    @Test
    void findByEmail() {
        Customer byEmail = customerService.findByEmail("aaa@.com");
        assertThat(byEmail).isEqualTo(saveCustomer);
    }

    @Test
    void findAll() {
        List<Customer> all = customerService.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}