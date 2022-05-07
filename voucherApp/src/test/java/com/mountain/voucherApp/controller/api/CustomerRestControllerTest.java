package com.mountain.voucherApp.controller.api;

import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.model.vo.CustomerName;
import com.mountain.voucherApp.model.vo.Email;
import com.mountain.voucherApp.service.VoucherAppService;
import com.mountain.voucherApp.service.customer.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerRestController.class)
@AutoConfigureMockMvc
class CustomerRestControllerTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private VoucherAppService voucherAppService;
    @Autowired
    MockMvc mockMvc;

    private void validateCustomerDto(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(jsonPath("$.[0].customerId").exists())
                .andExpect(jsonPath("$.[0].voucherId").exists())
                .andExpect(jsonPath("$.[0].customerName").exists())
                .andExpect(jsonPath("$.[0].email").exists())
                .andExpect(jsonPath("$.[0].lastLoginAt").exists())
                .andExpect(jsonPath("$.[0].createdAt").exists())
        ;
    }
    @Test
    @DisplayName("등록된 고객을 조회할 수 있다 - JSON.")
    void testCustomersAPI() throws Exception {
        List<CustomerDto> customers = List.of(
                new CustomerDto(UUID.randomUUID(), UUID.randomUUID(), new CustomerName("name"), new Email("name@email.com"), LocalDateTime.now(), LocalDateTime.now()),
                new CustomerDto(UUID.randomUUID(), UUID.randomUUID(), new CustomerName("name2"), new Email("name2@email.com"), LocalDateTime.now(), LocalDateTime.now())
        );
        given(customerService.findAll()).willReturn(customers);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
        );

        validateCustomerDto(resultActions);
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("등록된 고객을 조회할 수 있다 - XML.")
    void testCustomersXmlAPI() throws Exception {
        List<CustomerDto> customers = List.of(
                new CustomerDto(UUID.randomUUID(), UUID.randomUUID(), new CustomerName("name"), new Email("name@email.com"), LocalDateTime.now(), LocalDateTime.now()),
                new CustomerDto(UUID.randomUUID(), UUID.randomUUID(), new CustomerName("name2"), new Email("name2@email.com"), LocalDateTime.now(), LocalDateTime.now())
        );
        given(customerService.findAll()).willReturn(customers);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/customers")
                .accept(MediaType.APPLICATION_XML)
        );
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML.toString()))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("VoucherID별로 고객리스트를 조회할 수 있다. - JSON")
    void testCustomersByVoucherIdAPI() throws Exception {
        UUID voucherId = UUID.randomUUID();
        List<CustomerDto> customers = List.of(
                new CustomerDto(UUID.randomUUID(), voucherId, new CustomerName("name"), new Email("name@email.com"), LocalDateTime.now(), LocalDateTime.now()),
                new CustomerDto(UUID.randomUUID(), voucherId, new CustomerName("name2"), new Email("name2@email.com"), LocalDateTime.now(), LocalDateTime.now())
        );
        given(voucherAppService.showByVoucher(voucherId)).willReturn(customers);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/customers/{voucherId}", voucherId.toString())
                .accept(MediaType.APPLICATION_JSON)
        );
        validateCustomerDto(resultActions);
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("VoucherID별로 고객리스트를 조회할 수 있다. - XML")
    void testCustomersByVoucherIdXmlAPI() throws Exception {
        UUID voucherId = UUID.randomUUID();
        List<CustomerDto> customers = List.of(
                new CustomerDto(UUID.randomUUID(), voucherId, new CustomerName("name"), new Email("name@email.com"), LocalDateTime.now(), LocalDateTime.now())
        );
        given(customerService.findAll()).willReturn(customers);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/customers/{voucherId}", voucherId.toString())
                .accept(MediaType.APPLICATION_XML)

        );
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML.toString()))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }


}