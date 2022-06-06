package org.devcourse.voucher.application.customer.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.devcourse.voucher.application.customer.controller.dto.CustomerRequest;
import org.devcourse.voucher.application.customer.controller.dto.CustomerResponse;
import org.devcourse.voucher.application.customer.model.Email;
import org.devcourse.voucher.application.customer.service.CustomerService;
import org.devcourse.voucher.core.exception.ControllerExceptionHandler;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.devcourse.voucher.core.exception.stub.CustomerStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_CUSTOMER;
import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_VOUCHER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class RestCustomerControllerTest {

    @InjectMocks
    private RestCustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String URL = "/api/v1/customer";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("고객 생성 테스트")
    void postCreateCustomerTest() throws Exception {
        // given
        CustomerRequest request = CustomerStubs.customerRequest();
        CustomerResponse response = CustomerStubs.customerResponse(UUID.randomUUID());

        // when
        doReturn(response)
                .when(customerService)
                .createCustomer(anyString(), any(Email.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(CREATED.value()))
                .andExpect(jsonPath("$.data.customerId").value(response.getCustomerId().toString()))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("페이징을 이용한 고객 목록 가져오기 테스트")
    void getCustomerListTest() throws Exception {
        // given
        List<CustomerResponse> response = CustomerStubs.customerResponseList();

        // when
        doReturn(response)
                .when(customerService)
                .recallAllCustomer(any(Pageable.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(URL)
                        .param("page", "0")
                        .param("size", "5")
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].customerId").value(response.get(0).getCustomerId().toString()))
                .andExpect(jsonPath("$.data.[1].customerId").value(response.get(1).getCustomerId().toString()))
                .andExpect(jsonPath("$.data.[2].customerId").value(response.get(2).getCustomerId().toString()))
                .andExpect(jsonPath("$.data.[3].customerId").value(response.get(3).getCustomerId().toString()))
                .andExpect(jsonPath("$.data.[4].customerId").value(response.get(4).getCustomerId().toString()));
    }

    @Test
    @DisplayName("고객 ID를 통해 해당 고객 가져오기 테스트")
    void getCustomerByIdTest() throws Exception {
        // given
        UUID request = UUID.randomUUID();
        CustomerResponse response = CustomerStubs.customerResponse(request);

        // when
        doReturn(response)
                .when(customerService)
                .recallCustomerById(any(UUID.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(URL + "/" + request)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data.customerId").value(response.getCustomerId().toString()))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("존재하지 않는 고객을 조회시 예외 발생 테스트")
    void notValidGetCustomerByIdTest() throws Exception {
        // given
        UUID request = UUID.randomUUID();
        NotFoundException notFoundException = new NotFoundException(NOT_FOUND_CUSTOMER, request);

        // when
        doThrow(notFoundException)
                .when(customerService)
                .recallCustomerById(any(UUID.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(URL + "/" + request)
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").value(notFoundException.getMessage()));
    }

    @Test
    @DisplayName("고객 정보 업데이트 테스트")
    void patchUpdateCustomerTest() throws Exception {
        // given
        UUID requestId = UUID.randomUUID();
        CustomerRequest request = CustomerStubs.customerRequest();
        CustomerResponse response = CustomerStubs.customerResponse(requestId);

        // when
        doReturn(response)
                .when(customerService)
                .updateCustomer(any(UUID.class), anyString(), any(Email.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(URL + "/" + requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data.customerId").value(response.getCustomerId().toString()))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("존재하지 않는 고객을 업데이트 시도시 예외 발생 테스트")
    void notValidPatchUpdateCustomerTest() throws Exception {
        // given
        UUID requestId = UUID.randomUUID();
        CustomerRequest request = CustomerStubs.customerRequest();
        NotFoundException notFoundException = new NotFoundException(NOT_FOUND_CUSTOMER, requestId);

        // when
        doThrow(notFoundException)
                .when(customerService)
                .updateCustomer(any(UUID.class), anyString(), any(Email.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(URL + "/" + requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").value(notFoundException.getMessage()));
    }

    @Test
    @DisplayName("고객 아이디를 기준으로 고객 삭제 테스트")
    void deleteRemoveCustomerTest() throws Exception {
        // given
        UUID request = UUID.randomUUID();
        String response = CustomerStubs.deleteMessage(request);

        // when
        doNothing()
                .when(customerService)
                .deleteCustomer(request);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(URL + "/" + request)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data").value(response));
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 삭제 요청을 보냈을 경우 예외 발생 테스트")
    void notValidDeleteRemoveCustomerTest() throws Exception {
        // given
        UUID request = UUID.randomUUID();
        NotFoundException notFoundException = new NotFoundException(NOT_FOUND_CUSTOMER, request);

        // when
        doThrow(notFoundException)
                .when(customerService)
                .deleteCustomer(request);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(URL + "/" + request)
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").value(notFoundException.getMessage()));
    }
}