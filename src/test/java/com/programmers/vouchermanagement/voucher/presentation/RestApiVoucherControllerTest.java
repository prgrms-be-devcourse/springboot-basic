package com.programmers.vouchermanagement.voucher.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.FixedAmountDiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import com.programmers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestApiVoucherController.class)
class RestApiVoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoucherService voucherService;

    @Test
    @DisplayName("바우처 생성 API")
    void createVoucher() throws Exception {
        // given
        VoucherCreationRequest request = new VoucherCreationRequest(DiscountType.FIX, 5000);
        VoucherResponse response = new VoucherResponse(new Voucher(new FixedAmountDiscountPolicy(5000)));
        given(voucherService.createVoucher(any(VoucherCreationRequest.class)))
                .willReturn(response);

        String requestJson = objectMapper.writeValueAsString(request);

        // when & then
        this.mockMvc.perform(post("/api/v3/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.type").value(response.getType().toString()))
                .andExpect(jsonPath("$.amount").value(response.getAmount()));
    }

    @Test
    @DisplayName("바우처 목록 조회 API")
    void getVouchers() throws Exception {
        // given
        VoucherResponse response = new VoucherResponse(new Voucher(new FixedAmountDiscountPolicy(5000)));
        given(voucherService.getVouchers())
                .willReturn(List.of(response));

        // when & then
        this.mockMvc.perform(get("/api/v3/vouchers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(response.getId().toString()))
                .andExpect(jsonPath("$.[0].type").value(response.getType().toString()))
                .andExpect(jsonPath("$.[0].amount").value(response.getAmount()));
    }

    @Test
    @DisplayName("바우처 조회 API")
    void getVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));
        VoucherResponse response = new VoucherResponse(voucher);
        given(voucherService.getVoucher(voucher.getId()))
                .willReturn(response);

        // when & then
        this.mockMvc.perform(get("/api/v3/vouchers/{voucherId}", voucher.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.type").value(response.getType().toString()))
                .andExpect(jsonPath("$.amount").value(response.getAmount()));
    }

    @Test
    @DisplayName("바우처 수정 API")
    void updateVoucher() throws Exception {
        // given
        VoucherUpdateRequest request = new VoucherUpdateRequest(DiscountType.FIX, 5000);
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));

        String requestJson = objectMapper.writeValueAsString(request);

        // when & then
        this.mockMvc.perform(post("/api/v3/vouchers/{voucherId}", voucher.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("바우처 삭제 API")
    void deleteVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher(new FixedAmountDiscountPolicy(5000));

        // when & then
        this.mockMvc.perform(delete("/api/v3/vouchers/{voucherId}", voucher.getId().toString()))
                .andExpect(status().isNoContent());
    }
}