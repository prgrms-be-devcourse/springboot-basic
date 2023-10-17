package com.zerozae.voucher.controller;

import com.zerozae.voucher.common.message.MessageConverter;
import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.customer.CustomerController;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.service.customer.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {
    CustomerService customerService = mock(CustomerService.class);
    CustomerController customerController = new CustomerController(customerService);
    MessageConverter messageConverter = new MessageConverter(mock(MessageSource.class));

    @Test
    @DisplayName("회원 컨트롤러 : 회원 생성 성공 반환 테스트")
    void createCustomerSuccessTest(){
        // Given
        CustomerRequest customerRequest = new CustomerRequest("영재", CustomerType.BLACKLIST);

        // When
        Response response = customerController.createCustomer(customerRequest);

        // Then
        assertTrue(response.isSuccess());
    }

    @Test
    @DisplayName("회원 컨트롤러 : 회원 생성 실패 테스트(빈 문자열 입력)")
    void createCustomerFailedTest(){
        // Given
        CustomerRequest customerRequest = new CustomerRequest("", CustomerType.BLACKLIST);

        // When
        Response response = customerController.createCustomer(customerRequest);

        // Then
        assertFalse(response.isSuccess());
    }

    @Test
    @DisplayName("회원 컨트롤러 : 회원 생성 실패 테스트(CustomerService 예외)")
    void createCustomerFailureServiceExceptionTest() {
        // Given: 유효한 고객 정보, CustomerService에서 발생하는 예외
        CustomerRequest customerRequest = new CustomerRequest("Alice", CustomerType.NORMAL);
        doThrow(ErrorMessage.class).when(customerService).createCustomer(customerRequest);

        // When: createCustomer 메서드 호출
        Response response = customerController.createCustomer(customerRequest);

        // Then: Response 객체는 실패해야 합니다.
        assertFalse(response.isSuccess());
    }

    @Test
    @DisplayName("모든 고객 조회 테스트")
    void findAllCustomersSuccessTest() {
        // Given
        List<CustomerResponse> customerResponses = List.of(
                new CustomerResponse(UUID.randomUUID().toString(), "고객1", CustomerType.NORMAL),
                new CustomerResponse(UUID.randomUUID().toString(), "고객2", CustomerType.BLACKLIST)
        );

        List<String> result = customerResponses.stream()
                .map(CustomerResponse::getInfo)
                .toList();

        when(customerService.findAllCustomers()).thenReturn(customerResponses);

        // When
        Response response = customerController.findAllCustomers();

        // Then
        assertTrue(response.isSuccess());
        assertEquals(2, response.getData().size());
        assertEquals(response.getData(), result);
    }

    @Test
    @DisplayName("블랙리스트 고객 조회 테스트")
    void findBlacklistCustomersSuccessTest() {
        // Given
        CustomerResponse customer1 = new CustomerResponse(UUID.randomUUID().toString(), "고객1", CustomerType.NORMAL);
        CustomerResponse customer2 = new CustomerResponse(UUID.randomUUID().toString(), "고객2", CustomerType.BLACKLIST);
        List<CustomerResponse> customerResponses = new ArrayList<>();

        customerResponses.add(customer1);
        customerResponses.add(customer2);

        List<String> result = customerResponses.stream()
                .filter(r -> r.getCustomerType().equals(CustomerType.BLACKLIST))
                .map(CustomerResponse::getInfo)
                .toList();

        when(customerService.findAllBlacklistCustomer()).thenReturn(List.of(customer2));

        // When
        Response response = customerController.findAllBlacklistCustomers();

        // Then
        assertTrue(response.isSuccess());
        assertEquals(1, response.getData().size());
        assertEquals(response.getData(), result);
    }
}
