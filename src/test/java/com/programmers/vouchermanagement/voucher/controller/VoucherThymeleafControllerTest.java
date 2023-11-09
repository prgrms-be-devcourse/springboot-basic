package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.programmers.vouchermanagement.voucher.controller.MvcControllerResource.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherThymeleafController.class)
@ActiveProfiles("thyme")
class VoucherThymeleafControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    VoucherService voucherService;

    @Test
    @DisplayName("바우처 생성을 요청한다. 그리고 vouchers 페이지로 이동한다.")
    void createVoucher() throws Exception {
        when(voucherService.create(CREATE_VOUCHER_REQUEST)).thenReturn(VOUCHER_RESPONSE);

        mockMvc.perform(post("/vouchers/new")
                        .param("typeName", CREATE_VOUCHER_REQUEST.typeName())
                        .param("discountValue", Long.toString(CREATE_VOUCHER_REQUEST.discountValue())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 생성 페이지를 요청한다.")
    void viewCreatePage() throws Exception {
        mockMvc.perform(get("/vouchers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("vouchers/voucher-new"));
    }

    @Test
    @DisplayName("모든 바우처 조회 페이지를 요청한다.")
    void viewVouchersPage() throws Exception {
        when(voucherService.readAll()).thenReturn(VOUCHERS);

        mockMvc.perform(get("/vouchers"))
                .andExpect(status().isOk())
                .andExpect(view().name("voucher/vouchers"))
                .andExpect(model().attribute("vouchers", VOUCHERS));
    }

    @Test
    @DisplayName("id별 바우처 상세 페이지를 요청한다.")
    void viewVoucherByIdPage() throws Exception {
        when(voucherService.readById(VOUCHER_ID)).thenReturn(VOUCHER_RESPONSE);

        mockMvc.perform(get("/vouchers/" + VOUCHER_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("voucher/voucher-detail"))
                .andExpect(model().attribute("voucher", VOUCHER_RESPONSE));
    }

    @Test
    @DisplayName("바우처 삭제를 요청한다.")
    void deleteVoucher() throws Exception {
        mockMvc.perform(delete("/vouchers/" + VOUCHER_ID.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 업데이트를 요청한다.")
    void update() throws Exception {
        mockMvc.perform(put("/vouchers/update/" + VOUCHER_ID.toString())
                        .param("typeName", CREATE_VOUCHER_REQUEST.typeName())
                        .param("discountValue", Long.toString(CREATE_VOUCHER_REQUEST.discountValue())))                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 업데이트 페이지를 요청한다.")
    void viewUpdatePage() throws Exception {
        when(voucherService.readById(VOUCHER_ID)).thenReturn(VOUCHER_RESPONSE);

        mockMvc.perform(get("/vouchers/update/" + VOUCHER_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("voucher/voucher-update"))
                .andExpect(model().attribute("voucher", VOUCHER_RESPONSE));
    }
}