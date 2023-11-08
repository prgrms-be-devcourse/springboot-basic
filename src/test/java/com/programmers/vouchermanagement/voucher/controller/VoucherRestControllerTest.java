package com.programmers.vouchermanagement.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherRestController.class)
@ActiveProfiles("api")
class VoucherRestControllerTest {
    static ObjectMapper objectMapper;
    static List<VoucherResponse> vouchers;
    static Voucher voucher;
    static UUID voucherId;
    static CreateVoucherRequest createVoucherRequest;
    static VoucherResponse voucherResponse;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    VoucherService voucherService;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        vouchers = List.of(
                VoucherResponse.from(new Voucher("FIXED", 100)),
                VoucherResponse.from(new Voucher("PERCENT", 50))
        );

        createVoucherRequest = new CreateVoucherRequest("PERCENT", 50);
        voucher = new Voucher(createVoucherRequest.typeName(), createVoucherRequest.discountValue());
        voucherId = voucher.getId();
        voucherResponse = VoucherResponse.from(voucher);
    }

    @Test
    @DisplayName("바우처 생성을 요청한다.")
    void createVoucher() throws Exception {
        when(voucherService.create(createVoucherRequest)).thenReturn(voucherResponse);

        String response = mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createVoucherRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(voucherResponse));
    }

    @Test
    @DisplayName("모든 바우처 조회를 요청한다.")
    void readAllVouchers() throws Exception {
        when(voucherService.readAll()).thenReturn(vouchers);

        String response = mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(vouchers));
    }

    @Test
    @DisplayName("모든 바우처 조회를 요청한다. + 쿼리 스트링(filter=all)")
    void readAllVouchersWithRequestParam() throws Exception {
        when(voucherService.readAll()).thenReturn(vouchers);

        String response = mockMvc.perform(get("/api/v1/vouchers?filter=all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(vouchers));
    }

    @Test
    @DisplayName("바우처 id로 바우처 조회를 요청한다.")
    void readVoucherById() throws Exception {
        when(voucherService.readById(voucherId)).thenReturn(voucherResponse);

        String response = mockMvc.perform(get("/api/v1/vouchers/" + voucherId.toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(voucherResponse));
    }

    @Test
    @DisplayName("바우처 id로 바우처를 삭제한다.")
    void deleteVoucher() throws Exception {
        when(voucherService.delete(voucherId)).thenReturn(voucherResponse);

        String response = mockMvc.perform(delete("/api/v1/vouchers/" + voucherId.toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(voucherResponse));
    }

    @Test
    @DisplayName("바우처 id로 바우처를 업데이트한다.")
    void update() throws Exception {
        CreateVoucherRequest updateVoucherRequest = new CreateVoucherRequest("FIXED", 100000);
        VoucherResponse updatedVoucherResponse = new VoucherResponse(voucherId, voucher.getCreatedAt(), createVoucherRequest.typeName(), createVoucherRequest.discountValue());
        when(voucherService.update(voucherId, updateVoucherRequest)).thenReturn(updatedVoucherResponse);

        String response = mockMvc.perform(put("/api/v1/vouchers/" + voucherId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateVoucherRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(objectMapper.writeValueAsString(updatedVoucherResponse));
    }

//    @Test
//    void readAllByCreatedAt() {
//    }
//
//    @Test
//    void readAllByType() {
//    }
}