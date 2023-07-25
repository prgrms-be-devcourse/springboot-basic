package com.programmers.voucher.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.voucher.domain.customer.controller.CustomerApiController;
import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GlobalApiControllerAdviceTest {

    private MockMvc mvc;

    @InjectMocks
    private CustomerApiController customerApiController;

    @Mock
    private CustomerService customerService;

    private ObjectMapper mapper;

    @BeforeEach
    void beforeAll() {
        mvc = MockMvcBuilders.standaloneSetup(customerApiController)
                .setControllerAdvice(new GlobalApiControllerAdvice())
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("성공: NoSuchElementException 처리")
    void noSuchElementExHandle() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        given(customerService.findCustomer(any())).willThrow(new NoSuchElementException("No such element"));

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value("404"))
                .andExpect(jsonPath("message").value("No such element"));
    }

    @Test
    @DisplayName("성공: DuplicateKeyException 처리")
    void duplicateKeyExHandle() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        given(customerService.findCustomer(any())).willThrow(new DuplicateKeyException("Email conflict"));

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value("409"))
                .andExpect(jsonPath("message").value("Email conflict"));
    }

    @Test
    @DisplayName("성공: IllegalArgumentException 처리")
    void illegalArgumentExHandle() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        given(customerService.findCustomer(any())).willThrow(new IllegalArgumentException("Email length out of range"));

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value("400"))
                .andExpect(jsonPath("message").value("Email length out of range"));
    }

    @Test
    @DisplayName("성공: MethodArgumentNotValidException 처리")
    void methodArgumentNotValidExHandle() throws Exception {
        //given
        String invalidEmail = "thisIsInvalidCustomerEmail@gmail.com";
        CustomerCreateRequest request = new CustomerCreateRequest(invalidEmail, "customer");
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value("400"));
    }

    @Test
    @DisplayName("성공: RuntimeException 처리")
    void runtimeExHandle() throws Exception {
        //given
        UUID customerId = UUID.randomUUID();

        given(customerService.findCustomer(any())).willThrow(new RuntimeException("Something wrong happen"));

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/customers/" + customerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("code").value("500"))
                .andExpect(jsonPath("message").value("Unexpected exception occurred"));
    }
}