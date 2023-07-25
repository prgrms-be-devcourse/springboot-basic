package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucherDto;
import static com.programmers.voucher.testutil.VoucherTestUtil.createPercentVoucherDto;
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
        ResultActions resultActions = mvc.perform(get("/vouchers"));

        //then
        resultActions
                .andExpect(view().name("vouchers/voucher-list"))
                .andExpect(model().attribute("vouchers", voucherDtos));
    }

    @Test
    @DisplayName("예외: voucher 목록 화면 - 검색 시작 시간이 종료 시간 이후")
    void findVouchers_ButSearchStartTimeIsAfterEndTime_Then_ErrorPage() throws Exception {
        //given
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.plusHours(1);

        //when
        ResultActions resultActions = mvc.perform(get("/vouchers")
                .param("startTime", startTime.toString())
                .param("endTime", endTime.toString()));

        //then
        resultActions
                .andExpect(view().name("errorPage"))
                .andExpect(model().attributeExists("errorResult"));
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

    @ParameterizedTest
    @CsvSource({
            "-2", "-1", "0"
    })
    @DisplayName("예외: voucher 생성 요청 - 잘못된 범위의 Fixed Amount")
    void createVoucher_ButInvalidFixedAmount_Then_ErrorPage(String invalidAmount) throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(post("/vouchers/new")
                        .param("voucherType", VoucherType.FIXED_AMOUNT.name())
                        .param("amount", invalidAmount)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        //then
        resultActions.andExpect(view().name("errorPage"))
                .andExpect(model().attributeExists("errorResult"));
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "0", "100", "101"
    })
    @DisplayName("예외: voucher 생성 요청 - 잘못된 범위의 Percent")
    void createVoucher_ButInvalidPercent_Then_ErrorPage(String invalidPercent) throws Exception {
        //given
        //when
        ResultActions resultActions = mvc.perform(post("/vouchers/new")
                        .param("voucherType", VoucherType.PERCENT.name())
                        .param("amount", invalidPercent)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        //then
        resultActions.andExpect(view().name("errorPage"))
                .andExpect(model().attributeExists("errorResult"));
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