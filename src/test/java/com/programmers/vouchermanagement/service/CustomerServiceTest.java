package com.programmers.vouchermanagement.service;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.mock.repository.MockCustomerRepository;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerServiceTest {
    private CustomerService customerService;
    private List<Customer> customerFixtures;

    @BeforeEach
    void setUp() {
        customerFixtures = Arrays.asList(
                new Customer(UUID.fromString("00000000-0000-0000-0000-000000000000"), "철수", true),
                new Customer(UUID.fromString("11111111-1111-1111-1111-111111111111"), "영희", false)
        );
        CustomerRepository customerRepository = new MockCustomerRepository(customerFixtures);

        customerService = new CustomerService(customerRepository);
    }

    @Test
    void blacklist() {
        // given
        // when
        List<Customer> customers = customerService.blacklist();
        // then
        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getId()).isEqualTo((customerFixtures.get(0).getId()));
        assertThat(customers.get(0).getName()).isEqualTo(customerFixtures.get(0).getName());
        assertThat(customers.get(0).isBlacklisted()).isEqualTo(customerFixtures.get(0).isBlacklisted());
    }
}