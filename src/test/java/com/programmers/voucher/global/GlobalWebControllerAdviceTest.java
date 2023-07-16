package com.programmers.voucher.global;

import com.programmers.voucher.domain.customer.controller.CustomerWebController;
import com.programmers.voucher.domain.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class GlobalWebControllerAdviceTest {

    private MockMvc mvc;

    @InjectMocks
    private CustomerWebController customerWebController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders.standaloneSetup(customerWebController)
                .setControllerAdvice(new GlobalWebControllerAdvice())
                .build();
    }

    @Test
    @DisplayName("성공: NoSuchElementException 발생 - 예외 페이지 모델&뷰 반환")
    void noSuchElementExHandle() throws Exception {
        //given
        given(customerService.findCustomers()).willThrow(new NoSuchElementException("No such element"));

        //when
        ResultActions resultActions = mvc.perform(get("/customers"));

        //then
        resultActions
                .andExpect(model().attributeExists("errorResult"))
                .andExpect(view().name("errorPage"));
    }

    @Test
    @DisplayName("성공: DuplicateKeyException 발생 - 예외 페이지 모델&뷰 반환")
    void duplicateKeyExHandle() throws Exception {
        //given
        given(customerService.findCustomers()).willThrow(new DuplicateKeyException("Email conflict"));

        //when
        ResultActions resultActions = mvc.perform(get("/customers"));

        //then
        resultActions
                .andExpect(model().attributeExists("errorResult"))
                .andExpect(view().name("errorPage"));
    }

    @Test
    @DisplayName("성공: IllegalArgumentException 발생 - 예외 페이지 모델&뷰 반환")
    void illegalArgumentExHandle() throws Exception {
        //given
        given(customerService.findCustomers()).willThrow(new IllegalArgumentException("Email length out of range"));

        //when
        ResultActions resultActions = mvc.perform(get("/customers"));

        //then
        resultActions
                .andExpect(model().attributeExists("errorResult"))
                .andExpect(view().name("errorPage"));
    }

    @Test
    @DisplayName("성공: RuntimeException 발생 - 예외 페이지 모델&뷰 반환")
    void runtimeExHandle() throws Exception {
        //given
        given(customerService.findCustomers()).willThrow(new RuntimeException("Something wrong happen"));

        //when
        ResultActions resultActions = mvc.perform(get("/customers"));

        //then
        resultActions
                .andExpect(model().attributeExists("errorResult"))
                .andExpect(view().name("errorPage"));
    }



}