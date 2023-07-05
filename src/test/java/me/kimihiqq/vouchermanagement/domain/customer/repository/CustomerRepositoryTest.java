package me.kimihiqq.vouchermanagement.domain.customer.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.dto.CustomerDto;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerDto customerDto1;
    private CustomerDto customerDto2;

    @BeforeEach
    void setUp() {
        customerDto1 = new CustomerDto("Test User1", "user1@test.com", CustomerStatus.WHITE);
        customerDto2 = new CustomerDto("Test User2", "user2@test.com", CustomerStatus.WHITE);
    }

    @AfterEach
    void tearDown() {
        customerRepository.findAll().forEach(customer ->
                customerRepository.deleteById(customer.getId())
        );
    }

    @Test
    void saveCustomer() {
        Customer savedCustomer = customerRepository.save(customerDto1.toCustomer());
        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomer.getId());

        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(savedCustomer));
    }

    @Test
    void findCustomerById() {
        Customer savedCustomer = customerRepository.save(customerDto1.toCustomer());
        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomer.getId());

        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(savedCustomer));
    }

    @Test
    void findAllCustomers() {
        Customer savedCustomer1 = customerRepository.save(customerDto1.toCustomer());
        Customer savedCustomer2 = customerRepository.save(customerDto2.toCustomer());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers, containsInAnyOrder(
                Matchers.samePropertyValuesAs(savedCustomer1),
                Matchers.samePropertyValuesAs(savedCustomer2)
        ));
    }

    @Test
    void customerNotFound() {
        Optional<Customer> retrievedCustomer = customerRepository.findById(UUID.randomUUID());

        assertThat(retrievedCustomer.isPresent(), is(false));
    }
}