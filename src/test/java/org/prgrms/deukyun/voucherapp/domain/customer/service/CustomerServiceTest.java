package org.prgrms.deukyun.voucherapp.domain.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.CustomerRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.*;

class CustomerServiceTest {

    CustomerService customerService;
    CustomerRepository mockCustomerRepository;

    @BeforeEach
    void setUp() {
        mockCustomerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(mockCustomerRepository);
    }

    @Test
    void 성공_전체조회() {
        //given
        List<Customer> customers = List.of(customer(), customer());
        when(mockCustomerRepository.findAll()).thenReturn(customers);

        //when
        List<Customer> foundCustomers = customerService.findAll();

        //then
        assertThat(foundCustomers).hasSameElementsAs(customers);
    }

    @Test
    void 성공_차단고객_전체조회() {
        //given
        List<Customer> customers = List.of(customer(), customer());
        when(mockCustomerRepository.findAllBlocked()).thenReturn(customers);

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
        verify(mockCustomerRepository).insert(customer);
    }
}