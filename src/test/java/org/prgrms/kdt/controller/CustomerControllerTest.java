package org.prgrms.kdt.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.controller.request.CreateCustomerRequest;
import org.prgrms.kdt.controller.response.CustomerResponse;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.dto.CreateCustomerDto;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {
    private CustomerController customerController;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        this.customerService = mock(CustomerService.class);
        this.customerController = new CustomerController(customerService);
    }

    @Test
    @DisplayName("[성공] 사용자 저장 요청 처리하기")
    void createCustomerTest() {
        String email = "asdf@naver.com";
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(email);
        CreateCustomerDto createCustomerDto = new CreateCustomerDto(email);
        when(customerService.hasDuplicatedCustomer(email)).thenReturn(false);
        when(customerService.createCustomer(createCustomerDto)).thenReturn(true);

        boolean result = customerController.createCustomer(createCustomerRequest);

        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("[실패] 사용자 이메일이 이미 존재할 경우")
    void createCustomerTest_duplicatedUserEmail() {
        String email = "asdf@naver.com";
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(email);
        when(customerService.hasDuplicatedCustomer(email)).thenReturn(true);

        boolean result = customerController.createCustomer(createCustomerRequest);

        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("[성공] 이메일을 통해 사용자 가져오는 요청 처리하기")
    void getCustomerByEmail() {
        String email = "asdf@naver.com";
        Customer customer = new Customer(email);
        when(customerService.getCustomerByEmail(email)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerController.getCustomerByEmail(email);

        Assertions.assertEquals(response.customer().getEmail(), email);
    }

    @Test
    @DisplayName("[실패] 해당 이메일을 가지는 사용자가 존재하지 않을 경우")
    void getCustomerByEmail_InvalidEmail(){
        String email = "asdf@naver.com";
        when(customerService.getCustomerByEmail(email)).thenReturn(Optional.empty());

        CustomerResponse response = customerController.getCustomerByEmail(email);

        Assertions.assertNull(response.customer());
    }
}
