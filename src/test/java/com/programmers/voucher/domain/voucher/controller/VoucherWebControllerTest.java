package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherWebController.class)
class VoucherWebControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VoucherService voucherService;

    @Test
    @DisplayName("성공: voucher 목록 화면")
    void findVouchers() throws Exception {
        //given
        VoucherDto percentVoucherDto = createPercentVoucherDto(UUID.randomUUID(), 10);
        VoucherDto fixedVoucherDto = createFixedVoucherDto(UUID.randomUUID(), 10);
        List<VoucherDto> voucherDtos = List.of(percentVoucherDto, fixedVoucherDto);

        given(voucherService.findVouchers(any(), any(), any())).willReturn(voucherDtos);

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/vouchers"));

        //then
        resultActions
                .andExpect(view().name("vouchers/voucher-list"))
                .andExpect(model().attribute("vouchers", voucherDtos));
    }

    @Test
    @DisplayName("성공: voucher 생성 요청")
    void createVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        given(voucherService.createVoucher(any(), anyLong())).willReturn(voucherId);

        //when
        ResultActions resultActions = mvc.perform(post("/vouchers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("voucherType=FIXED_AMOUNT&amount=10"));

        //then
        resultActions
                .andExpect(redirectedUrl("/vouchers/" + voucherId));
    }

    @Test
    @DisplayName("성공: voucher 단건 조회")
    void findVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherDto fixedVoucherDto = createFixedVoucherDto(voucherId, 10);

        given(voucherService.findVoucher(any())).willReturn(fixedVoucherDto);

        //when
        ResultActions resultActions = mvc.perform(get("/vouchers/" + voucherId));

        //then
        resultActions
                .andExpect(view().name("vouchers/voucher"))
                .andExpect(model().attribute("voucher", fixedVoucherDto));
    }

    @Test
    @DisplayName("성공: voucher 삭제 요청")
    void deleteVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        ResultActions resultActions = mvc.perform(post("/vouchers/" + voucherId + "/delete"));

        //then
        resultActions
                .andExpect(redirectedUrl("/vouchers"));
    }
}