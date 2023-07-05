package com.programmers.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.programmers.domain.customer.Customer;
import com.programmers.domain.customer.CustomerType;
import com.programmers.domain.customer.dto.CustomerCreateRequestDto;
import com.programmers.domain.customer.dto.CustomerResponseDto;
import com.programmers.domain.customer.dto.CustomersResponseDto;
import com.programmers.io.Console;
import com.programmers.service.BlacklistService;
import com.programmers.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class CustomerControllerTest {

    private CustomerController customerController;

    @Mock
    private Console console;

    @Mock
    private CustomerService customerService;

    @Mock
    private BlacklistService blacklistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(console, customerService, blacklistService);
    }

    @DisplayName("회원 이름을 입력받아 회원을 생성한다")
    @Test
    public void createCustomer() {
        //given
        String customerName = "customerName";

        CustomerCreateRequestDto requestDto = new CustomerCreateRequestDto(customerName);
        CustomerResponseDto responseDto = new CustomerResponseDto(UUID.randomUUID(), customerName, CustomerType.NORMAL);

        when(console.readInput()).thenReturn(customerName);
        when(customerService.save(requestDto)).thenReturn(responseDto);

        //when
        Customer createdCustomer = customerController.createCustomer();

        //then
        assertThat(customerName, is(createdCustomer.getCustomerName()));
    }

    @DisplayName("회원을 생성한다")
    @Test
    public void makeCustomer() {
        //given
        String customerName = "testCustomer";
        CustomerCreateRequestDto requestDto = new CustomerCreateRequestDto(customerName);
        CustomerResponseDto responseDto = new CustomerResponseDto(UUID.randomUUID(), customerName, CustomerType.NORMAL);

        when(customerService.save(requestDto)).thenReturn(responseDto);

        //when
        Customer createdCustomer = customerController.makeCustomer(customerName);

        //then
        assertThat(customerName, is(createdCustomer.getCustomerName()));
    }

    @DisplayName("Normal 회원 목록을 조회한다")
    @Test
    public void getNormalCustomerList() {
        //given
        CustomersResponseDto customersResponseDto = new CustomersResponseDto(
                Arrays.asList(new Customer("customer1"), new Customer("customer2")));

        when(customerService.findAll()).thenReturn(customersResponseDto);

        //when
        List<Customer> customers = customerController.getNormalCustomerList();

        //then
        assertThat(2, is(customers.size()));
        assertThat("customer1", is(customers.get(0).getCustomerName()));
        assertThat("customer2", is(customers.get(1).getCustomerName()));
    }

    @DisplayName("Normal 회원이 존재하지 않는 경우 빈 리스트를 반환한다")
    @Test
    public void getNormalCustomerListEmpty() {
        //given
        CustomersResponseDto customersResponseDto = new CustomersResponseDto(Collections.emptyList());

        when(customerService.findAll()).thenReturn(customersResponseDto);

        //when
        List<Customer> customers = customerController.getNormalCustomerList();

        //then
        Assertions.assertThat(customers).isEmpty();
    }

    @DisplayName("블랙리스트를 조회한다")
    @Test
    public void getBlacklist() {
        //given
        List<String> blacklist = Arrays.asList("black1", "black2");

        when(blacklistService.findAll()).thenReturn(blacklist);

        //when
        List<String> result = customerController.getBlacklist();

        //then
        assertThat(blacklist, is(result));
    }

    @DisplayName("업데이트 할 회원의 Id를 입력받고 해당하는 회원을 찾아서 반환한다")
    @Test
    public void getCustomerToUpdate() {
        //given
        UUID customerId = UUID.randomUUID();
        when(console.readInput()).thenReturn(customerId.toString());

        CustomerResponseDto customerResponseDto = new CustomerResponseDto(customerId, "customerName", CustomerType.NORMAL);
        when(customerService.findById(customerId)).thenReturn(customerResponseDto);

        //when
        Customer result = customerController.getCustomerToUpdate();

        //then
        assertEquals(customerId, result.getCustomerId());
        assertEquals("customerName", result.getCustomerName());
    }
}