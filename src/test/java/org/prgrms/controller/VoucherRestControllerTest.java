package org.prgrms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.service.VoucherService;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VoucherRestController.class)
class VoucherRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    private static Voucher fixedVoucher;
    private static Voucher percentVoucher;

    @BeforeAll
    static void setUp() {
        fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(1000L),
            LocalDateTime.now());
        percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(10L),
            LocalDateTime.now());
    }


    @Test
    void saveVoucher() throws Exception {

        String source = new ObjectMapper().writeValueAsString(
            Map.of("type", "fixed", "amount", 200L));

        mockMvc.perform(post("/rest/vouchers")
                .content(source)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());

    }

    @DisplayName("저장된 바우처리스트를 반환한다. 200응답코드를 생성한다")
    @Test
    void getVoucherList() throws Exception {
        when(voucherService.findAll()).thenReturn(List.of(fixedVoucher, percentVoucher));

        mockMvc.perform(get("/rest/vouchers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].voucherId").value(fixedVoucher.getVoucherId().toString()))
            .andExpect(jsonPath("$[1].date").value(percentVoucher.getDate().toString()))
            .andDo(print());
    }

    @DisplayName("id에 해당하는 바우처를 리턴한다. 200응답코드를 생성한다")
    @Test
    void getVoucherById() throws Exception {
        when(voucherService.findById(fixedVoucher.getVoucherId())).thenReturn(Optional.of(
            fixedVoucher));

        mockMvc.perform(get("/rest/vouchers/{id}", fixedVoucher.getVoucherId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.voucherId").value(fixedVoucher.getVoucherId().toString()))
            .andExpect(
                jsonPath("$.voucherAmount.value").value(fixedVoucher.getVoucherAmount().getValue()))
            .andDo(print());
    }

    @DisplayName("정해진기간내에 해당하는바우처를 리턴한다. 200응답코드를 생성한다")
    @Test
    void getVoucherByCreationTime() throws Exception {
        when(voucherService.findByPeriod(fixedVoucher.getDate().toString(),
            percentVoucher.getDate().toString())).thenReturn(List.of(fixedVoucher, percentVoucher));

        mockMvc.perform(get("/rest/vouchers/period")
                .param("startDate", fixedVoucher.getDate().toString())
                .param("endDate", percentVoucher.getDate().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andDo(print());
    }

    @DisplayName("정해진 타입을 가진 바우처 리스트를 리턴한다. 200응답코드를 생성한다")
    @Test
    void getVoucherByType() throws Exception {
        when(voucherService.findByType("fixed")).thenReturn(List.of(fixedVoucher));

        mockMvc.perform(get("/rest/vouchers/type").queryParam("type", "fixed"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].voucherType").value(fixedVoucher.getVoucherType().name()))
            .andDo(print());
    }

    @Test
    void createVoucher() throws Exception {
        String content = new ObjectMapper().writeValueAsString(
            Map.of("type", "FIXED", "amount", 100));

        mockMvc.perform(post("/rest/vouchers")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }


}