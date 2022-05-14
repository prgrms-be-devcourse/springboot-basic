package com.mountain.voucherApp.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mountain.voucherApp.dto.VoucherCreateDto;
import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.model.enums.DiscountPolicy;
import com.mountain.voucherApp.service.VoucherAppService;
import com.mountain.voucherApp.service.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherRestController.class)
@AutoConfigureMockMvc
class VoucherRestControllerTest {
    @MockBean
    private VoucherService voucherService;
    @MockBean
    private VoucherAppService voucherAppService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private void validateVoucherEntity(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(jsonPath("$.[0].voucherId").exists())
                .andExpect(jsonPath("$.[0].discountPolicy").exists())
                .andExpect(jsonPath("$.[0].discountAmount").exists())
        ;
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다 - JSON")
    void testVouchersAPI() throws Exception {
        List<VoucherEntity> vouchers = List.of(
                new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 1000L),
                new VoucherEntity(UUID.randomUUID(), DiscountPolicy.FIXED, 2000L)
        );
        given(voucherService.findAll()).willReturn(vouchers);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON)
        );
        validateVoucherEntity(resultActions);
        resultActions
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("이미 존재하는 바우처는 200을 반환한다.")
    void testCreateVoucherAPI() throws Exception {
        VoucherCreateDto voucherCreateDto = new VoucherCreateDto(DiscountPolicy.FIXED, 1000L);
        given(voucherAppService.create(voucherCreateDto)).willReturn(false);

        ResultActions perform = mockMvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voucherCreateDto))
        );

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("새롭게 생성된 경우에는 201코드  Location Header 응답한다.")
    void testCreateVoucherAPI_responseOK() throws Exception {
        VoucherCreateDto voucherCreateDto = new VoucherCreateDto(DiscountPolicy.FIXED, 1000L);
        given(voucherAppService.create(voucherCreateDto)).willReturn(true);

        ResultActions perform = mockMvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voucherCreateDto))
        );
        perform
                .andExpect(header().string(HttpHeaders.LOCATION.toString(), "/new-voucher-success"))
                .andDo(print());
    }

    @Test
    @DisplayName("생성 중 예외가 발생하면 500에러를 반환한다.")
    void testCreateIllegalException() throws Exception {
        VoucherCreateDto voucherCreateDto = new VoucherCreateDto(DiscountPolicy.FIXED, 1000L);
        given(voucherAppService.create(voucherCreateDto)).willThrow(IllegalArgumentException.class);

        ResultActions perform = mockMvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voucherCreateDto))
        );

        perform
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 아이디로 삭제할 수 있다.")
    void testDeleteVoucher() throws Exception {
        UUID voucherId = UUID.randomUUID();

        ResultActions perform = mockMvc.perform(delete("/api/v1/vouchers/{voucherId}", voucherId.toString())
        );
        perform
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}