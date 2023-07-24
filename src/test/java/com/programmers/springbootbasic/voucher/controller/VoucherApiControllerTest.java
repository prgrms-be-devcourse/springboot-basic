package com.programmers.springbootbasic.voucher.controller;

import com.programmers.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.voucher.domain.VoucherType;
import com.programmers.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import com.programmers.springbootbasic.voucher.dto.VoucherDto;
import com.programmers.springbootbasic.voucher.dto.VouchersResponseDto;
import com.programmers.springbootbasic.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherApiController.class)
class VoucherApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @DisplayName("바우처 목록을 조회한다")
    @Test
    public void list() throws Exception {
        //given
        VouchersResponseDto responseDto = new VouchersResponseDto(Collections.emptyList());
        when(voucherService.findAll()).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/voucher/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vouchers").isArray());
    }

    @DisplayName("바우처를 생성한다")
    @Test
    public void save() throws Exception {
        //given
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto("voucher1", 10L, VoucherType.FixedAmountVoucher);
        VoucherDto voucherDto = new VoucherDto(UUID.randomUUID(), "voucher1", 10L, VoucherType.FixedAmountVoucher, Optional.empty());

        when(voucherService.save(voucherCreateRequestDto)).thenReturn(voucherDto);

        //when
        //then
        mockMvc.perform(post("/api/voucher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"voucher1\",\"type\":\"FixedAmountVoucher\",\"value\":10}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("voucher1"))
                .andExpect(jsonPath("$.type").value("FixedAmountVoucher"))
                .andExpect(jsonPath("$.value").value(10));
    }

    @DisplayName("바우처의 상세 정보를 조회한다")
    @Test
    public void detail() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherDto voucherDto = new VoucherDto(voucherId, "voucher1", 10L, VoucherType.FixedAmountVoucher, Optional.empty());

        when(voucherService.findById(voucherId)).thenReturn(voucherDto);

        //when
        //then
        mockMvc.perform(get("/api/voucher/detail/{id}", voucherId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(voucherId.toString()))
                .andExpect(jsonPath("$.name").value("voucher1"))
                .andExpect(jsonPath("$.type").value("FixedAmountVoucher"))
                .andExpect(jsonPath("$.value").value(10));
    }

    @DisplayName("타입 별로 바우처를 조회한다")
    @Test
    public void findByType() throws Exception {
        //given
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), "voucher1", 10L);
        FixedAmountVoucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), "voucher2", 11L);
        VouchersResponseDto responseDto = new VouchersResponseDto(List.of(voucher1, voucher2));

        when(voucherService.findByType(VoucherType.FixedAmountVoucher)).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/voucher/list/search")
                        .param("type", "FixedAmountVoucher"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vouchers").isArray())
                .andExpect(jsonPath("$.vouchers[0].voucherId").value(voucher1.getVoucherId().toString()))
                .andExpect(jsonPath("$.vouchers[0].voucherName").value("voucher1"))
                .andExpect(jsonPath("$.vouchers[0].voucherType").value("FixedAmountVoucher"))
                .andExpect(jsonPath("$.vouchers[0].voucherValue").value(10));
    }

    @DisplayName("바우처를 수정한다")
    @Test
    public void update() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();

        VoucherDto voucherDto = new VoucherDto(voucherId, "voucher1", 10L, VoucherType.FixedAmountVoucher, Optional.empty());

        when(voucherService.update(voucherDto)).thenReturn(voucherDto);

        //when
        //then
        mockMvc.perform(put("/api/voucher/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + voucherId + "\",\"type\":\"FixedAmountVoucher\",\"value\":20}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(voucherId.toString()))
                .andExpect(jsonPath("$.type").value("FixedAmountVoucher"))
                .andExpect(jsonPath("$.value").value(20));
    }

    @DisplayName("바우처를 삭제한다")
    @Test
    public void deleteById() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/voucher/delete/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("모든 바우처를 삭제한다")
    @Test
    public void deleteAll() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/voucher/deleteAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("All vouchers have been deleted."));
    }
}