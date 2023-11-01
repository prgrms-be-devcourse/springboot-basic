package com.zerozae.voucher.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.service.customer.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.domain.customer.CustomerType.BLACKLIST;
import static com.zerozae.voucher.domain.customer.CustomerType.NORMAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiCustomerController.class)
class ApiCustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private Customer normalCustomer = new Customer(UUID.randomUUID(), "normalCustomer", NORMAL);
    private Customer blackCustomer = new Customer(UUID.randomUUID(), "blackCustomer", BLACKLIST);

    @Test
    @DisplayName("회원 생성 테스트")
    void createCustomer_Success_Test() throws Exception {
        // Given
        CustomerCreateRequest request = new CustomerCreateRequest(normalCustomer.getCustomerName(), String.valueOf(normalCustomer.getCustomerType()));
        CustomerResponse response = CustomerResponse.toDto(normalCustomer);
        when(customerService.createCustomer(request)).thenReturn(response);

        // When
        mvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Then
        verify(customerService).createCustomer(any(CustomerCreateRequest.class));
    }

    @Test
    @DisplayName("회원 생성 실패 테스트 - 이름 유효성 검사 실패")
    void createCustomer_InvalidName_Failed_Test() throws Exception {
        // Given
        CustomerCreateRequest invalidRequest = new CustomerCreateRequest("a", String.valueOf(BLACKLIST));

        // When & Then
        mvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(jsonPath("$.message").value("회원 이름은 2자에서 15자 사이로 입력해주세요."))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("회원 생성 실패 테스트 - 회원 타입 누락 실패")
    void createCustomer_EmptyCustomerType_Failed_Test() throws Exception {
        // Given
        CustomerCreateRequest invalidRequest = new CustomerCreateRequest("customer", null);

        // When & Then
        mvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(jsonPath("$.message").value("회원 타입은 필수 입력란입니다."))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("회원 전체 조회 테스트")
    void findAllCustomers_Success_Test() throws Exception {
        // Given
        List<CustomerResponse> customers = List.of(
                CustomerResponse.toDto(normalCustomer)
        );
        when(customerService.findAllCustomers()).thenReturn(customers);

        // When
        mvc.perform(get("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(normalCustomer.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].customerName").value(normalCustomer.getCustomerName()))
                .andExpect(jsonPath("$[0].customerType").value(normalCustomer.getCustomerType().toString()));

        // Then
        verify(customerService).findAllCustomers();
    }

    @Test
    @DisplayName("회원 아이디로 조회 테스트")
    void findCustomerById_Success_Test() throws Exception {
        // Given
        when(customerService.findById(normalCustomer.getCustomerId())).thenReturn(CustomerResponse.toDto(normalCustomer));

        // When
        mvc.perform(get("/api/customers/{customerId}", normalCustomer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(normalCustomer.getCustomerId().toString()))
                .andExpect(jsonPath("$.customerName").value(normalCustomer.getCustomerName()))
                .andExpect(jsonPath("$.customerType").value(normalCustomer.getCustomerType().toString()));

        // Then
        verify(customerService).findById(any(UUID.class));
    }
    
    @Test
    @DisplayName("회원 업데이트 테스트")
    void updateCustomer_Success_Test() throws Exception {
        // Given
        CustomerUpdateRequest request = new CustomerUpdateRequest("updateCustomer", String.valueOf(BLACKLIST));

        // When
        mvc.perform(patch("/api/customers/{customerId}", normalCustomer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Then
        verify(customerService).update(any(UUID.class), any(CustomerUpdateRequest.class));
    }

    @Test
    @DisplayName("회원 업데이트 실패 테스트 - 회원 타입 누락 실패")
    void updateCustomer_EmptyCustomerType_Failed_Test() throws Exception {
        // Given
        CustomerUpdateRequest invalidRequest = new CustomerUpdateRequest("customer", null);

        // When & Then
        mvc.perform(patch("/api/customers/{customerId}", normalCustomer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(jsonPath("$.message").value("회원 타입은 필수 입력란입니다."))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("회원 업데이트 실패 테스트 - 이름 유효성 검사 실패")
    void updateCustomer_InvalidName_Failed_Test() throws Exception {
        // Given
        CustomerUpdateRequest invalidRequest = new CustomerUpdateRequest("a", String.valueOf(BLACKLIST));

        // When & Then
        mvc.perform(patch("/api/customers/{customerId}", normalCustomer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(jsonPath("$.message").value("회원 이름은 2자에서 15자 사이로 입력해주세요."))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("회원 아이디로 삭제 테스트")
    void deleteCustomerById_Success_Test() throws Exception {
        // Given

        // When
        mvc.perform(delete("/api/customers/{customerId}", blackCustomer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(customerService).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("회원 전체 삭제 테스트")
    void deleteAllCustomers_Success_Test() throws Exception {
        // Given

        // When
        mvc.perform(delete("/api/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(customerService).deleteAll();
    }
}
