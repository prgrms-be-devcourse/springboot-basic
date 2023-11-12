package com.programmers.vouchermanagement.customer.controller;

import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.programmers.vouchermanagement.customer.controller.MvcControllerResource.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerRestController.class)
@ActiveProfiles("api")
class CustomerRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;

    @Test
    @DisplayName("고객 생성을 요청한다.")
    void createCustomer() throws Exception {
        when(customerService.create(CREATE_CUSTOMER_REQUEST)).thenReturn(CUSTOMER_RESPONSE);

        String response = mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(CREATE_CUSTOMER_REQUEST)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(CUSTOMER_RESPONSE));
    }

    @Test
    @DisplayName("모든 고객 조회를 요청한다.")
    void readAllCustomers1() throws Exception {
        when(customerService.readAll()).thenReturn(CUSTOMERS);

        String response = mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(CUSTOMERS));
    }

    @Test
    @DisplayName("모든 고객 조회를 요청한다. + 쿼리 스트링 사용(type=all)")
    void readAllCustomers2() throws Exception {
        when(customerService.readAll()).thenReturn(CUSTOMERS);

        String response = mockMvc.perform(get("/api/v1/customers")
                        .param("type", "all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(CUSTOMERS));
    }

    @Test
    @DisplayName("모든 블랙리스트 고객 조회를 요청한다.")
    void readAllBlacklist() throws Exception {
        when(customerService.readAllBlackCustomer()).thenReturn(BLACKLIST);

        String response = mockMvc.perform(get("/api/v1/customers")
                        .param("type","blacklist"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(BLACKLIST));
    }
}