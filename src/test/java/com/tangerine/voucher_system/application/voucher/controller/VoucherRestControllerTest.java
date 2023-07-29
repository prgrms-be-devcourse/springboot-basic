package com.tangerine.voucher_system.application.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangerine.voucher_system.application.voucher.controller.dto.CreateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.controller.dto.UpdateVoucherRequest;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.voucher.service.VoucherService;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VoucherRestControllerTest {

    @InjectMocks
    VoucherRestController controller;

    @Mock
    VoucherService service;

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 바우처를 생성 시 성공한다.")
    @MethodSource("provideCreateVoucherRequests")
    void createVoucher_ParamNotExistVoucherDto_RegisterAndReturnVoucherDto(CreateVoucherRequest request, VoucherResult result) throws Exception {
        given(service.createVoucher(any())).willReturn(result);

        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId").value(result.voucherId().toString()))
                .andExpect(jsonPath("$.voucherType").value(result.voucherType().toString()));

        verify(service, times(1)).createVoucher(any());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 갱신 시 성공한다.")
    @MethodSource("provideUpdateVoucherRequests")
    void updateVoucher_ParamExistVoucherDto_UpdateAndReturnVoucherDto(UpdateVoucherRequest request, VoucherResult result) throws Exception {
        given(service.updateVoucher(any())).willReturn(result);

        mockMvc.perform(patch("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId").value(result.voucherId().toString()))
                .andExpect(jsonPath("$.voucherType").value(result.voucherType().toString()));

        verify(service, times(1)).updateVoucher(any());
    }

    @Test
    @DisplayName("모든 바우처 Dto 를 리스트로 반환한다.")
    void voucherList_ParamVoid_ReturnVoucherList() throws Exception {
        given(service.findVouchers()).willReturn(voucherResults);

        mockMvc.perform(get("/api/v1/vouchers")
                        .param("isBlack", String.valueOf(false)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responses[0].voucherId").value(voucherResults.get(0).voucherId().toString()))
                .andExpect(jsonPath("$.responses[0].voucherType").value(voucherResults.get(0).voucherType().toString()));

        verify(service, times(1)).findVouchers();
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 아이디로 찾으면 성공한다.")
    @MethodSource("provideVoucherResults")
    void voucherById_ParamExistVoucherId_ReturnVoucherDto(VoucherResult result) throws Exception {
        given(service.findVoucherById(any())).willReturn(result);

        mockMvc.perform(get("/api/v1/vouchers/" + result.voucherId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId").value(result.voucherId().toString()))
                .andExpect(jsonPath("$.voucherType").value(result.voucherType().toString()));

        verify(service, times(1)).findVoucherById(result.voucherId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 생성일자로 찾으면 성공한다.")
    @MethodSource("provideVoucherResults")
    void voucherByCreatedAt_ParamExistVoucherId_ReturnVoucherDto(VoucherResult result) throws Exception {
        given(service.findVoucherByCreatedAt(any())).willReturn(voucherResults);

        mockMvc.perform(get("/api/v1/vouchers/created-at/" + result.createdAt()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.responses[0].voucherId").value(voucherResults.get(0).voucherId().toString()))
                .andExpect(jsonPath("$.responses[0].voucherType").value(voucherResults.get(0).voucherType().toString()));

        verify(service, times(1)).findVoucherByCreatedAt(result.createdAt());
    }

    @ParameterizedTest
    @DisplayName("존재하는 바우처를 제거하면 성공한다.")
    @MethodSource("provideVoucherResults")
    void deleteVoucherById_ParamExistVoucherId_DeleteAndReturnVoucherDto(VoucherResult result) throws Exception {
        given(service.deleteVoucherById(any())).willReturn(result);

        mockMvc.perform(delete("/api/v1/vouchers/" + result.voucherId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId").value(result.voucherId().toString()))
                .andExpect(jsonPath("$.voucherType").value(result.voucherType().toString()));

        verify(service, times(1)).deleteVoucherById(result.voucherId());
    }

    static List<VoucherResult> voucherResults = List.of(
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 23), LocalDate.now()),
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, 41), LocalDate.now()),
            new VoucherResult(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, 711), LocalDate.now())
    );

    static List<CreateVoucherRequest> createVoucherRequests = voucherResults.stream()
            .map(result -> new CreateVoucherRequest(result.voucherType(), result.discountValue().value()))
            .toList();

    static List<UpdateVoucherRequest> updateVoucherRequests = voucherResults.stream()
            .map(result -> new UpdateVoucherRequest(result.voucherId(), result.voucherType(), result.discountValue().value()))
            .toList();

    static Stream<Arguments> provideCreateVoucherRequests() {
        return IntStream.range(0, voucherResults.size())
                .mapToObj(i -> Arguments.of(createVoucherRequests.get(i), voucherResults.get(i)));
    }

    static Stream<Arguments> provideUpdateVoucherRequests() {
        return IntStream.range(0, voucherResults.size())
                .mapToObj(i -> Arguments.of(updateVoucherRequests.get(i), voucherResults.get(i)));
    }

    static Stream<Arguments> provideVoucherResults() {
        return voucherResults.stream()
                .map(Arguments::of);
    }

}