package org.programmers.kdtspring.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtspring.dto.ResponseVoucher;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VoucherRestController.class)
class VoucherRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    VoucherService voucherService;

    // 바우처 생성

    @Test
    @DisplayName("모든 바우처 조회")
    void getVouchersTest() throws Exception {
        ResponseVoucher responseVoucher = new ResponseVoucher(UUID.randomUUID(), VoucherType.FixedAmountVoucher.name(), 10000, null);
        given(voucherService.getVouchers().stream().map(
                voucher -> new ResponseVoucher(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), voucher.getCustomerId())
        ).toList()).willReturn(List.of(responseVoucher));

        mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    // 바우처 ID로 조회
    @Test
    @DisplayName("바우처 ID로 조회")
    void testFindUserById() throws Exception {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.FixedAmountVoucher.name());

        given(voucherService.getVoucher(fixedAmountVoucher.getVoucherId())).willReturn(Optional.of(fixedAmountVoucher));

        mockMvc.perform(get("/api/v1/vouchers/voucher?voucherId=" + fixedAmountVoucher.getVoucherId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherType", is("FixedAmountVoucher")))
                .andExpect(jsonPath("$.discount", is(10000)));
    }

    // 바우처 유저 할당
    // 바우처 삭제
    @Test
    @DisplayName("바우처 삭제")
    void testDeleteVoucher() throws Exception {
        UUID voucherId = UUID.randomUUID();
        given(voucherService.removeVoucher(voucherId)).willReturn(voucherId);

        mockMvc.perform(delete("/api/v1/vouchers/" + voucherId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}