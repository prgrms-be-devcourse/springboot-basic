package org.promgrammers.springbootbasic.domain.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherService;
import org.promgrammers.springbootbasic.global.error.exception.BusinessException;
import org.promgrammers.springbootbasic.global.error.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.promgrammers.springbootbasic.global.error.exception.ErrorCode.NOT_FOUND_VOUCHER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherApiController.class)
class VoucherApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    VoucherService voucherService;

    @Test
    @DisplayName("바우처 생성 성공")
    void createVoucherSuccessTest() throws Exception {

        //given
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(VoucherType.PERCENT, 10);

        //when -> then
        mockMvc.perform(post("/api/vouchers/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createVoucherRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 생성 실패 - 잘못된 금액")
    void createVoucherFailTest_Amount() throws Exception {

        //given
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(VoucherType.PERCENT, 2000);

        //when
        given(voucherService.create(createVoucherRequest))
                .willThrow(new BusinessException(ErrorCode.INVALID_PERCENT_VOUCHER_AMOUNT));

        //then
        mockMvc.perform(post("/api/vouchers/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createVoucherRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 조회")
    void findAllSuccessTest() throws Exception {

        //given
        List<Voucher> vouchers = Arrays.asList(
                new PercentDiscountVoucher(UUID.randomUUID(), 10),
                new PercentDiscountVoucher(UUID.randomUUID(), 20)
        );

        //when
        given(voucherService.findAll())
                .willReturn(new VoucherListResponse(vouchers.stream()
                        .map(VoucherResponse::new)
                        .toList()));

        //then
        mockMvc.perform(get("/api/vouchers/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 조회 실패 - 바우처 존재하지 않음")
    void findAllFailTest() throws Exception {

        //given -> when
        given(voucherService.findAll())
                .willThrow(new BusinessException(NOT_FOUND_VOUCHER));

        //then
        mockMvc.perform(get("/api/vouchers/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    @DisplayName("바우처 타입으로 조회 성공")
    void findByTypeSuccessTest() throws Exception {

        //given
        List<Voucher> vouchers = Arrays.asList(
                new PercentDiscountVoucher(UUID.randomUUID(), 10),
                new PercentDiscountVoucher(UUID.randomUUID(), 20)
        );

        //when
        given(voucherService.findByType(VoucherType.PERCENT))
                .willReturn(new VoucherListResponse(vouchers.stream()
                        .map(VoucherResponse::new)
                        .toList()));

        //then
        mockMvc.perform(get("/api/vouchers").param("voucherType", "percent")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 타입으로 조회 실패 - 저장되지 않은 타입의 바우처")
    void findByTypeFailTest() throws Exception {

        //given
        List<Voucher> vouchers = Arrays.asList(
                new PercentDiscountVoucher(UUID.randomUUID(), 10),
                new PercentDiscountVoucher(UUID.randomUUID(), 20)
        );

        //when
        given(voucherService.findByType(VoucherType.FIXED))
                .willThrow(new BusinessException(NOT_FOUND_VOUCHER));

        //then
        mockMvc.perform(get("/api/vouchers").param("voucherType", "fixed")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 ID로 조회 성공")
    void findByIdSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(voucherId, 10);

        //when
        given(voucherService.findById(voucherId))
                .willReturn(new VoucherResponse(voucher));

        //then
        mockMvc.perform(get("/api/vouchers/{id}", voucher.getVoucherId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.voucherId").value(voucher.getVoucherId().toString()))
                .andExpect(jsonPath("$.body.voucherType").value(voucher.getVoucherType().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 ID로 조회 실패 - 존재하지 않는 ID")
    void findByIdFailTest() throws Exception {

        //given
        UUID unknownId = UUID.randomUUID();

        //when
        given(voucherService.findById(unknownId))
                .willThrow(new BusinessException(NOT_FOUND_VOUCHER));

        //then
        mockMvc.perform(get("/api/vouchers/{id}", unknownId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 아이디로 단건 삭제")
    void deleteByIdSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(voucherId, 10);

        //when -> then
        mockMvc.perform(delete("/api/vouchers/{id}", voucher.getVoucherId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("삭제 완료"))
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 ID로 단건 삭제 실패 - 존재하지 않는 ID")
    void deleteByIdFailTest() throws Exception {

        //given
        UUID id = UUID.randomUUID();

        //when
        doThrow(new BusinessException(NOT_FOUND_VOUCHER))
                .when(voucherService).deleteById(id);

        //then
        mockMvc.perform(delete("/api/vouchers/{id}", id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 삭제 성공")
    void deleteAllSuccessTest() throws Exception {

        //given -> when -> then
        mockMvc.perform(delete("/api/vouchers"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("바우처 전체 삭제 실패 - 존재하는 바우처 없음")
    void deleteAllFailTest() throws Exception {

        //given -> when
        doThrow(new BusinessException(NOT_FOUND_VOUCHER))
                .when(voucherService).deleteAll();

        //then
        mockMvc.perform(delete("/api/vouchers"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}