package org.programmers.kdtspring.controller.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VoucherController.class)
class VoucherControllerTest {

    @MockBean
    VoucherService voucherService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("바우처 전체 조회 페이지")
    void testVouchersPage() throws Exception {
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.FixedAmountVoucher.name()),
                new PercentDiscountVoucher(UUID.randomUUID(), 10, VoucherType.PercentDiscountVoucher.name())
        );
        given(voucherService.getVouchers()).willReturn(vouchers);

        mockMvc.perform(get("/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(model().attributeExists("vouchers"))
                .andExpect(view().name("vouchers"));
    }

    @Test
    @DisplayName("바우처 상세 페이지 조회")
    void testVoucherDetail() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.FixedAmountVoucher.name());
        given(voucherService.getVoucher(voucher.getVoucherId())).willReturn(Optional.of(voucher));

        mockMvc.perform(get("/vouchers/" + voucher.getVoucherId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(model().attributeExists("voucher"))
                .andExpect(view().name("voucherDetail"));
    }

    @Test
    @DisplayName("바우처 생성 폼 페이지 조회")
    void testCreateNewVoucherPage() throws Exception {
        mockMvc.perform(get("/vouchers/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
                .andExpect(model().attributeExists("createVoucherRequest"))
                .andExpect(view().name("voucherCreateForm"));
    }

    @Test
    @DisplayName("바우처 생성 요청")
    void testCreateNewVoucher() throws Exception {
        mockMvc.perform(post("/vouchers/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("voucherType", "FixedAmountVoucher")
                        .param("amount", "10000"))
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 삭제 요청")
    void testDeleteVoucher() throws Exception {
        UUID voucherId = UUID.randomUUID();
        given(voucherService.removeVoucher(voucherId)).willReturn(voucherId);

        mockMvc.perform(post("/vouchers/" + voucherId.toString() + "/delete"))
                .andExpect(redirectedUrl("/vouchers"));
    }
}