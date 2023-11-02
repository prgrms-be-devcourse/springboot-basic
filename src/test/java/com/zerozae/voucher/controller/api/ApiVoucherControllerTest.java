package com.zerozae.voucher.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.zerozae.voucher.domain.voucher.UseStatusType.AVAILABLE;
import static com.zerozae.voucher.domain.voucher.UseStatusType.UNAVAILABLE;
import static com.zerozae.voucher.domain.voucher.VoucherType.FIXED;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiVoucherController.class)
class ApiVoucherControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoucherService voucherService;

    private Voucher fixedDiscountVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 10, AVAILABLE, LocalDateTime.now());
    private Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20 , UNAVAILABLE, LocalDateTime.now());
    private VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(fixedDiscountVoucher.getDiscount(), String.valueOf(fixedDiscountVoucher.getVoucherType()));;

    @Test
    @DisplayName("바우처 생성 테스트")
    void createVoucher_Success_Test() throws Exception {
        // Given
        when(voucherService.createVoucher(voucherCreateRequest)).thenReturn(VoucherResponse.toDto(fixedDiscountVoucher));

        // When
        mvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voucherCreateRequest)))
                .andExpect(status().isCreated());

        // Then
        verify(voucherService).createVoucher(any(VoucherCreateRequest.class));
    }

    @Test
    @DisplayName("바우처 생성 실패 - 잘못된 할인 정보 테스트")
    void createVoucher_InvalidDiscount_Failed_Test() throws Exception {
        // Given
        VoucherCreateRequest invalidRequest = new VoucherCreateRequest(0, String.valueOf(FIXED));

        // When & Then
        mvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("할인 정보는 1이상의 숫자값을 입력해야 합니다."));
    }

    @Test
    @DisplayName("바우처 생성 실패 - 타입 누락 테스트")
    void createVoucher_EmptyVoucherType_Failed_Test() throws Exception {
        // Given
        VoucherCreateRequest invalidRequest = new VoucherCreateRequest(10, null);

        // When & Then
        mvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("바우처의 타입은 필수 입력란입니다."));
    }

    @Test
    @DisplayName("바우처 전체 조회 테스트")
    void findAllVouchers_Success_Test() throws Exception {
        // Given
        List<VoucherResponse> vouchers = List.of(
                VoucherResponse.toDto(fixedDiscountVoucher)
        );
        when(voucherService.findAllVouchers()).thenReturn(vouchers);

        // When
        mvc.perform(get("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId").value(fixedDiscountVoucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[0].discount").value(fixedDiscountVoucher.getDiscount()))
                .andExpect(jsonPath("$[0].voucherType").value(fixedDiscountVoucher.getVoucherType().toString()))
                .andExpect(jsonPath("$[0].useStatusType").value(fixedDiscountVoucher.getUseStatusType().toString()))
                .andExpect(jsonPath("$[0].createdAt").value(fixedDiscountVoucher.getCreatedAt().toString()));

        // Then
        verify(voucherService).findAllVouchers();
    }

    @Test
    @DisplayName("바우처 아이디로 조회 성공 테스트")
    void findVoucherById_Success_Test() throws Exception {
        // Given
        when(voucherService.findById(fixedDiscountVoucher.getVoucherId())).thenReturn(VoucherResponse.toDto(fixedDiscountVoucher));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime expectedDateTime = LocalDateTime.parse(fixedDiscountVoucher.getCreatedAt().toString(), formatter);

        // When
        mvc.perform(get("/api/v1/vouchers/{voucherId}", fixedDiscountVoucher.getVoucherId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").value(fixedDiscountVoucher.getVoucherId().toString()))
                .andExpect(jsonPath("$.discount").value(fixedDiscountVoucher.getDiscount()))
                .andExpect(jsonPath("$.voucherType").value(fixedDiscountVoucher.getVoucherType().toString()))
                .andExpect(jsonPath("$.useStatusType").value(fixedDiscountVoucher.getUseStatusType().toString()))
                .andExpect(jsonPath("$.createdAt").value(expectedDateTime.format(formatter)));

        // Then
        verify(voucherService).findById(any(UUID.class));
    }

    @Test
    @DisplayName("조건(생성 날짜)으로 바우처 검색하기")
    void findVoucherByCreatedAt_Success_Test() throws Exception {
        // Given
        VoucherCondition condition = new VoucherCondition(null, null, LocalDate.now().toString(), null);
        List<VoucherResponse> vouchers = List.of(
                VoucherResponse.toDto(fixedDiscountVoucher),
                VoucherResponse.toDto(percentDiscountVoucher));
        when(voucherService.findVoucherByCondition(condition)).thenReturn(vouchers);

        // When
        mvc.perform(get("/api/v1/vouchers/condition")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(condition)))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).findVoucherByCondition(any(VoucherCondition.class));
    }

    @Test
    @DisplayName("조건(바우처 타입)으로 바우처 검색하기")
    void findVoucherByVoucherType_Success_Test() throws Exception {
        // Given
        VoucherCondition condition = new VoucherCondition(String.valueOf(FIXED), null, null , null);
        List<VoucherResponse> vouchers = List.of(
                VoucherResponse.toDto(fixedDiscountVoucher),
                VoucherResponse.toDto(percentDiscountVoucher));
        when(voucherService.findVoucherByCondition(condition)).thenReturn(List.of(vouchers.get(0)));

        // When
        mvc.perform(get("/api/v1/vouchers/condition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(condition)))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).findVoucherByCondition(any(VoucherCondition.class));
    }

    @Test
    @DisplayName("조건(날짜 범위)으로 바우처 검색하기")
    void findVoucherByCreatedAtRange_Success_Test() throws Exception {
        // Given
        VoucherCondition condition = new VoucherCondition(null, null, "2023-11-01", "2023-11-02");
        List<VoucherResponse> vouchers = List.of(
                VoucherResponse.toDto(new FixedDiscountVoucher(UUID.randomUUID(), 1, AVAILABLE, LocalDateTime.of(2023, 11, 1, 10, 30))),
                VoucherResponse.toDto(new FixedDiscountVoucher(UUID.randomUUID(), 1, AVAILABLE, LocalDateTime.of(2023, 11, 2, 10, 30))));
        when(voucherService.findVoucherByCondition(condition)).thenReturn(vouchers);

        // When
        mvc.perform(get("/api/v1/vouchers/condition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(condition)))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).findVoucherByCondition(any(VoucherCondition.class));
    }

    @Test
    @DisplayName("조건(할인 정보)으로 바우처 검색하기")
    void findVoucherByDiscount_Success_Test() throws Exception {
        // Given
        VoucherCondition condition = new VoucherCondition(null, 10L, null, null);
        List<VoucherResponse> vouchers = List.of(
                VoucherResponse.toDto(fixedDiscountVoucher),
                VoucherResponse.toDto(percentDiscountVoucher)
        );
        when(voucherService.findVoucherByCondition(condition)).thenReturn(vouchers);

        // When
        mvc.perform(get("/api/v1/vouchers/condition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(condition)))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).findVoucherByCondition(any(VoucherCondition.class));
    }

    @Test
    @DisplayName("바우처 업데이트 테스트")
    void voucherUpdate_Success_Test() throws Exception {
        // Given
        VoucherUpdateRequest voucherUpdateRequest = new VoucherUpdateRequest(30, String.valueOf(AVAILABLE));
        Voucher newVoucher = new FixedDiscountVoucher(UUID.randomUUID(), 10, UNAVAILABLE, LocalDateTime.now());
        Voucher updatedVoucher = new FixedDiscountVoucher(
                newVoucher.getVoucherId(),
                voucherUpdateRequest.discount(),
                UseStatusType.valueOf(voucherUpdateRequest.useStatusType()),
                newVoucher.getCreatedAt());

        VoucherResponse voucherResponse = VoucherResponse.toDto(updatedVoucher);
        when(voucherService.update(newVoucher.getVoucherId(), voucherUpdateRequest)).thenReturn(voucherResponse);

        // When
        mvc.perform(patch("/api/v1/vouchers/{voucherId}", newVoucher.getVoucherId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voucherUpdateRequest)))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).update(any(UUID.class), any(VoucherUpdateRequest.class));
    }

    @Test
    @DisplayName("바우처 업데이트 실패 - 잘못된 할인 정보 테스트")
    void updateVoucher_InvalidDiscount_Failed_Test() throws Exception {
        // Given
        VoucherUpdateRequest invalidRequest = new VoucherUpdateRequest(0, String.valueOf(FIXED));

        // When & Then
        mvc.perform(patch("/api/v1/vouchers/{voucherId}", fixedDiscountVoucher.getVoucherId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("할인 정보는 1이상의 숫자값을 입력해야 합니다."));
    }

    @Test
    @DisplayName("바우처 업데이트 실패 - 타입 누락 테스트")
    void updateVoucher_EmptyVoucherType_Failed_Test() throws Exception {
        // Given
        VoucherUpdateRequest invalidRequest = new VoucherUpdateRequest(10, null);

        // When & Then
        mvc.perform(patch("/api/v1/vouchers/{voucherId}", fixedDiscountVoucher.getVoucherId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("바우처의 타입은 필수 입력란입니다."));
    }

    @Test
    @DisplayName("바우처 아이디로 삭제 테스트")
    void deleteVoucherById_Success_Test() throws Exception {
        // Given

        // When
        mvc.perform(delete("/api/v1/vouchers/{voucherId}", fixedDiscountVoucher.getVoucherId()))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("바우처 전체 삭제 테스트")
    void deleteAllVouchers_Success_Test() throws Exception {
        // Given

        // When
        mvc.perform(delete("/api/v1/vouchers"))
                .andExpect(status().isOk());

        // Then
        verify(voucherService).deleteAll();
    }
}
