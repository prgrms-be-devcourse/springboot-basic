package org.prgrms.springbootbasic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.dto.CustomerInputDto;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("customer 생성 test")
    void testCreateCustomer() throws Exception {
        // given
        UUID customerId = UUID.randomUUID();

        CustomerInputDto customerInputDto = new CustomerInputDto("name", "email@naver.com");
        String customerInputDtoJsonString = objectMapper.writeValueAsString(customerInputDto);

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Customer customer = Customer.builder()
                .customerId(customerId)
                .name("name")
                .email("email@naver.com")
                .createdAt(now)
                .lastLoginAt(now)
                .build();

        when(customerService.createCustomer(any(CustomerInputDto.class)))
                .thenReturn(customer);

        //expected
        mockMvc.perform(post("/api/v1/customers")
                        .contentType(APPLICATION_JSON)
                        .content(customerInputDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId.toString()))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.createdAt").value(customer.getCreatedAt().toString()))
                .andExpect(jsonPath("$.lastLoginAt").value(customer.getLastLoginAt().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("customer 목록 조회 - 성공")
    void testLookupCustomerList() throws Exception {
        // given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        LocalDateTime now = LocalDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS);

        Customer customer1 = Customer.builder()
                .customerId(id1)
                .name("name1")
                .email("email1@naver.com")
                .createdAt(now)
                .lastLoginAt(now)
                .build();

        Customer customer2 = Customer.builder()
                .customerId(id2)
                .name("name2")
                .email("email2@naver.com")
                .createdAt(now)
                .lastLoginAt(now)
                .build();

        Customer customer3 = Customer.builder()
                .customerId(id3)
                .name("name3")
                .email("email3@naver.com")
                .createdAt(now)
                .lastLoginAt(now)
                .build();

        when(customerService.getCustomerList())
                .thenReturn(List.of(customer1, customer2, customer3));

        //expected
        mockMvc.perform(get("/api/v1/customers/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(customer1.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].name").value(customer1.getName()))
                .andExpect(jsonPath("$[0].email").value(customer1.getEmail()))
                .andExpect(jsonPath("$[0].createdAt").value(customer1.getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].lastLoginAt").value(customer1.getLastLoginAt().toString()))
                .andExpect(jsonPath("$[1].customerId").value(customer2.getCustomerId().toString()))
                .andExpect(jsonPath("$[1].name").value(customer2.getName()))
                .andExpect(jsonPath("$[1].email").value(customer2.getEmail()))
                .andExpect(jsonPath("$[1].createdAt").value(customer2.getCreatedAt().toString()))
                .andExpect(jsonPath("$[1].lastLoginAt").value(customer2.getLastLoginAt().toString()))
                .andExpect(jsonPath("$[2].customerId").value(customer3.getCustomerId().toString()))
                .andExpect(jsonPath("$[2].name").value(customer3.getName()))
                .andExpect(jsonPath("$[2].email").value(customer3.getEmail()))
                .andExpect(jsonPath("$[2].createdAt").value(customer3.getCreatedAt().toString()))
                .andExpect(jsonPath("$[2].lastLoginAt").value(customer3.getLastLoginAt().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("customer 조회 - 성공")
    void testLookupCustomer() throws Exception {
        // given
        UUID customerId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS);

        Customer customer = Customer.builder()
                .customerId(customerId)
                .name("name")
                .email("email@naver.com")
                .createdAt(now)
                .lastLoginAt(now)
                .build();

        when(customerService.getCustomerById(customerId.toString()))
                .thenReturn(customer);

        //expected
        mockMvc.perform(get("/api/v1/customers/{customerId}", customerId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId.toString()))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.createdAt").value(customer.getCreatedAt().toString()))
                .andExpect(jsonPath("$.lastLoginAt").value(customer.getLastLoginAt().toString()))
                .andDo(print());
    }

}