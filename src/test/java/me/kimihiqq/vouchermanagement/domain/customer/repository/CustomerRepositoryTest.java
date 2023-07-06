package me.kimihiqq.vouchermanagement.domain.customer.repository;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.dto.CustomerDto;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
        customerDto1 = new CustomerDto("Customer1", "customer1@test.com", CustomerStatus.WHITE);
        customerDto2 = new CustomerDto("Customer2", "customer2@test.com", CustomerStatus.WHITE);
    }

    @AfterEach
    void tearDown() {
        customerRepository.findAll().forEach(customer ->
                customerRepository.deleteById(customer.getId())
        );
    }

    @Test
    @DisplayName("고객 정보 저장이 가능하다")
    void saveCustomer() {
        // given
        Customer savedCustomer = customerRepository.save(customerDto1.toCustomer());
        // when
        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomer.getId());
        // then
        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(savedCustomer));
    }

    @Test
    @DisplayName("고객 ID로 고객을 찾을 수 있다")
    void findCustomerById() {
        // given
        Customer savedCustomer = customerRepository.save(customerDto1.toCustomer());
        // when
        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomer.getId());
        // then
        assertThat(retrievedCustomer.isPresent(), is(true));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(savedCustomer));
    }

    @Test
    @DisplayName("모든 고객을 찾을 수 있다")
    void findAllCustomers() {
        // given
        Customer savedCustomer1 = customerRepository.save(customerDto1.toCustomer());
        Customer savedCustomer2 = customerRepository.save(customerDto2.toCustomer());
        // when
        List<Customer> customers = customerRepository.findAll();
        // then
        assertThat(customers, containsInAnyOrder(
                Matchers.samePropertyValuesAs(savedCustomer1),
                Matchers.samePropertyValuesAs(savedCustomer2)
        ));
    }

    @Test
    @DisplayName("존재하지 않는 고객을 찾으려고 시도하면 결과가 없다")
    void customerNotFound() {
        // when
        Optional<Customer> retrievedCustomer = customerRepository.findById(UUID.randomUUID());
        // then
        assertThat(retrievedCustomer.isPresent(), is(false));
    }
}