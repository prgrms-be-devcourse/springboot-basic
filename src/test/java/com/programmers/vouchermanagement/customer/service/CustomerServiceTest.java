package com.programmers.vouchermanagement.customer.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;

class CustomerServiceTest {
    CustomerRepository customerRepository;
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock();
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("저장된 블랙리스트가 없을 때 블랙리스트 조회 시 빈 리스트를 반환한다.")
    void testReadBlacklistSuccessful_ReturnEmptyList() {
        //given
        doReturn(Collections.emptyList()).when(customerRepository).findBlackCustomers();

        //when
        final List<CustomerResponse> blacklist = customerService.readBlacklist();

        //then
        assertThat(blacklist.isEmpty(), is(true));

        //verify
        verify(customerRepository).findBlackCustomers();
    }

    @Test
    @DisplayName("저장된 블랙리스트 조회에 성공한다.")
    void testReadBlacklistSuccessful_ReturnList() {
        //given
        final Customer firstCustomer = new Customer(UUID.randomUUID(), "black 1", CustomerType.BLACK);
        final Customer secondCustomer = new Customer(UUID.randomUUID(), "black 2", CustomerType.BLACK);
        doReturn(List.of(firstCustomer, secondCustomer)).when(customerRepository).findBlackCustomers();

        //when
        final List<CustomerResponse> blacklist = customerService.readBlacklist();

        //then
        assertThat(blacklist, hasSize(2));

        //verify
        verify(customerRepository).findBlackCustomers();
    }
}
