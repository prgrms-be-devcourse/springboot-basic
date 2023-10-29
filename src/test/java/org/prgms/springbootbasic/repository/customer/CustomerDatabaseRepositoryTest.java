package org.prgms.springbootbasic.repository.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("dev")
class CustomerDatabaseRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer setUpCustomer;

    @BeforeEach
    void setUp() {
        setUpCustomer = new Customer(UUID.randomUUID(),
                "test",
                "test@gmail.com",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        customerRepository.save(setUpCustomer);
    }

    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    void insertNewCustomerInDB() {
        Customer customer = new Customer(UUID.randomUUID(),
                "test2",
                "test2@gmail.com",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        customerRepository.save(customer);
        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), is(samePropertyValuesAs(customer)));
    }

    @Test
    void updateExistingCustomerInDB() {
        Customer customer = customerRepository.findById(setUpCustomer.getCustomerId()).orElseThrow(EntityNotFoundException::new);

        Customer changedCustomer = customer.changeInfo("updated", customer.isBlacked());

        customerRepository.save(changedCustomer);

        Optional<Customer> retrievedCustomer = customerRepository.findById(setUpCustomer.getCustomerId());

        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(changedCustomer));
    }

    @Test
    void findCustomerByCustomerIdInDB() {
        Optional<Customer> retrievedCustomer = customerRepository.findById(setUpCustomer.getCustomerId());
        Optional<Customer> notExistingCustomer = customerRepository.findById(UUID.randomUUID());

        assertThat(notExistingCustomer.isPresent(), is(false));
        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(setUpCustomer));
    }

    @Test
    void findCustomerByEmailInDB() {
        Optional<Customer> retrievedCustomer = customerRepository.findByEmail(setUpCustomer.getEmail());
        Optional<Customer> notExistingCustomer = customerRepository.findByEmail("blahblah@naver.com");

        assertThat(notExistingCustomer.isPresent(), is(false));
        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(setUpCustomer));
    }

    @Test
    void findAllCustomersInDB(){
        customerRepository.save(new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now()));

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers, hasSize(2));
    }

    @Test
    void findAllBlackedCustomersInDB() {
        Customer blackCustomer = new Customer(UUID.randomUUID(),
                "black",
                "black@gmail.com",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        blackCustomer.changeInfo(blackCustomer.getName(), true);
        customerRepository.save(blackCustomer);

        List<Customer> blackList = customerRepository.findBlackAll();

        assertThat(blackList, hasSize(1));
    }

    @Test
    void deleteCustomerByCustomerIdInDB() {
        customerRepository.deleteById(setUpCustomer.getCustomerId());

         var deletedCustomer = customerRepository.findById(setUpCustomer.getCustomerId());

        assertThat(deletedCustomer.isPresent(), is(false));
    }

    @Test
    void deleteAllCustomersInDB() {
        customerRepository.save(new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now()));

        customerRepository.deleteAll();

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers, hasSize(0));
    }
}
