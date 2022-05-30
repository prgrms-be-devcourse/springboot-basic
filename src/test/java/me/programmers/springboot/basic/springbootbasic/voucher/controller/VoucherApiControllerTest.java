package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoucherApiController.class)
class VoucherApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JdbcVoucherService voucherService;

    @Test
    @DisplayName("전체 조회")
    void findAllTest() throws Exception {
        given(voucherService.getAllVouchers()).willReturn(List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        mockMvc.perform(get("/api/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("ID로 조회")
    void findByIdTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixVoucher = new FixedAmountVoucher(uuid, 1000);
        given(voucherService.getVoucherById(uuid)).willReturn(fixVoucher);

        mockMvc.perform(get("/api/vouchers/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId", is(fixVoucher.getVoucherId().toString())));
    }

    @Test
    @DisplayName("잘못된 ID로 조회")
    void findByIdTestFail() throws Exception {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixVoucher = new FixedAmountVoucher(uuid, 1000);
        given(voucherService.getVoucherById(uuid)).willReturn(fixVoucher);

        mockMvc.perform(get("/api/vouchers/" + uuid + "dd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("모든 고정 할인 바우저 조회")
    void findAllFixVouchersTest() throws Exception {
        given(voucherService.getAllFixVouchers()).willReturn(List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        mockMvc.perform(get("/api/vouchers?type=fixed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("type 값(request param)을 잘못 지정한 경우")
    void findAllFixVouchersTestFail() throws Exception {
        given(voucherService.getAllFixVouchers()).willReturn(List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        mockMvc.perform(get("/api/vouchers?type=fixedd"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("ID로 삭제")
    void deleteByIdTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixVoucher = new FixedAmountVoucher(uuid, 1000);
        given(voucherService.getVoucherById(uuid)).willReturn(fixVoucher);

        mockMvc.perform(delete("/api/vouchers/" + uuid))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 ID로 삭제 요청")
    void deleteByIdTestFail() throws Exception {
        mockMvc.perform(delete("/api/vouchers/ddewqcxzds"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("바우처 저장")
    void saveTest() throws Exception {
        VoucherCreateRequestDto createRequestDto = new VoucherCreateRequestDto("fixed", 1000L, 0L);

        given(voucherService.save(any(Voucher.class))).willReturn(new FixedAmountVoucher(UUID.randomUUID(), createRequestDto.getDiscountPrice()));

        mockMvc.perform(post("/api/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestDto)))
                .andExpect(status().isOk());
    }

}