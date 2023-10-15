package com.zerozae.voucher.controller;

import com.zerozae.voucher.common.message.MessageConverter;
import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.customer.CustomerController;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.mock.MockCustomerRepository;
import com.zerozae.voucher.mock.MockMessageSource;
import com.zerozae.voucher.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerControllerTest {
    CustomerController customerController;
    MessageConverter messageConverter;

    @BeforeEach
    void setUp(){
        customerController = new CustomerController(new CustomerService(new MockCustomerRepository()));
        messageConverter = new MessageConverter(new MockMessageSource());
    }

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
    @DisplayName("회원 컨트롤러 : 회원 생성 성공 실패 테스트(빈 문자열 입력)")
    void createCustomerFailedTest(){
        // Given
        CustomerRequest customerRequest = new CustomerRequest("", CustomerType.BLACKLIST);

        // When
        Response response = customerController.createCustomer(customerRequest);

        // Then
        assertFalse(response.isSuccess());
    }
}
