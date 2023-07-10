package com.programmers.voucher.domain.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.dto.request.VoucherSearchRequest;
import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.testutil.VoucherTestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        VoucherDto fixedVoucher = VoucherTestUtil.createFixedVoucherDto(UUID.randomUUID(), 10);
        VoucherDto percentVoucher = VoucherTestUtil.createPercentVoucherDto(UUID.randomUUID(), 10);
        List<VoucherDto> vouchers = List.of(fixedVoucher, percentVoucher);

        given(voucherService.findVouchers(any(), any(), any())).willReturn(vouchers);
        String jsonRequestPayload = mapper.writeValueAsString(request);
        String jsonResponsePayload = mapper.writeValueAsString(vouchers);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload))
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponsePayload));
    }

    @Test
    @DisplayName("성공: voucher 단건 조회 요청")
    void findVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherDto fixedVoucher = VoucherTestUtil.createFixedVoucherDto(voucherId, 10);

        given(voucherService.findVoucher(any())).willReturn(fixedVoucher);
        String jsonResponsePayload = mapper.writeValueAsString(fixedVoucher);

        //when
        ResultActions resultActions = mvc.perform(get("/api/v1/vouchers/" + voucherId))
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
                        .content(jsonRequestPayload))
                .andDo(print());

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonResponsePayload));
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제 요청")
    void deleteVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        String jsonRequestPayload = mapper.writeValueAsString(voucherId);

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/vouchers/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestPayload))
                .andDo(print());

        //then
        resultActions.andExpect(status().isNoContent());
    }
}