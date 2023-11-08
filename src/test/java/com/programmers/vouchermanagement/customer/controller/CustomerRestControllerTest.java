package com.programmers.vouchermanagement.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.customer.controller.dto.CreateCustomerRequest;
import com.programmers.vouchermanagement.customer.controller.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerRestController.class)
@ActiveProfiles("api")
class CustomerRestControllerTest {
    static ObjectMapper objectMapper;
    static List<CustomerResponse> customers;
    static List<CustomerResponse> blacklist;
    static Customer customer;
    static CreateCustomerRequest createCustomerRequest;
    static CustomerResponse customerResponse;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();

        customers = List.of(
                CustomerResponse.from(new Customer("customer1", false)),
                CustomerResponse.from(new Customer("customer2", true))
        );
        blacklist = List.of(
                CustomerResponse.from(new Customer("customer3", true)),
                CustomerResponse.from(new Customer("customer4", true))
        );

        createCustomerRequest = new CreateCustomerRequest("customer5", true);
        customer = new Customer(createCustomerRequest.name(), createCustomerRequest.isBlack());
        customerResponse = CustomerResponse.from(customer);
    }

    @Test
    @DisplayName("고객 생성을 요청한다.")
    void createCustomer() throws Exception {
        when(customerService.create(createCustomerRequest)).thenReturn(customerResponse);

        String response = mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCustomerRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(customerResponse));
    }

    @Test
    @DisplayName("모든 고객 조회를 요청한다.")
    void readAllCustomers1() throws Exception {
        when(customerService.readAll()).thenReturn(customers);

        String response = mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(customers));
    }

    @Test
    @DisplayName("모든 고객 조회를 요청한다. + 쿼리 스트링 사용(type=all)")
    void readAllCustomers2() throws Exception {
        when(customerService.readAll()).thenReturn(customers);

        String response = mockMvc.perform(get("/api/v1/customers?type=all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(customers));
    }

    @Test
    @DisplayName("모든 블랙리스트 고객 조회를 요청한다.")
    void readAllBlacklist() throws Exception {
        when(customerService.readAllBlackCustomer()).thenReturn(blacklist);

        String response = mockMvc.perform(get("/api/v1/customers?type=blacklist"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(blacklist));
    }
}