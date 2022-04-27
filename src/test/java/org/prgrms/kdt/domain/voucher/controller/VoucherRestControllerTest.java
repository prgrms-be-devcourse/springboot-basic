package org.prgrms.kdt.domain.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.request.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.prgrms.kdt.domain.voucher.model.VoucherType.FIXED_AMOUNT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(VoucherRestController.class)
class VoucherRestControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VoucherRestController(voucherService))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("바우처 전체 조회 요청을 처리할 수 있다.")
    void voucherList() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID firstVoucherId = UUID.randomUUID();
        UUID secondVoucherId = UUID.randomUUID();
        List<Voucher> savedVouchers = Arrays.asList(
                new Voucher(firstVoucherId, FIXED_AMOUNT, 10000L, now, now),
                new Voucher(secondVoucherId, VoucherType.PERCENT_DISCOUNT, 10L, now, now));
        //when
        when(voucherService.getAllVouchers()).thenReturn(savedVouchers);
        //then
        mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId", equalTo(firstVoucherId.toString())))
                .andExpect(jsonPath("$[1].voucherId", equalTo(secondVoucherId.toString())));
    }

    @Test
    @DisplayName("바우처 타입과 생성날짜를 입력받아 조건에 해당하는 바우처 조회 요청을 처리할 수 있다.")
    void voucherSearch() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        UUID voucherId = UUID.randomUUID();
        List<Voucher> savedVouchers = List.of(
                new Voucher(voucherId, FIXED_AMOUNT, 10000L, now, now));
        //when
        when(voucherService.getVoucherByTypeAndDate(any(), any())).thenReturn(savedVouchers);
        //then
        mockMvc.perform(get("/api/v1/vouchers/search")
                        .param("voucherType", FIXED_AMOUNT.toString())
                        .param("date", now.toLocalDate().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId", equalTo(voucherId.toString())))
                .andExpect(jsonPath("$.length()", equalTo(1)));
    }

    @Test
    @DisplayName("바우처 타입과 할인 값을 받아 새로운 바우처 생성 요청을 처리할 수 있다.")
    void voucherCreate() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherCreateRequest createRequest = new VoucherCreateRequest();
        createRequest.setVoucherType(FIXED_AMOUNT);
        createRequest.setDiscountValue(1000L);
        String jsonContent = objectMapper.writeValueAsString(createRequest);
        //when
        when(voucherService.save(any())).thenReturn(voucherId);
        //then
        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("바우처 ID를 통해 바우처에 대한 상세 정보 조회 요청을 처리할 수 있다.")
    void voucherDetails() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Voucher voucher = new Voucher(voucherId, FIXED_AMOUNT, 10000L, now, now);
        //when
        when(voucherService.getVoucherById(any())).thenReturn(Optional.of(voucher));
        //then
        mockMvc.perform(get("/api/v1/vouchers/{voucherId}", voucherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", equalTo(1)))
                .andExpect(jsonPath("$.data.voucherId", equalTo(voucherId.toString())));
    }

    @Test
    @DisplayName("바우처 타입과 할인 값을 받아 바우처 수정 요청을 처리할 수 있다.")
    void voucherModify() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        VoucherUpdateRequest updateRequest = new VoucherUpdateRequest();
        updateRequest.setVoucherType(FIXED_AMOUNT);
        updateRequest.setDiscountValue(100L);
        String jsonContent = objectMapper.writeValueAsString(updateRequest);
        //when
        //then
        mockMvc.perform(put("/api/v1/vouchers/{voucherId}", voucherId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("바우처 ID를 통해 바우처 삭제요청을 처리할 수 있다.")
    void voucherRemove() throws Exception{
        //given
        UUID voucherId = UUID.randomUUID();
        //when
        //then
        mockMvc.perform(delete("/api/v1/vouchers/{voucherId}", voucherId))
                .andExpect(status().isOk());
    }
}