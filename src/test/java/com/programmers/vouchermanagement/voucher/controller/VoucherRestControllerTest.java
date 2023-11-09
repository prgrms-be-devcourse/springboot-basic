package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.programmers.vouchermanagement.voucher.controller.MvcControllerResource.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoucherRestController.class)
@ActiveProfiles("api")
class VoucherRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    VoucherService voucherService;

    @Test
    @DisplayName("바우처 생성을 요청한다.")
    void createVoucher() throws Exception {
        when(voucherService.create(CREATE_VOUCHER_REQUEST)).thenReturn(VOUCHER_RESPONSE);

        String response = mockMvc.perform(post("/api/v1/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(CREATE_VOUCHER_REQUEST)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(VOUCHER_RESPONSE));
    }

    @Test
    @DisplayName("모든 바우처 조회를 요청한다.")
    void readAllVouchers1() throws Exception {
        when(voucherService.readAll()).thenReturn(VOUCHERS);

        String response = mockMvc.perform(get("/api/v1/vouchers"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(VOUCHERS));
    }

    @Test
    @DisplayName("모든 바우처 조회를 요청한다. + 쿼리 스트링(filter=all)")
    void readAllVouchers2() throws Exception {
        when(voucherService.readAll()).thenReturn(VOUCHERS);

        String response = mockMvc.perform(get("/api/v1/vouchers")
                        .param("filter", "all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(VOUCHERS));
    }

    @Test
    @DisplayName("입력 기간 안에 있는 모든 바우처 조회를 요청한다. + 쿼리 스트링(filter=created-at...)")
    void readAllByCreatedAt() throws Exception {
        LocalDate from = LocalDate.of(2022, 12, 25);
        LocalDate to = LocalDate.now();
        when(voucherService.readAllByCreatedAt(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(VOUCHERS_CREATED_NOW);

        String response = mockMvc.perform(get("/api/v1/vouchers")
                        .param("filter", "created-at")
                        .param("from", from.toString())
                        .param("to", to.toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(VOUCHERS_CREATED_NOW));
    }

    @Test
    @DisplayName("입력 타입에 해당하는 모든 바우처 조회를 요청한다. + 쿼리 스트링(filter=type...)")
    void readAllByType() throws Exception {
        String typeName = "FIXED";
        when(voucherService.readAllByType(typeName)).thenReturn(FIXED_AMOUNT_VOUCHERS);

        String response = mockMvc.perform(get("/api/v1/vouchers")
                        .param("filter", "type")
                        .param("type-name", typeName))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(FIXED_AMOUNT_VOUCHERS));
    }

    @Test
    @DisplayName("바우처 id로 바우처 조회를 요청한다.")
    void readVoucherById() throws Exception {
        when(voucherService.readById(VOUCHER_ID)).thenReturn(VOUCHER_RESPONSE);

        String response = mockMvc.perform(get("/api/v1/vouchers/" + VOUCHER_ID.toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(VOUCHER_RESPONSE));
    }

    @Test
    @DisplayName("바우처 id로 바우처를 삭제한다.")
    void deleteVoucher() throws Exception {
        when(voucherService.delete(VOUCHER_ID)).thenReturn(VOUCHER_RESPONSE);

        String response = mockMvc.perform(delete("/api/v1/vouchers/" + VOUCHER_ID.toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(VOUCHER_RESPONSE));
    }

    @Test
    @DisplayName("바우처 id로 바우처를 업데이트한다.")
    void update() throws Exception {
        CreateVoucherRequest updateVoucherRequest = new CreateVoucherRequest("FIXED", 100000);
        VoucherResponse updatedVoucherResponse = new VoucherResponse(VOUCHER_ID, VOUCHER.getCreatedAt(), CREATE_VOUCHER_REQUEST.typeName(), CREATE_VOUCHER_REQUEST.discountValue());
        when(voucherService.update(VOUCHER_ID, updateVoucherRequest)).thenReturn(updatedVoucherResponse);

        String response = mockMvc.perform(put("/api/v1/vouchers/" + VOUCHER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(updateVoucherRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(OBJECT_MAPPER.writeValueAsString(updatedVoucherResponse));
    }
}