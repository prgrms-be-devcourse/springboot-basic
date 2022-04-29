package org.prgrms.springbootbasic.controller.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = WebVoucherController.class)
class WebVoucherControllerTest {

    @MockBean
    VoucherService voucherService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("바우처 전체 조회 페이지 조회")
    void testViewVouchersPage() throws Exception {
        //given
        var vouchers = List.of(
            new FixedAmountVoucher(UUID.randomUUID(), 1000),
            new PercentDiscountVoucher(UUID.randomUUID(), 20));
        given(voucherService.findAll()).willReturn(vouchers);

        //when
        //then
        mockMvc.perform(get("/vouchers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
            .andExpect(model().attributeExists("voucherDTOs"))
            .andExpect(view().name("voucher/vouchers"));
    }

    @Test
    @DisplayName("바우처 상세 페이지 조회")
    void testViewVoucherPage() throws Exception {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        given(voucherService.findVoucher(voucher.getVoucherId())).willReturn(voucher);

        //when
        //then
        mockMvc.perform(get("/vouchers/" + voucher.getVoucherId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
            .andExpect(model().attributeExists("voucherDTO"))
            .andExpect(view().name("voucher/voucher"));
    }

    @Test
    @DisplayName("바우처 등록 페이지 조회")
    void testViewNewVoucherPage() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/vouchers/new"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML + ";charset=UTF-8"))
            .andExpect(model().attributeExists("createVoucherRequest"))
            .andExpect(view().name("voucher/new-voucher"));
    }

    @Test
    @DisplayName("바우처 등록 요청")
    void testCreateVoucher() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/vouchers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("voucherType", "fixed")
                .param("amount", "1000"))
            .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처 삭제 요청")
    void testDeleteVoucher() throws Exception {
        //given
        var voucherId = UUID.randomUUID();
        given(voucherService.deleteVoucher(voucherId)).willReturn(voucherId);

        //when
        //then
        mockMvc.perform(post("/vouchers/" + voucherId.toString() + "/delete"))
            .andExpect(redirectedUrl("/vouchers"));
    }
}