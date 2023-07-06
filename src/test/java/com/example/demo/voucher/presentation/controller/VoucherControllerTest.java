package com.example.demo.voucher.presentation.controller;

import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.presentation.controller.dto.CreateVoucherForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherController.class)
@SpringJUnitWebConfig
class VoucherControllerTest {

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
    void findAll() throws Exception{
        // Given
        when(voucherService.listVouchers()).thenReturn(new ArrayList<>());

        // When, Then
        mockMvc.perform(get("/vouchers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
        ;
    }

    @Test
    void findById() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        long value = 10L;
        Voucher voucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.createVoucher(id, value);
        when(voucherService.getVoucher(id)).thenReturn(voucher);

        // When, Then
        mockMvc.perform(get("/vouchers/" + id.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("voucher"))
                .andExpect(view().name("voucher/details"));
    }

    @Test
    void viewNewVoucherPage() throws Exception {
        //When, Then
        mockMvc.perform(get("/vouchers/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("voucher/new"));
    }

    @Test
    void createVoucher() throws Exception {
        //Given
        long value = 10L;
        String voucherType = VoucherType.FIXED_AMOUNT_VOUCHER.name();

        CreateVoucherForm createVoucherForm = new CreateVoucherForm(voucherType, value);

        doNothing().when(voucherService).createVoucher(createVoucherForm.voucherType(), createVoucherForm.value());

        mockMvc.perform(post("/vouchers/new")
                        .param("voucherType", voucherType)
                        .param("value", String.valueOf(value)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));

        verify(voucherService).createVoucher(voucherType, value);
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(voucherService).deleteAll();
        mockMvc.perform(delete("/vouchers"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
