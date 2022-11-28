package com.programmers.customer;

import com.programmers.customer.domain.Customer;
import com.programmers.customer.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
class JdbcCustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        Customer customer1 = new Customer("lee@kakao.com", "1111", "Lee", LocalDateTime.now());
        Customer customer2 = new Customer("dee@kakao.com", "2222", "Dee", LocalDateTime.now());
        Customer customer3 = new Customer("jee@kakao.com", "3333", "Jee", LocalDateTime.now());
        customerRepository.insert(customer1);
        customerRepository.insert(customer2);
        customerRepository.insert(customer3);
    }

    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    void insert() {
        Customer customer = new Customer("super1@gmail.com", "1234", "Sul", LocalDateTime.now());
        customerRepository.insert(customer);

        var retrievedCustomer = customerRepository.findByEmail(customer.getEmail());

        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    void update() {
        String newName = "DongJun";
        customerRepository.update("lee@kakao.com", newName);

        var retrievedCustomer = customerRepository.findByEmail("lee@kakao.com");
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get().getName(), is(newName));
    }

    @Test
    void findAll() {
        var customers = customerRepository.findAll();
        assertThat(customers.size(), is(3));
    }

    @Test
    void findByEmail() {
        String email = "lee@kakao.com";
        var retrievedCustomer = customerRepository.findByEmail(email);
        assertThat(retrievedCustomer.isEmpty(), is(false));
    }

    @Test
    void deleteByEmail() {
        String email = "lee@kakao.com";
        customerRepository.deleteByEmail(email);
        var customers = customerRepository.findAll();
        assertThat(customers.size(), is(2));
    }

    @Test
    void deleteAll() {
        customerRepository.deleteAll();
        var customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }
}