package com.example.demo.voucher.presentation.controller;

import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.presentation.controller.dto.CreateVoucherForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherRestController.class)
@SpringJUnitWebConfig(VoucherRestControllerTest.Config.class)
class VoucherRestControllerTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.example.demo.voucher.presentation.controller"}
    )
    static class Config {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @Test
    void findAll() throws Exception {
        // Given
        when(voucherService.listVouchers()).thenReturn(new ArrayList<>());

        // When, Then
        mockMvc.perform(get("/api/v1/vouchers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

    }

    @Test
    void findById() throws Exception {
        // Given
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        Voucher voucher = VoucherType.FIXED_AMOUNT_VOUCHER.createVoucher(uuid, 10L);
        when(voucherService.getVoucher(any(UUID.class))).thenReturn(voucher);

        // When, Then
        mockMvc.perform(get("/api/v1/vouchers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(voucher.getType().name()))
                .andExpect(jsonPath("$.value").value(voucher.getValue()));
    }

    @Test
    void createVoucher() throws Exception {
        // Given
        CreateVoucherForm voucherForm = new CreateVoucherForm("type", 10L);
        ObjectMapper objectMapper = new ObjectMapper();
        String voucherFormJson = objectMapper.writeValueAsString(voucherForm);

        // When, Then
        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voucherFormJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void deleteAll() throws Exception {
        // When, Then
        mockMvc.perform(delete("/api/v1/vouchers"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
