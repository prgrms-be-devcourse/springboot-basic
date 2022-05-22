package org.prgms.voucheradmin.domain.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucheradmin.domain.voucher.dto.VoucherReqDto;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;
import org.prgms.voucheradmin.global.exception.customexception.VoucherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherController.class)
class VoucherControllerTest {
    @MockBean
    private VoucherService voucherService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private VoucherReqDto voucherReqDto = new VoucherReqDto(VoucherType.FIXED_AMOUNT, 1000);
    private Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000, LocalDateTime.now());

    @Test
    @DisplayName("바우처 추가를 테스트합니다.")
    void testAddVoucher() throws Exception{
        when(voucherService.createVoucher(voucherReqDto)).thenReturn(fixedAmountVoucher);


        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voucherReqDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("바우처 단건 조회를 테스트합니다.")
    void testGetVoucher() throws Exception {
        when(voucherService.getVoucher(fixedAmountVoucher.getVoucherId())).thenReturn(fixedAmountVoucher);

        mockMvc.perform(get("/api/v1/vouchers/"+fixedAmountVoucher.getVoucherId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("ID에 해당하는 바우처가 존재하지 않는 경우를 테스트합니다.")
    void testGetVoucherThrowVoucherNotFoundException() throws Exception {
        when(voucherService.getVoucher(fixedAmountVoucher.getVoucherId()))
                .thenThrow(new VoucherNotFoundException(fixedAmountVoucher.getVoucherId()));

        mockMvc.perform(get("/api/v1/vouchers/"+fixedAmountVoucher.getVoucherId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("바우처 조회 목록을 테스트합니다.")
    void testGetVouchers() throws Exception {
        when(voucherService.getVouchers())
                .thenReturn(Collections.singletonList(fixedAmountVoucher));

        mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("바우처 삭제를 테스트합니다.")
    void testDeleteVoucher() throws Exception {
        mockMvc.perform(delete("/api/v1/vouchers/"+fixedAmountVoucher.getVoucherId()))
                .andExpect(status().isOk());
    }
}