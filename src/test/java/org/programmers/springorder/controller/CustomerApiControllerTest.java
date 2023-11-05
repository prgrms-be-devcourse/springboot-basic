package org.programmers.springorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.controller.customer.CustomerApiController;
import org.programmers.springorder.dto.customer.CustomerResponseDto;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;
import org.programmers.springorder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerApiController.class)
class CustomerApiControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("전체 회원 조회에 성공한다.")
    void findAllCustomer() throws Exception {
        // given
        Customer customer = Customer.toCustomer(UUID.randomUUID(), "홍길동", CustomerType.NORMAL);
        List<CustomerResponseDto> customers = List.of(CustomerResponseDto.of(customer));

        // when
        when(customerService.getAllCustomer()).thenReturn(customers);

        // then
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("홍길동"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customerType").value(CustomerType.NORMAL.name()));
    }

    @Test
    @DisplayName("회원 ID로 조회에 성공한다.")
    void findCustomer() throws Exception {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = Customer.toCustomer(UUID.randomUUID(), "홍길동", CustomerType.NORMAL);

        // when
        when(customerService.findById(customerId)).thenReturn(customer);

        // then
        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("홍길동"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerType").value(CustomerType.NORMAL.name()));
    }

    @Test
    @DisplayName("회원 삭제에 성공한다.")
    void deleteCustomer() throws Exception {
        // given
        UUID customerId = UUID.randomUUID();

        // then
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("삭제가 완료되었습니다."));
    }
}