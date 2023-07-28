package com.tangerine.voucher_system.application.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangerine.voucher_system.application.customer.controller.dto.CreateCustomerRequest;
import com.tangerine.voucher_system.application.customer.controller.dto.CustomerResponse;
import com.tangerine.voucher_system.application.customer.controller.dto.UpdateCustomerRequest;
import com.tangerine.voucher_system.application.customer.model.Name;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import com.tangerine.voucher_system.application.customer.service.mapper.CustomerServiceMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerRestControllerTest {

    @InjectMocks
    CustomerRestController controller;

    @Mock
    CustomerService service;

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 고객을 추가하면 성공한다.")
    @MethodSource("provideCreateCustomerRequests")
    void registerCustomer_ParamCreateCustomerRequests_InsertAndReturnCustomerResponse(CreateCustomerRequest request, CustomerResult result) throws Exception {
        given(service.createCustomer(any())).willReturn(result);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(request.name()));

        verify(service, times(1)).createCustomer(any());
    }

    @Test
    @DisplayName("진상고객 정보를 리스트로 반환하면 성공한다.")
    void blackCustomerList_ParamVoid_ReturnCustomerResponseList() throws Exception {
        given(service.findBlackCustomers()).willReturn(blackCustomerResults);

        mockMvc.perform(get("/api/v1/customers/black")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerId").value(blackCustomerResults.get(0).customerId().toString()));

        verify(service, times(1)).findBlackCustomers();
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 업데이트하면 성공한다.")
    @MethodSource("provideUpdateCustomerRequests")
    void updateCustomer_ParamExistCustomerDto_UpdateCustomerDto(UpdateCustomerRequest request, CustomerResult result) throws Exception {
        given(service.updateCustomer(any())).willReturn(result);

        mockMvc.perform(patch("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(request.name()));

        verify(service, times(1)).updateCustomer(any());
    }

    @Test
    @DisplayName("모든 고객을 반환하면 성공한다.")
    void customerList_ParamVoid_ReturnCustomerDtoList() throws Exception {
        given(service.findAllCustomers()).willReturn(customerResults);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerId").value(customerResults.get(0).customerId().toString()));

        verify(service, times(1)).findAllCustomers();
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 조회 시 성공한다.")
    @MethodSource("provideCustomerResults")
    void customerById_ParamExistCustomerDto_ReturnCustomerDto(CustomerResult result) throws Exception {
        given(service.findCustomerById(any())).willReturn(result);

        mockMvc.perform(get("/api/v1/customers/" + result.customerId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(result.customerId().toString()))
                .andExpect(jsonPath("$.name").value(result.name().getValue()));

        verify(service, times(1)).findCustomerById(any());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 이름으로 조회 시 성공한다.")
    @MethodSource("provideCustomerResults")
    void customerByName_ParamExistCustomerDto_ReturnCustomerDto(CustomerResult result) throws Exception {
        given(service.findCustomerByName(any())).willReturn(result);

        mockMvc.perform(get("/api/v1/customers/name")
                        .param("name", result.name().getValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(result.customerId().toString()))
                .andExpect(jsonPath("$.name").value(result.name().getValue()));

        verify(service, times(1)).findCustomerByName(any());
    }

    @ParameterizedTest
    @DisplayName("존재하는 고객을 아이디로 제거하면 성공한다.")
    @MethodSource("provideCustomerResults")
    void unregisterCustomerById_ParamExistCustomer_ReturnAndDeleteCustomer(CustomerResult result) throws Exception {
        given(service.deleteCustomerById(any())).willReturn(result);

        mockMvc.perform(delete("/api/v1/customers")
                .param("id", result.customerId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(result.customerId().toString()))
                .andExpect(jsonPath("$.name").value(result.name().getValue()));

        verify(service, times(1)).deleteCustomerById(result.customerId());
    }

    static Stream<Arguments> provideCreateCustomerRequests() {
        return IntStream.range(0, customerResults.size())
                .mapToObj(i -> Arguments.of(createCustomerRequests.get(i), customerResults.get(i)));
    }

    static Stream<Arguments> provideUpdateCustomerRequests() {
        return IntStream.range(0, customerResults.size())
                .mapToObj(i -> Arguments.of(updateCustomerRequests.get(i), customerResults.get(i)));
    }

    static Stream<Arguments> provideCustomerResults() {
        return customerResults.stream()
                .map(Arguments::of);
    }

    static List<CustomerResult> blackCustomerResults = List.of(
            new CustomerResult(UUID.randomUUID(), new Name("상한사과"), true),
            new CustomerResult(UUID.randomUUID(), new Name("맛없는딸기"), true)
    );

    static List<CustomerResult> customerResults = List.of(
            new CustomerResult(UUID.randomUUID(), new Name("사과"), false),
            new CustomerResult(UUID.randomUUID(), new Name("딸기"), true),
            new CustomerResult(UUID.randomUUID(), new Name("포도"), false),
            new CustomerResult(UUID.randomUUID(), new Name("배"), false)
    );

    static List<CreateCustomerRequest> createCustomerRequests = customerResults.stream()
            .map(result -> new CreateCustomerRequest(result.name().getValue(), result.isBlack()))
            .toList();

    static List<UpdateCustomerRequest> updateCustomerRequests = customerResults.stream()
            .map(result -> new UpdateCustomerRequest(result.customerId(), result.name().getValue(), result.isBlack()))
            .toList();

}