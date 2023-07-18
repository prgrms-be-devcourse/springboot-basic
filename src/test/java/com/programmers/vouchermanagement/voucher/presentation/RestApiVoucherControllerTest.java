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
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Voucher voucher = Voucher.from(new FixedAmountDiscountPolicy(5000));
        VoucherResponse response = new VoucherResponse(voucher);

        given(voucherService.createVoucher(request))
                .willReturn(response);

        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);

        // when & then
        this.mockMvc.perform(post("/api/v3/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(responseJson));
    }

    @Test
    @DisplayName("바우처 목록 조회 API")
    void getVouchers() throws Exception {
        // given
        Voucher voucher = Voucher.from(new FixedAmountDiscountPolicy(5000));
        List<VoucherResponse> responses = List.of(new VoucherResponse(voucher));
        given(voucherService.getVouchers())
                .willReturn(responses);

        String responsesJson = objectMapper.writeValueAsString(responses);

        // when & then
        this.mockMvc.perform(get("/api/v3/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().string(responsesJson));
    }

    @Test
    @DisplayName("바우처 조회 API")
    void getVoucher() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.of(voucherId, new FixedAmountDiscountPolicy(5000));
        VoucherResponse response = new VoucherResponse(voucher);

        given(voucherService.getVoucher(voucherId))
                .willReturn(response);

        String responseJson = objectMapper.writeValueAsString(response);

        // when & then
        this.mockMvc.perform(get("/api/v3/vouchers/{voucherId}", voucherId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(responseJson));
    }

    @Test
    @DisplayName("바우처 수정 API")
    void updateVoucher() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();
        VoucherUpdateRequest request = new VoucherUpdateRequest(DiscountType.FIX, 5000);

        String requestJson = objectMapper.writeValueAsString(request);

        // when & then
        this.mockMvc.perform(post("/api/v3/vouchers/{voucherId}", voucherId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // verify
        verify(voucherService).updateVoucher(voucherId, request);
    }

    @Test
    @DisplayName("바우처 삭제 API")
    void deleteVoucher() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();

        // when & then
        this.mockMvc.perform(delete("/api/v3/vouchers/{voucherId}", voucherId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // verify
        verify(voucherService).deleteVoucher(voucherId);
    }
}