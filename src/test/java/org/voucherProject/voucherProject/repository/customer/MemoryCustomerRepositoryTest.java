package org.voucherProject.voucherProject.repository.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.voucherProject.voucherProject.entity.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoryCustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    UUID id;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        Customer customer = new Customer(UUID.randomUUID(), "aaa", "aaa@.com", "1234");
        Customer save = customerRepository.save(customer);
        id = customer.getCustomerId();
    }

    @Test
    void findById() {
        Optional<Customer> findById = customerRepository.findById(id);
        assertThat(findById.isPresent()).isTrue();
    }

    @Test
    void findByName() {
        Optional<Customer> byName = customerRepository.findByName("aaa");
        assertThat(byName.isPresent()).isTrue();
    }

    @Test
    void findByEmail() {
        Optional<Customer> byEmail = customerRepository.findByEmail("aaa@.com");
        assertThat(byEmail.isPresent()).isTrue();
    }

    @Test
    void findAll() {
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }
}