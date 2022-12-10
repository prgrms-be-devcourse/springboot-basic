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
        Customer customer = createCustomer(UUID.randomUUID());
        when(customerRepository.save(any())).thenReturn(customer);

        Customer insert = customerService.createCustomer("test", "test@gmail.com");

        assertThat(insert, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("서비스를 통해 유저를 조회할 수 있다.")
    void testGetCustomer() {
        Customer customer = createCustomer(UUID.randomUUID());
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
        Customer customer = createCustomer(UUID.randomUUID());
        Customer otherCustomer = createOtherCustomer(UUID.randomUUID());

        when(customerRepository.findAll()).thenReturn(List.of(customer, otherCustomer));

        assertThat(customerService.getAllCustomers(), hasSize(2));
        assertThat(customerService.getAllCustomers(), containsInAnyOrder(samePropertyValuesAs(customer), samePropertyValuesAs(otherCustomer)));
    }

    private Customer createCustomer(UUID customerId) {
        return Customer.builder()
                .customerId((customerId != null) ? customerId : UUID.randomUUID())
                .name("test")
                .email("test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
    }

    private Customer createOtherCustomer(UUID customerId) {
        return Customer.builder()
                .customerId((customerId != null) ? customerId : UUID.randomUUID())
                .name("other-test")
                .email("other-test@gmail.com")
                .createdAt(LocalDateTime.now())
                .isBlocked(false)
                .build();
    }
}
