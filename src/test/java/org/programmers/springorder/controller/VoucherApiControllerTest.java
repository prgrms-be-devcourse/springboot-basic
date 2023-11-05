package org.programmers.springorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springorder.controller.voucher.VoucherApiController;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherApiController.class)
class VoucherApiControllerTest {

    @MockBean
    VoucherService voucherService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("전체 바우처 조회에 성공한다.")
    void findAllVoucher() throws Exception {
        // given
        Voucher voucher = Voucher.toVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED);
        List<VoucherResponseDto> vouchers = List.of(VoucherResponseDto.of(voucher));

        // when
        when(voucherService.getAllVoucher()).thenReturn(vouchers);

        // then
        mockMvc.perform(get("/api/vouchers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].voucherType").value(VoucherType.FIXED.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].discountValue").value(1000));
    }

    @Test
    @DisplayName("바우처 ID로 조회에 성공한다.")
    void findVoucher() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = Voucher.toVoucher(voucherId, 1000, VoucherType.FIXED);

        // when
        when(voucherService.findById(voucherId)).thenReturn(voucher);

        // then
        mockMvc.perform(get("/api/vouchers/{id}", voucherId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.voucherId").value(String.valueOf(voucherId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.voucherType").value(VoucherType.FIXED.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discountValue").value(1000));
    }

    @Test
    @DisplayName("바우처 티입 조회에 성공한다.")
    void findVoucherByType() throws Exception {
        // given
        VoucherType voucherType = VoucherType.FIXED;
        Voucher voucher = Voucher.toVoucher(UUID.randomUUID(), 1000, VoucherType.FIXED);
        List<VoucherResponseDto> vouchers = List.of(
                VoucherResponseDto.of(voucher)
        );

        // when
        when(voucherService.findByType(voucherType)).thenReturn(vouchers);

        // then
        mockMvc.perform(get("/api/vouchers/type?type={type}", voucherType))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].voucherType").value(voucherType.name()));
    }

    @Test
    @DisplayName("바우처 삭제에 성공한다.")
    void deleteVoucher() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();

        // then
        mockMvc.perform(delete("/api/vouchers/{id}", voucherId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("삭제가 완료되었습니다."));
    }
}