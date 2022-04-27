package org.prgrms.springbootbasic.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.dto.CreateVoucherRequest;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ApiVoucherController.class)
class ApiVoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoucherService voucherService;

    @Test
    @DisplayName("모든 바우처 조회 api")
    void findAllVouchers() throws Exception {
        //given
        given(voucherService.findAll())
            .willReturn(List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new PercentDiscountVoucher(UUID.randomUUID(), 20)
            ));

        //when
        //then
        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherDtoList", hasSize(2)));
    }

    @Test
    @DisplayName("특정 타입의 바우처 조회 api")
    void findVoucherUsingType() throws Exception {
        //given
        given(voucherService.findVoucherUsingType(VoucherType.FIXED)).willReturn(
            List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        //when
        //then
        mockMvc.perform(get("/api/v1/vouchers/search/fixed")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherDtoList", hasSize(1)));
    }

    @Test
    @DisplayName("생성기간(날짜) 기준 바우처 조회 api")
    void findVoucherUsingCreatedAt() throws Exception {
        //given
        given(voucherService.findVoucherUsingCreatedAt(
            any(LocalDateTime.class), any(LocalDateTime.class)))
            .willReturn(List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new PercentDiscountVoucher(UUID.randomUUID(), 20)
            ));

        //when
        //then
        mockMvc.perform(get("/api/v1/vouchers/search?start=2022-04-27&end=2022-04-27")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.voucherDtoList", hasSize(2)));
    }

    @Test
    @DisplayName("바우처 생성 api")
    void createVoucher() throws Exception {
        //given
        var request = new CreateVoucherRequest();
        request.setVoucherType(VoucherType.FIXED);
        request.setAmount(1000);

        //when
        //then
        mockMvc.perform(post("/api/v1/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/vouchers"));
    }

    @Test
    @DisplayName("바우처 삭제 api")
    void deleteVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        given(voucherService.deleteVoucher(voucherId)).willReturn(voucherId);

        //when
        //then
        mockMvc.perform(delete("/api/v1/vouchers/" + voucherId.toString())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("바우처 아이디로 조회 API")
    void findVoucherUsingId() throws Exception {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        given(voucherService.findVoucher(voucher.getVoucherId()))
            .willReturn(voucher);

        //when
        //then
        mockMvc.perform(get("/api/v1/vouchers/" + voucher.getVoucherId().toString())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherId").value(voucher.getVoucherId().toString()));
    }
}