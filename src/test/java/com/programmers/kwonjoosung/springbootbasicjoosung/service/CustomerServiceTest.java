package com.programmers.kwonjoosung.springbootbasicjoosung.service;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    CustomerRepository customerRepositoryMock = mock(CustomerRepository.class);
    CustomerService customerService = new CustomerService(customerRepositoryMock);

    @Test
    @DisplayName("고객을 생성할 수 있다")
    void createCustomer() {

    }

    @Test
    @DisplayName("고객을 Id로 조회할 수 있다")
    void findByCustomerId() {
        //given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "joosung");
        //when
        when(customerRepositoryMock.findById(customerId)).thenReturn(customer);
        Customer foundCustomerById = customerService.findCustomerByCustomerId(customerId);
        //then
        assertEquals(customer, foundCustomerById);
        verify(customerRepositoryMock).findById(customerId);
    }

    @Test
    @DisplayName("모든 고객을 조회할 수 있다")
    void findAllCustomers() {
        //given
        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();
        Customer customer1 = new Customer(customerId1, "joosung1");
        Customer customer2 = new Customer(customerId2, "joosung2");
        //when
        when(customerRepositoryMock.findAll()).thenReturn(List.of(customer1, customer2));
        List<Customer> customers = customerService.getAllCustomers();
        //then
        assertEquals(customer1, customers.get(0));
        assertEquals(customer2, customers.get(1));
        verify(customerRepositoryMock).findAll();
    }

    @Test
    @DisplayName("고객 정보를 수정할 수 있다.")
    void updateCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "joosung";
        //when
        when(customerRepositoryMock.update(customerId,name)).thenReturn(true);
        //then
        assertTrue(customerService.updateCustomer(customerId, name));
        verify(customerRepositoryMock).update(customerId,name);
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void deleteCustomer() {
        //given
        UUID customerId = UUID.randomUUID();
        //when
        when(customerRepositoryMock.delete(customerId)).thenReturn(true);
        //then
        assertTrue(customerService.deleteCustomer(customerId));
        verify(customerRepositoryMock).delete(customerId);
    }

}