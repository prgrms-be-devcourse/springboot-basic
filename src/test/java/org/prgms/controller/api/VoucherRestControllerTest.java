package org.prgms.controller.api;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.domain.FixedAmountVoucher;
import org.prgms.domain.PercentDiscountVoucher;
import org.prgms.domain.Voucher;
import org.prgms.domain.VoucherType;
import org.prgms.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class VoucherRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    protected MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            StandardCharsets.UTF_8);

    private final Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
    private final Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
    private final Voucher fixedVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 10);

    @Test
    @DisplayName("[API][GET] 바우처 리스트 조회")
    void getAllVouchers() throws Exception {
        given(voucherService.getVouchers()).willReturn(List.of(fixedVoucher, percentVoucher, fixedVoucher2));

        mockMvc.perform(get("/api/v1/voucher").accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId").value(fixedVoucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[1].voucherId").value(percentVoucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[2].voucherId").value(fixedVoucher2.getVoucherId().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API][GET] 바우처 타입별 조회")
    void getVoucherByType() throws Exception {
        String voucherType = VoucherType.FIXED.name();
        String requestUrl = String.format("/api/v1/voucher/type/%s", voucherType);
        given(voucherService.getVoucherByType(voucherType)).willReturn(List.of(fixedVoucher, fixedVoucher2));


        mockMvc.perform(get(requestUrl).accept(MediaType.APPLICATION_JSON))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId").value(fixedVoucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[1].voucherId").value(fixedVoucher2.getVoucherId().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API][GET] 바우처 생성일자별 조회")
    void getVoucherByCreatedTime() throws Exception {
        String begin = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String end = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String requestUrl = String.format("/api/v1/voucher/created/%s/%s", begin, end);
        given(voucherService.getVoucherByCreatedTime(begin, end))
                .willReturn(List.of(fixedVoucher, fixedVoucher2, percentVoucher));


        mockMvc.perform(get(requestUrl).accept(MediaType.APPLICATION_JSON))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].voucherId").value(fixedVoucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[1].voucherId").value(fixedVoucher2.getVoucherId().toString()))
                .andExpect(jsonPath("$[2].voucherId").value(percentVoucher.getVoucherId().toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("[API][POST] 바우처 생성")
    void createVoucher() throws Exception {
        given(voucherService.createVoucher(VoucherType.FIXED.name(), 10)).willReturn(fixedVoucher);

        JsonObject json = new JsonObject();
        json.addProperty("voucherKind", VoucherType.FIXED.name());
        json.addProperty("discountAmount", 10);
        mockMvc.perform(post("/api/v1/voucher")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.voucherId").value(fixedVoucher.getVoucherId().toString()))
                .andDo(print());

    }

    @Test
    void deleteVoucher() {
    }

    @Test
    void getVoucher() {
    }
}