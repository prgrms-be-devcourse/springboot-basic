package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.global.io.Console;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private Console console;

    @Mock
    private CustomerService customerService;

    @Test
    @DisplayName("성공: Customer 생성 요청")
    void createCustomer() {
        //given
        CustomerCreateRequest request = new CustomerCreateRequest("customer@gmail.com", "customer");
        UUID customerId = UUID.randomUUID();

        given(console.inputCustomerCreateInfo()).willReturn(request);
        given(customerService.createCustomer(any(), any())).willReturn(customerId);

        //when
        customerController.createCustomer();

        //then
        then(console).should().print(anyString());
    }

    @Test
    @DisplayName("성공: Customer 업데이트 요청")
    void updateCustomer() {
        //given
        CustomerUpdateRequest request = new CustomerUpdateRequest(UUID.randomUUID(), "updatedName", false);
        given(console.inputCustomerUpdateInfo()).willReturn(request);

        //when
        customerController.updateCustomer();

        //then
        then(customerService).should().updateCustomer(any(), any(), anyBoolean());
        then(console).should().print(anyString());
    }

    @Test
    @DisplayName("성공: Customer 삭제 요청")
    void deleteCustomer() {
        //given
        given(console.inputUUID()).willReturn(UUID.randomUUID());

        //when
        customerController.deleteCustomer();

        //then
        then(customerService).should().deleteCustomer(any());
        then(console).should().print(anyString());
    }

    @Test
    @DisplayName("성공: Customer 목록 조회 요청")
    void findCustomers() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA@gmail.com", "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB@gmail.com", "customerB");
        List<Customer> customers = List.of(customerA, customerB);

        given(customerService.findCustomers()).willReturn(customers);

        //when
        customerController.findCustomers();

        //then
        then(console).should().printCustomers(anyList());
    }

}