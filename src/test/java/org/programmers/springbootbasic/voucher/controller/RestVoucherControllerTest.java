package org.programmers.springbootbasic.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.application.voucher.controller.VoucherConverter;
import org.programmers.springbootbasic.application.voucher.controller.RestVoucherController;
import org.programmers.springbootbasic.application.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.controller.api.UpdateVoucherRequest;
import org.programmers.springbootbasic.application.voucher.controller.api.VoucherResponse;
import org.programmers.springbootbasic.application.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.application.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.application.voucher.model.Voucher;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestVoucherController.class)
class RestVoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultVoucherService defaultVoucherService;

    @MockBean
    private VoucherConverter voucherConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Voucher를 생성할 수 있다.")
    void createVoucher() throws Exception {
        var createVoucherRequest = new CreateVoucherRequest(1500L, "FIXED");
        String param = objectMapper.writeValueAsString(createVoucherRequest);

        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1500L, LocalDateTime.now());
        VoucherResponse voucherResponse = new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType()
        );

        given(defaultVoucherService.createVoucher(any(CreateVoucherRequest.class)))
                .willReturn(voucher);
        given(voucherConverter.convertVoucherDto(any(FixedAmountVoucher.class)))
                .willReturn(voucherResponse);

        this.mockMvc.perform(post("/api/v1/vouchers")
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value").value(voucher.getValue()))
                .andExpect(jsonPath("$.voucherType").value(voucher.getVoucherType().toString()));
    }

    @Test
    @DisplayName("Voucher를 찾을 수 있다.")
    void findVoucher() throws Exception {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L, LocalDateTime.now());
        VoucherResponse voucherResponse = new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType()
        );

        given(defaultVoucherService.getVoucher(any(UUID.class)))
                .willReturn(voucher);
        given(voucherConverter.convertVoucherDto(any(PercentDiscountVoucher.class)))
                .willReturn(voucherResponse);

        this.mockMvc.perform(get("/api/v1/vouchers/" + voucher.getVoucherId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId").value(voucher.getVoucherId().toString()));
    }

    @Test
    @DisplayName("Vouchers를 찾을 수 있다.")
    void findVouchers() throws Exception {
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(
                UUID.randomUUID(), 10L, LocalDateTime.now());
        VoucherResponse voucherResponse = new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType()
        );
        List<Voucher> vouchers = List.of(voucher);

        given(voucherConverter.convertVoucherDto(any((PercentDiscountVoucher.class))))
                .willReturn(voucherResponse);
        given(defaultVoucherService.getVoucherList())
                .willReturn(vouchers);

        this.mockMvc.perform(get("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherId").value(voucher.getVoucherId().toString()));
    }

    @Test
    @DisplayName("voucherType으로 Vouchers를 찾을 수 있다.")
    void findVoucherByVoucherType() throws Exception {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 5L, LocalDateTime.now());
        VoucherResponse voucherResponse = new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType()
        );
        List<Voucher> vouchers = List.of(voucher);

        given(defaultVoucherService.getVoucherListByVoucherType(any(VoucherType.class)))
                .willReturn(vouchers);
        given(voucherConverter.convertVoucherDto(any(PercentDiscountVoucher.class)))
                .willReturn(voucherResponse);

        this.mockMvc.perform(get("/api/v1/vouchers/type/" + voucher.getVoucherType())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherId").value(voucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[0].voucherType").value(voucher.getVoucherType().toString()));
    }

    @Test
    @DisplayName("CreatedAt을 기준으로 정렬된 Vouchers를 찾을 수 있다.")
    void findVoucherOrderByCreatedAt()  throws Exception {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 5L, LocalDateTime.now());
        VoucherResponse voucherResponse = new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType()
        );
        List<Voucher> vouchers = List.of(voucher);

        given(defaultVoucherService.getVoucherListOrderByCreatedAt())
                .willReturn(vouchers);
        given(voucherConverter.convertVoucherDto(any(PercentDiscountVoucher.class)))
                .willReturn(voucherResponse);

        this.mockMvc.perform(get("/api/v1/vouchers/created-at")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherId").value(voucher.getVoucherId().toString()))
                .andExpect(jsonPath("$[0].createdAt").value(voucher.getCreatedAt().toString()));
    }

    @Test
    @DisplayName("Voucher를 수정 할 수 있다.")
    void updateVoucher() throws Exception {
        var voucherRequest = new UpdateVoucherRequest(UUID.randomUUID(), 2000L);
        String param = objectMapper.writeValueAsString(voucherRequest);

        FixedAmountVoucher voucher = new FixedAmountVoucher(voucherRequest.voucherId(), 2000L, LocalDateTime.now());
        VoucherResponse voucherResponse = new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                voucher.getVoucherType()
        );

        given(defaultVoucherService.updateVoucher(any(UpdateVoucherRequest.class)))
                .willReturn(voucher);
        given(voucherConverter.convertVoucherDto(any(FixedAmountVoucher.class)))
                .willReturn(voucherResponse);

        this.mockMvc.perform(put("/api/v1/vouchers/{voucherId}", voucher.getVoucherId())
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value").value(voucher.getValue()))
                .andExpect(jsonPath("$.voucherType").value(voucher.getVoucherType().toString()));
    }

    @Test
    @DisplayName("Voucher를 삭제 할 수 있다.")
    void deleteVoucher() throws Exception {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 2000L, LocalDateTime.now());
        willDoNothing().given(defaultVoucherService).deleteVoucher(voucher.getVoucherId());

        this.mockMvc.perform(delete("/api/v1/vouchers/" + voucher.getVoucherId()))
                .andExpect(status().isOk());
    }
}