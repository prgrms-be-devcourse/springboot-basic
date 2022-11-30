package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private final CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
    private final CustomerService customerService = new CustomerService(customerRepositoryMock);

    @Test
    @DisplayName("[성공] 고객을 생성할 수 있다")
    void createCustomer() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test1");
        //when
        try (MockedStatic<UUID> utilities = mockStatic(UUID.class)) {
            utilities.when(UUID::randomUUID).thenReturn(customer.getCustomerId());
            when(customerRepositoryMock.insert(customer)).thenReturn(customer);
            customerService.createCustomer(customer.getName());
            utilities.verify(UUID::randomUUID);
        }
        //then
        verify(customerRepositoryMock).insert(customer);
    }


    @Test
    @DisplayName("[성공] 고객을 Id로 조회하는 기능을 테스트 한다")
    void findCustomerByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test3");
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(customer));
        //when
        customerService.findCustomerByCustomerId(customerId);
        //then
        verify(customerRepositoryMock).findById(customerId);
    }

    @Test
    @DisplayName("[성공] 모든 고객을 조회하는 기능을 테스트 한다")
    void findAllCustomers() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        Customer customer1 = new Customer(customerId1, "test4");
        Customer customer2 = new Customer(customerId2, "test5");
        when(customerRepositoryMock.findAll()).thenReturn(List.of(customer1, customer2));
        //when
        customerService.getAllCustomers();
        //then
        verify(customerRepositoryMock).findAll();
    }

    @Test
    @DisplayName("[성공] 고객 정보를 수정하는 기능을 테스트 한다")
    void updateCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test6");
        when(customerRepositoryMock.update(customer)).thenReturn(customer);
        //when
        customerService.updateCustomer(customerId, customer.getName());
        //then
        verify(customerRepositoryMock).update(customer);
    }

    @Test
    @DisplayName("[성공] 고객을 삭제하는 기능을 테스트 한다")
    void deleteCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        //when
        customerService.deleteCustomer(customerId);
        //then
        verify(customerRepositoryMock).delete(customerId);
    }

}