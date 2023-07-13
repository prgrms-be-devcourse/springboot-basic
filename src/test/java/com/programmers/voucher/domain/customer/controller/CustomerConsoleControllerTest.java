package com.programmers.voucher.domain.customer.controller;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.CustomerTestUtil.createCustomerDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerConsoleControllerTest {

    @InjectMocks
    private CustomerConsoleController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    @DisplayName("성공: Customer 생성 요청")
    void createCustomer() {
        //given
        CustomerCreateRequest request = new CustomerCreateRequest("customer@gmail.com", "customer");
        UUID customerId = UUID.randomUUID();

        given(customerService.createCustomer(any(), any())).willReturn(customerId);

        //when
        customerController.createCustomer(request);

        //then
        then(customerService).should().createCustomer(any(), any());
    }

    @Test
    @DisplayName("성공: Customer 업데이트 요청")
    void updateCustomer() {
        //given
        CustomerUpdateRequest request = new CustomerUpdateRequest(UUID.randomUUID(), "updatedName", false);

        //when
        customerController.updateCustomer(request);

        //then
        then(customerService).should().updateCustomer(any(), any(), anyBoolean());
    }

    @Test
    @DisplayName("성공: Customer 삭제 요청")
    void deleteCustomer() {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        customerController.deleteCustomer(customerId);

        //then
        then(customerService).should().deleteCustomer(any());
    }

    @Test
    @DisplayName("성공: Customer 목록 조회 요청")
    void findCustomers() {
        //given
        CustomerDto customerA
                = createCustomerDto(UUID.randomUUID(), "customerA@gmail.com", "customerA", false);
        CustomerDto customerB
                = createCustomerDto(UUID.randomUUID(), "customerB@gmail.com", "customerB", false);
        List<CustomerDto> customers = List.of(customerA, customerB);

        given(customerService.findCustomers()).willReturn(customers);

        //when
        List<CustomerDto> result = customerController.findCustomers();

        //then
        assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(customerA, customerB);
    }

}