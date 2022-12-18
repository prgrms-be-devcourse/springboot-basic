package org.prgrms.springbootbasic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.type.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.prgrms.springbootbasic.type.VoucherType.FIXED;
import static org.prgrms.springbootbasic.type.VoucherType.PERCENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class VoucherRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("voucher 생성 test")
    void createVoucher_test_success() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();

        LocalDate now = LocalDate.now();
        VoucherInputDto voucherInputDto = new VoucherInputDto(
                FIXED.toString(), 1000L, 0L, 11L,
                now.toString(), now.plusDays(3).toString()
        );

        String voucherToJsonString = objectMapper.writeValueAsString(voucherInputDto);

        Voucher voucher = Voucher.builder()
                .voucherId(voucherId)
                .voucherType(VoucherType.valueOf(voucherInputDto.getVoucherType()))
                .discountQuantity(voucherInputDto.getDiscountQuantity())
                .discountRatio(voucherInputDto.getDiscountRatio())
                .voucherCount(voucherInputDto.getVoucherCount())
                .createdAt(LocalDate.parse(voucherInputDto.getCreatedAt()))
                .endedAt(LocalDate.parse(voucherInputDto.getEndedAt()))
                .build();


        when(voucherService.createVoucher(any(VoucherInputDto.class)))
                .thenReturn(voucher);

        //expected
        mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(APPLICATION_JSON)
                        .content(voucherToJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").value(voucherId.toString()))
                .andExpect(jsonPath("$.voucherType").value(voucher.getVoucherType().toString()))
                .andExpect(jsonPath("$.discountQuantity").value(voucher.getDiscountQuantity()))
                .andExpect(jsonPath("$.discountRatio").value(voucher.getDiscountRatio()))
                .andExpect(jsonPath("$.voucherCount").value(voucher.getVoucherCount()))
                .andExpect(jsonPath("$.createdAt").value(voucher.getCreatedAt().toString()))
                .andExpect(jsonPath("$.endedAt").value(voucher.getEndedAt().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("customer 목록 조회 - 성공")
    void getVoucherList_test_success() throws Exception {
        // given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        LocalDate now = LocalDate.now();

        Voucher voucher1 = Voucher.builder()
                .voucherId(id1)
                .voucherType(FIXED)
                .discountQuantity(1000L)
                .discountRatio(0L)
                .voucherCount(10)
                .createdAt(now)
                .endedAt(now.plusDays(1))
                .build();

        Voucher voucher2 = Voucher.builder()
                .voucherId(id2)
                .voucherType(FIXED)
                .discountQuantity(2000L)
                .discountRatio(0L)
                .voucherCount(20)
                .createdAt(now)
                .endedAt(now.plusDays(2))
                .build();

        Voucher voucher3 = Voucher.builder()
                .voucherId(id3)
                .voucherType(PERCENT)
                .discountQuantity(1)
                .discountRatio(20L)
                .voucherCount(30)
                .createdAt(now)
                .endedAt(now.plusDays(1))
                .build();


        when(voucherService.getVoucherList())
                .thenReturn(List.of(voucher1, voucher2, voucher3));

        //expected
        mockMvc.perform(get("/api/v1/vouchers")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId").value(id1.toString()))
                .andExpect(jsonPath("$[0].voucherType").value(voucher1.getVoucherType().toString()))
                .andExpect(jsonPath("$[0].discountQuantity").value(voucher1.getDiscountQuantity()))
                .andExpect(jsonPath("$[0].discountRatio").value(voucher1.getDiscountRatio()))
                .andExpect(jsonPath("$[0].voucherCount").value(voucher1.getVoucherCount()))
                .andExpect(jsonPath("$[0].createdAt").value(voucher1.getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].endedAt").value(voucher1.getEndedAt().toString()))
                .andExpect(jsonPath("$[1].voucherId").value(id2.toString()))
                .andExpect(jsonPath("$[1].voucherType").value(voucher2.getVoucherType().toString()))
                .andExpect(jsonPath("$[1].discountQuantity").value(voucher2.getDiscountQuantity()))
                .andExpect(jsonPath("$[1].discountRatio").value(voucher2.getDiscountRatio()))
                .andExpect(jsonPath("$[1].voucherCount").value(voucher2.getVoucherCount()))
                .andExpect(jsonPath("$[1].createdAt").value(voucher2.getCreatedAt().toString()))
                .andExpect(jsonPath("$[1].endedAt").value(voucher2.getEndedAt().toString()))
                .andExpect(jsonPath("$[2].voucherId").value(id3.toString()))
                .andExpect(jsonPath("$[2].voucherType").value(voucher3.getVoucherType().toString()))
                .andExpect(jsonPath("$[2].discountQuantity").value(voucher3.getDiscountQuantity()))
                .andExpect(jsonPath("$[2].discountRatio").value(voucher3.getDiscountRatio()))
                .andExpect(jsonPath("$[2].voucherCount").value(voucher3.getVoucherCount()))
                .andExpect(jsonPath("$[2].createdAt").value(voucher3.getCreatedAt().toString()))
                .andExpect(jsonPath("$[2].endedAt").value(voucher3.getEndedAt().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("customer 조회 - 성공")
    void getVoucher_test_success() throws Exception {
        // given
        UUID voucherId = UUID.randomUUID();
        LocalDate now = LocalDate.now();

        Voucher voucher = Voucher.builder()
                .voucherId(voucherId)
                .voucherType(PERCENT)
                .discountQuantity(0L)
                .discountRatio(50L)
                .voucherCount(100)
                .createdAt(now)
                .endedAt(now.plusDays(5))
                .build();

        when(voucherService.getVoucherById(voucherId.toString()))
                .thenReturn(voucher);

        //expected
        mockMvc.perform(get("/api/v1/vouchers/{voucherId}", voucherId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").value(voucherId.toString()))
                .andExpect(jsonPath("$.voucherType").value(voucher.getVoucherType().toString()))
                .andExpect(jsonPath("$.discountQuantity").value(voucher.getDiscountQuantity()))
                .andExpect(jsonPath("$.discountRatio").value(voucher.getDiscountRatio()))
                .andExpect(jsonPath("$.voucherCount").value(voucher.getVoucherCount()))
                .andExpect(jsonPath("$.createdAt").value(voucher.getCreatedAt().toString()))
                .andExpect(jsonPath("$.endedAt").value(voucher.getEndedAt().toString()))
                .andDo(print());
    }

}