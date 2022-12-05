package org.prgrms.java.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.prgrms.java.repository.customer.CustomerRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("서비스를 통해 정상/블랙 유저를 등록할 수 있다.")
    void testCreateCustomer() {
        String name = "test";
        String email = "test@gmail.com";
        LocalDateTime createdAt = LocalDateTime.now();

        Customer customer = new Customer(UUID.randomUUID(), name, email, createdAt);
        when(customerRepository.insert(any())).thenReturn(customer);

        Customer insertedCustomer = customerService.saveCustomer(name, email);

        assertThat(insertedCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("서비스를 통해 유저를 조회할 수 있다.")
    void testGetCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        Customer testCustomer = customerService.getCustomerById(customer.getCustomerId());

        assertThat(testCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("존재하지 않는 유저를 조회하면 예외가 발생한다.")
    void testGetNonExistCustomer() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerException.class, () -> customerService.getCustomerById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("유저를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllCustomersWithNoCreation() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        assertThat(customerService.getAllCustomers(), hasSize(0));
        assertThat(customerService.getAllBlackCustomers(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 유저를 조회할 수 있다.")
    void testGetAllCustomers() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now());

        when(customerRepository.findAll()).thenReturn(List.of(customer, customer2));

        assertThat(customerService.getAllCustomers(), hasSize(2));
        assertThat(customerService.getAllCustomers(), containsInAnyOrder(samePropertyValuesAs(customer), samePropertyValuesAs(customer2)));
    }
}
