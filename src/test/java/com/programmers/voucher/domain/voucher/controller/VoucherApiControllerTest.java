package com.programmers.voucher.domain.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.request.VoucherSearchRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherApiController.class)
class VoucherApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private VoucherService voucherService;

    @Test
    @DisplayName("성공: voucher 목록 조회 요청")
    void findVouchers() throws Exception {
        //given
        VoucherSearchRequest request = new VoucherSearchRequest(null, null, null);
        VoucherDto fixedVoucher = createFixedVoucherDto();
        VoucherDto percentVoucher = createPercentVoucherDto();
        List<VoucherDto> vouchers = List.of(fixedVoucher, percentVoucher);

        given(voucherService.findVouchers(any(), any(), any())).willReturn(vouchers);
        String jsonRequestPayload = mapper.writeValueAsString(request);
        String jsonResponsePayload = mapper.writeValueAsString(vouchers);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponsePayload));
    }

    @Test
    @DisplayName("성공(400): voucher 목록 조회 요청 - 검색 시작 시간이 종료 시가 이후")
    void findVouchers_ButSearchStartTimeIsAfterEndTime_Then_BadRequest() throws Exception {
        //given
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.plusHours(1);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/vouchers")
                        .param("startTime", startTime.toString())
                        .param("endTime", endTime.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("성공: voucher 단건 조회 요청")
    void findVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherDto fixedVoucher = createFixedVoucherDto();

        given(voucherService.findVoucher(any())).willReturn(fixedVoucher);
        String jsonResponsePayload = mapper.writeValueAsString(fixedVoucher);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/vouchers/" + voucherId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponsePayload));
    }

    @Test
    @DisplayName("성공: voucher 단건 생성 요청")
    void createVoucher() throws Exception {
        //given
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10);
        UUID voucherId = UUID.randomUUID();

        given(voucherService.createVoucher(any(), anyLong())).willReturn(voucherId);
        String jsonRequestPayload = mapper.writeValueAsString(request);
        String jsonResponsePayload = mapper.writeValueAsString(voucherId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponsePayload));
    }

    @ParameterizedTest
    @CsvSource({
            "-2", "-1", "0"
    })
    @DisplayName("성공(400): voucher 단건 생성 요청")
    void createVoucher_ButInvalidFixedAmount_Then_BadRequest(String amount) throws Exception {
        //given
        long invalidAmount = Long.parseLong(amount);
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, invalidAmount);
        UUID voucherId = UUID.randomUUID();

        given(voucherService.createVoucher(any(), anyLong())).willReturn(voucherId);
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").isString());
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "0", "100", "101"
    })
    @DisplayName("성공(400): voucher 단건 생성 요청")
    void createVoucher_ButInvalidPercent_Then_BadRequest(String percent) throws Exception {
        //given
        long invalidPercent = Long.parseLong(percent);
        VoucherCreateRequest request = new VoucherCreateRequest(VoucherType.PERCENT, invalidPercent);
        UUID voucherId = UUID.randomUUID();

        given(voucherService.createVoucher(any(), anyLong())).willReturn(voucherId);
        String jsonRequestPayload = mapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제 요청")
    void deleteVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/vouchers/" + voucherId))
                .andDo(print());

        //then
        resultActions.andExpect(status().isNoContent());
    }
}