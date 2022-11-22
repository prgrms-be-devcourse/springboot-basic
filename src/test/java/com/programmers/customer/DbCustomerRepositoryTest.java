package com.programmers.customer;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DbCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    Customer newCustomer;
    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "Albatross@gmail.com", "1234", "Dee", LocalDateTime.now());
        customerRepository.insert(newCustomer);
    }


    @Test
    @Order(1)
    void insert() {
        Customer customer1 = new Customer(UUID.randomUUID(), "super@gmail.com", "1234", "Lee", LocalDateTime.now());
        customerRepository.insert(customer1);
        Customer customer2 = new Customer(UUID.randomUUID(), "knight@gmail.com", "1234", "Jee", LocalDateTime.now());
        customerRepository.insert(customer2);

        var retrievedCustomer1 = customerRepository.findById(customer1.getCustomerId());
        var retrievedCustomer2 = customerRepository.findById(customer2.getCustomerId());

        assertThat(retrievedCustomer1.isEmpty(), is(false));
        assertThat(retrievedCustomer1.get(), samePropertyValuesAs(customer1));

        assertThat(retrievedCustomer2.isEmpty(), is(false));
        assertThat(retrievedCustomer2.get(), samePropertyValuesAs(customer2));
    }

    @Test
    @Order(2)
    void findAll() {
        var customers = customerRepository.findAll();
        assertThat(customers.size(), is(3));
    }

    @Test
    @Order(3)
    void findByEmail() {
        var retrievedCustomer = customerRepository.findByEmail(newCustomer.getEmail());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));

    }

    @Test
    @Order(4)
    void update() {
        newCustomer.changeName("Xee");
        var customer = customerRepository.update(newCustomer);
        assertThat(newCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @Order(5)
    void deleteAll() {
        customerRepository.deleteAll();
        var customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }
}