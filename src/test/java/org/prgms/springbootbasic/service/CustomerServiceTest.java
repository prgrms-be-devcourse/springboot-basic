package org.prgms.springbootbasic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("dev")
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("insert가 제대로 동작하는지 확인")
    void insertCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "name", "email", LocalDateTime.now());

        when(customerRepository.upsert(any())).thenReturn(customer);

        Customer createdCustomer = customerService.insert("name", "email");

        assertThat(createdCustomer).isEqualTo(customer);

        verify(customerRepository).upsert(any());
    }

    @Test
    @DisplayName("update가 제대로 동작하는지 확인")
    void updateCustomer() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "name", "email", LocalDateTime.now());
        Customer customer2 = new Customer(customerId, "name2", "email2", LocalDateTime.now());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.upsert(any())).thenReturn(customer2);

        Customer updatedCustomer = customerService.update(customerId, "name2", true);

        assertThat(updatedCustomer).isEqualTo(customer2);

        verify(customerRepository).upsert(any());
    }

    @Test
    @DisplayName("findById로 제대로 고객을 가져오는지 확인")
    void findById(){
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "name", "email", LocalDateTime.now());

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> retrievedCustomer = customerService.findById(customerId);
        Optional<Customer> noCustomer = customerService.findById(UUID.randomUUID());

        assertThat(retrievedCustomer.isPresent()).isTrue();
        assertThat(retrievedCustomer.get()).isEqualTo(customer);
        assertThat(noCustomer.isPresent()).isFalse();
    }

    @Test
    @DisplayName("findAll로 모든 고객을 가져오는지 확인")
    void findAllCustomers() {
        when(customerRepository.findAll())
                .thenReturn(List.of(new Customer(UUID.randomUUID(), "c1", "cmail1", LocalDateTime.now()),
                        new Customer(UUID.randomUUID(), "c2", "cmail2", LocalDateTime.now())));
        List<Customer> customers = customerService.findAll();

        assertThat(customers).hasSize(2);

        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("CustomerService 내 deleteAll 동작 확인")
    void deleteAllCustomers() {
        this.customerService.deleteAll();

        verify(customerRepository).deleteAll();
    }

    @Test
    @DisplayName("CustomerService 내 findAllBlack 동작 확인")
    void findAllBlackedCustomers() {
        Customer customer = new Customer(UUID.randomUUID(), "c", "cmail", LocalDateTime.now());
        customer.changeInfo(customer.getName(), true);

        when(customerRepository.findBlackAll()).thenReturn(List.of(customer));

        List<Customer> blackCustomers = customerService.findBlackAll();

        assertThat(blackCustomers).hasSize(1);

        verify(customerRepository).findBlackAll();
    }
}
