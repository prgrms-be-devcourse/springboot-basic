package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.programmers.vouchermanagement.customer.controller.MvcControllerResource.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerThymeleafController.class)
@ActiveProfiles("thyme")
class CustomerThymeleafControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;

    @Test
    @DisplayName("고객 생성을 요청한다. 그리고 customers 페이지도 이동한다.")
    void createCustomer() throws Exception {
        when(customerService.create(CREATE_CUSTOMER_REQUEST)).thenReturn(CUSTOMER_RESPONSE);

        mockMvc.perform(post("/customers/new")
                        .param("name", "customer")
                        .param("isBlack", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    @DisplayName("고객 생성 페이지를 요청한다.")
    void viewCreatePage() throws Exception {
        mockMvc.perform(get("/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customer-new"));
    }

    @Test
    @DisplayName("모든 고객 조회 페이지를 요청한다.")
    void readAll1() throws Exception {
        when(customerService.readAll()).thenReturn(CUSTOMERS);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attribute("customers", CUSTOMERS));
    }

    @Test
    @DisplayName("모든 고객 조회 페이지를 요청한다. + 쿼리 스트링(type=all)")
    void readAll2() throws Exception {
        when(customerService.readAll()).thenReturn(CUSTOMERS);

        mockMvc.perform(get("/customers")
                        .param("type", "all"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attribute("customers", CUSTOMERS));
    }

    @Test
    @DisplayName("블랙리스트 조회 페이지를 요청한다.")
    void readAllBlackCustomer() throws Exception {
        when(customerService.readAllBlackCustomer()).thenReturn(BLACKLIST);

        mockMvc.perform(get("/customers")
                        .param("type", "blacklist"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attribute("customers", BLACKLIST));
    }
}