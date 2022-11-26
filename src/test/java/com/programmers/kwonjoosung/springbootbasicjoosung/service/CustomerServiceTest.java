package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private final CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
    private final CustomerService customerService = new CustomerService(customerRepositoryMock);

    @Test
    @DisplayName("[성공] 고객을 생성할 수 있다")
    void createCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        String customerName = "test1";
        Customer customer = new Customer(customerId, customerName);
        Optional<Customer> createdCustomer;
        //when
        try (MockedStatic<UUID> utilities = mockStatic(UUID.class)) {
            utilities.when(UUID::randomUUID).thenReturn(customerId);
            when(customerRepositoryMock.insert(customer)).thenReturn(true);
            createdCustomer = customerService.createCustomer(customerName);
        }
        //then
        assertThat(createdCustomer).isNotEmpty();
        assertThat(createdCustomer.get()).isEqualTo(customer);
        verify(customerRepositoryMock).insert(customer);
    }

    @Test
    @DisplayName("[실패] 동일한 id의 고객을 생성할 수 없다")
    void createAlreadyExistCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test2");
        when(customerRepositoryMock.insert(customer)).thenReturn(false);
        Optional<Customer> createdCustomer;
        //when
        try (MockedStatic<UUID> utilities = mockStatic(UUID.class)) {
            utilities.when(UUID::randomUUID).thenReturn(customer.getCustomerId());
            createdCustomer = customerService.createCustomer(customer.getName());
        }
        //then
        assertThat(createdCustomer).isEmpty();
        verify(customerRepositoryMock).insert(customer);
    }

    @Test
    @DisplayName("[성공] 고객을 Id로 조회할 수 있다")
    void findCustomerByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test3");
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));
        //when
        Optional<Customer> foundCustomerById = customerService.findCustomerByCustomerId(customerId);
        //then
        assertThat(foundCustomerById).isNotEmpty();
        assertThat(foundCustomerById.get()).isEqualTo(customer);
        verify(customerRepositoryMock).findById(customerId);
    }

    @Test
    @DisplayName("[실패] 고객테이블에 없는 고객은 Id로 조회할 수 없다")
    void findNotExistCustomerByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
        //when
        Optional<Customer> foundCustomerById = customerService.findCustomerByCustomerId(customerId);
        //then
        assertThat(foundCustomerById).isEmpty();
        verify(customerRepositoryMock).findById(customerId);
    }

    @Test
    @DisplayName("[성공] 모든 고객을 조회할 수 있다")
    void findAllCustomers() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        Customer customer1 = new Customer(customerId1, "test4");
        Customer customer2 = new Customer(customerId2, "test5");
        when(customerRepositoryMock.findAll()).thenReturn(List.of(customer1, customer2));
        //when
        List<Customer> customers = customerService.getAllCustomers();
        //then
        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSize(2);
        assertThat(customers).contains(customer1, customer2);
        assertThat(customers.get(0)).isEqualTo(customer1);
        assertThat(customers.get(1)).isEqualTo(customer2);
        verify(customerRepositoryMock).findAll();
    }

    @Test
    @DisplayName("[실패] 고객 테이블이 비어 있다면 빈 리스트가 반환된다")
    void findAllCustomersFromEmpty() {
        //given
        when(customerRepositoryMock.findAll()).thenReturn(List.of());
        //when
        List<Customer> customers = customerService.getAllCustomers();
        //then
        assertThat(customers).isEmpty();
        verify(customerRepositoryMock).findAll();
    }

    @Test
    @DisplayName("[성공] 고객 정보를 수정할 수 있다.")
    void updateCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test6");
        Customer newCustomer = new Customer(customerId, "test7");
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepositoryMock.update(newCustomer)).thenReturn(true);
        //when
        boolean updateResult = customerService.updateCustomer(customerId, newCustomer.getName());
        //then
        assertThat(updateResult).isTrue();
        verify(customerRepositoryMock).findById(customerId);
        verify(customerRepositoryMock).update(newCustomer);
    }

    @Test
    @DisplayName("[실패] 고객테이블에 없는 고객은 정보를 수정할 수 없다.")
    void updateNotExistCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
        //when
        boolean updateResult = customerService.updateCustomer(customerId, "test8");
        //then
        assertThat(updateResult).isFalse();
        verify(customerRepositoryMock).findById(customerId);
    }

    @Test
    @DisplayName("[성공] 고객을 삭제할 수 있다.")
    void deleteCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test10");
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepositoryMock.delete(customerId)).thenReturn(true);
        //when
        boolean deleteResult = customerService.deleteCustomer(customerId);
        //then
        assertThat(deleteResult).isTrue();
        verify(customerRepositoryMock).findById(customerId);
        verify(customerRepositoryMock).delete(customerId);
    }

    @Test
    @DisplayName("[실패] 고객테이블에 없는 고객은 삭제할 수 없다.")
    void deleteNotExistCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.empty());
        //when
        boolean deleteResult = customerService.deleteCustomer(customerId);
        //then
        assertThat(deleteResult).isFalse();
        verify(customerRepositoryMock).findById(customerId);
    }
}