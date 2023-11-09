package com.programmers.springbootbasic.domain.customer.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.customer.dto.CustomerRequestDto;
import com.programmers.springbootbasic.domain.customer.entity.Customer;
import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Controller Test")
class CustomerControllerTest {
    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    private static final String EMAIL = "test@gmail.com";
    private static final String WRONG_EMAIL = "test@";
    private static final String NAME = "test";
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;

    @Test
    void testCreateCustomerSuccess() {
        // Arrange
        Customer expectedCustomer = Customer.builder()
                .customerId(CUSTOMER_ID)
                .email(EMAIL)
                .name(NAME)
                .isBlacklist(false)
                .build();
        when(customerService.createCustomer(any(CustomerRequestDto.class))).thenReturn(expectedCustomer);
        // Act
        CommonResult<String> actualResult = customerController.createCustomer(EMAIL, NAME);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test createCustomerFail: Email verify Fail")
    @Test
    void testCreateCustomerFailWhenEmailVerifyFail() {
        // Act
        CommonResult<String> actualResult = customerController.createCustomer(WRONG_EMAIL, NAME);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

    @Test
    void testAddCustomerInBlacklistSuccess() {
        // Arrange
        doNothing().when(customerService).addCustomerInBlacklist(any(CustomerRequestDto.class));
        // Act
        CommonResult<String> actualResult = customerController.addCustomerInBlacklist(EMAIL);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test addCustomerInBlacklist: Email verify Fail")
    @Test
    void testAddCustomerInBlacklistFailWhenEmailVerifyFail() {
        // Act
        CommonResult<String> actualResult = customerController.addCustomerInBlacklist(WRONG_EMAIL);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

    @Test
    void testRemoveCustomerInBlacklistSuccess() {
        // Arrange
        doNothing().when(customerService).removeCustomerFromBlacklist(any(CustomerRequestDto.class));
        // Act
        CommonResult<String> actualResult = customerController.removeCustomerInBlacklist(EMAIL);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test removeCustomerInBlacklist: Email verify Fail")
    @Test
    void testRemoveCustomerInBlacklistFailWhenEmailVerifyFail() {
        // Act
        CommonResult<String> actualResult = customerController.removeCustomerInBlacklist(WRONG_EMAIL);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

}