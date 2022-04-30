package org.prgrms.deukyun.voucherapp.domain.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.CustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.*;

class CustomerServiceTest {

    CustomerService customerService;
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void 성공_전체조회() {
        //given
        List<Customer> customers = List.of(customer(), customer());
        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<Customer> foundCustomers = customerService.findAll();

        //then
        assertThat(foundCustomers).hasSameElementsAs(customers);
    }

    @Test
    void 성공_차단고객_전체조회() {
        //given
        List<Customer> customers = List.of(customer(), customer());
        when(customerRepository.findAllBlocked()).thenReturn(customers);

        //when
        List<Customer> foundCustomers = customerService.findAllBlocked();

        //then
        assertThat(foundCustomers).hasSameElementsAs(customers);
    }

    @Test
    void 성공_삽입(){
        //given
        Customer customer = customer();

        //when
        customerService.insert(customer);

        //then
        verify(customerRepository).insert(customer);
    }
}