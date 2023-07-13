//package com.prgms.VoucherApp.domain.customer.controller;
//
//import com.prgms.VoucherApp.domain.customer.model.CustomerService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = CustomerController.class)
//class CustomerControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    CustomerService customerService;
//
//    @Test
//    @DisplayName("고객 생성 페이지를 요청하고 정상 반환한다.")
//    void customerSaveFormTest() throws Exception {
//        // given
//
//        // when
//        MockHttpServletRequestBuilder requestBuilder =
//                MockMvcRequestBuilders.get("/customers/add").contentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        // then
//        mockMvc.perform(requestBuilder)
//                .andExpect(view().name("customer/add_customer"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("고객 생성을 완료하고 리다이렉트 한다.")
//    void customerSaveTest() throws Exception {
//        // given
//
//        // when
//        MockHttpServletRequestBuilder requestBuilder =
//                MockMvcRequestBuilders.post("/customers/add")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        // then
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/customers"));
//    }
//}