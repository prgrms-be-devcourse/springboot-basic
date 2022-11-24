package org.prgrms.java.service;

import org.junit.jupiter.api.*;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    private final CustomerService customerService = mock(CustomerService.class);

    @Test
    @DisplayName("서비스를 통해 정상/블랙 유저를 등록할 수 있다.")
    void testCreateCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now(), true);

        customerService.saveCustomer(customer);
        customerService.saveCustomer(blockedCustomer);

        verify(customerService).saveCustomer(customer);
        verify(customerService).saveCustomer(blockedCustomer);
    }

    @Test
    @DisplayName("서비스를 통해 유저를 조회할 수 있다.")
    void testGetCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());

        customerService.saveCustomer(customer);
        Customer insertedCustomer = customerService.getCustomerById(customer.getCustomerId());

        verify(customerService).getCustomerById(customer.getCustomerId());
    }

    @Test
    @DisplayName("존재하지 않는 유저를 조회하면 예외가 발생한다.")
    void testGetNonExistCustomer() {
        when(customerService.getCustomerById(any())).thenThrow(CustomerException.class);

        Assertions.assertThrows(CustomerException.class, () -> customerService.getCustomerById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("유저를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllCustomersWithNoCreation() {
        assertThat(customerService.getAllCustomers(), hasSize(0));
        assertThat(customerService.getAllBlackCustomers(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 유저를 조회할 수 있다.")
    void testGetAllCustomers() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now());

        when(customerService.getAllCustomers())
                .thenReturn(List.of(customer, customer2));

        assertThat(customerService.getAllCustomers(), hasSize(2));
        assertThat(customerService.getAllCustomers(), containsInAnyOrder(samePropertyValuesAs(customer), samePropertyValuesAs(customer2)));
    }
}
