package com.programmers.voucher.domain.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import com.programmers.voucher.testutil.CustomerTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerApiController.class)
class CustomerApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("성공: customer 목록 조회 요청")
    void findCustomers() throws Exception {
        //given
        CustomerDto customerA
                = CustomerTestUtil.createCustomerDto("customerA@gmail.com");
        CustomerDto customerB
                = CustomerTestUtil.createCustomerDto("customerB@gmail.com");
        List<CustomerDto> customers = List.of(customerA, customerB);

        given(customerService.findCustomers()).willReturn(customers);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(customers)));
    }

    @Test
    @DisplayName("성공: customer 단건 조회 요청")
    void findCustomer() throws Exception {
        //given
        CustomerDto customer
                = CustomerTestUtil.createCustomerDto("customer@gmail.com");

        given(customerService.findCustomer(any())).willReturn(customer);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/customers/" + customer.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(customer)));
    }

    @Test
    @DisplayName("성공: customer 단건 생성 요청")
    void createCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        CustomerCreateRequest request = new CustomerCreateRequest("customer@gmail.com", "customer");

        given(customerService.createCustomer(any(), any())).willReturn(customerId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(mapper.writeValueAsString(customerId)));
    }

    @Test
    @DisplayName("성공(400): customer 단건 생성 요청 - 잘못된 이메일 형식")
    void createCustomer_ButInvalidEmail_Then_BadRequest() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        String invalidEmail = "thisIsiIvalidCustomerEmail@gmail.com";
        CustomerCreateRequest request = new CustomerCreateRequest(invalidEmail, "customer");

        given(customerService.createCustomer(any(), any())).willReturn(customerId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("성공(400): customer 단건 생성 요청 - 잘못된 이름 형식")
    void createCustomer_ButInvalidName_Then_BadRequest() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        String invalidName = "thisIsInvalidCustomerName";
        CustomerCreateRequest request = new CustomerCreateRequest("customer@gmail.com", invalidName);

        given(customerService.createCustomer(any(), any())).willReturn(customerId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("성공: customer 단건 업데이트 요청")
    void updateCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        CustomerUpdateRequest request = new CustomerUpdateRequest(customerId, "customer", false);

        //when
        ResultActions resultActions = mvc.perform(patch("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("성공: customer 단건 업데이트 요청 - 잘못된 이름 형식")
    void updateCustomer_ButInvalidName_Then_BadRequest() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();
        String invalidName = "thisIsInvalidCustomerName";
        CustomerUpdateRequest request = new CustomerUpdateRequest(customerId, invalidName, false);

        //when
        ResultActions resultActions = mvc.perform(patch("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("성공: customer 단건 삭제 요청")
    void deleteCustomer() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerId)))
                .andDo(print());

        //then
        resultActions.andExpect(status().isNoContent());
    }
}