package org.prgrms.springbootbasic.controller.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.controller.VoucherType;
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
    @DisplayName("모든 바우처 조회")
    void testFindAllVouchers() throws Exception {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        given(voucherService.findAll())
            .willReturn(List.of(voucher));

        //when
        //then
        mockMvc.perform(get("/api/v1/vouchers")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherDtoList", hasSize(1)))
            .andExpect(
                jsonPath("$.voucherDtoList[0].voucherId").value(voucher.getVoucherId().toString()));
    }

    @Test
    @DisplayName("특정 타입의 바우처 조회")
    void testFindVoucherUsingType() throws Exception {
        //given
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        given(voucherService.findVoucherUsingType(VoucherType.FIXED)).willReturn(
            List.of(voucher));

        //when
        //then
        mockMvc.perform(get("/api/v1/vouchers?voucherType=fixed")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherDtoList", hasSize(1)))
            .andExpect(
                jsonPath("$.voucherDtoList[0].voucherId").value(voucher.getVoucherId().toString()))
            .andExpect(
                jsonPath("$.voucherDtoList[0].voucherType").value(VoucherType.FIXED.name()));
    }

    @Test
    @DisplayName("특정 기간에 생성된 바우처 조회(시간 제외)")
    void testFindVoucherUsingCreatedAt() throws Exception {
        //given
        var date = LocalDate.now();
        given(voucherService.findVoucherUsingCreatedAt(date.atStartOfDay(), date.atStartOfDay()))
            .willReturn(List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 1000),
                new PercentDiscountVoucher(UUID.randomUUID(), 20)));

        //when
        //then
        mockMvc.perform(
                get("/api/v1/vouchers?start=" + date.toString() + "&end=" + date.toString())
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.voucherDtoList", hasSize(2)));
    }

    @Test
    @DisplayName("바우처 생성")
    void testCreateVoucher() throws Exception {
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
    @DisplayName("바우처 삭제")
    void testDeleteVoucher() throws Exception {
        //given
        UUID voucherId = UUID.randomUUID();
        given(voucherService.deleteVoucher(voucherId)).willReturn(voucherId);

        //when
        //then
        mockMvc.perform(delete("/api/v1/vouchers/" + voucherId.toString())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/vouchers"));
    }

    @Test
    @DisplayName("특정 아이디의 바우처 조회")
    void testFindVoucherUsingId() throws Exception {
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