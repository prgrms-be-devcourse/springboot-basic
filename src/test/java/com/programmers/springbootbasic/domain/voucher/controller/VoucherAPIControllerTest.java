package com.programmers.springbootbasic.domain.voucher.controller;

import com.programmers.springbootbasic.domain.voucher.dto.VoucherServiceRequestDto;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherService;
import com.programmers.springbootbasic.domain.voucher.service.VoucherType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherAPIController.class)
class VoucherAPIControllerTest {

    private static final int VOUCHER_TYPE = 1;
    private static final UUID VOUCHER_ID = UUID.randomUUID();
    private static final long VOUCHER_VALUE = 20L;
    private static final LocalDate VOUCHER_CREATE_DATE = LocalDate.now();
    private static final Voucher VOUCHER = VoucherType.of(VOUCHER_TYPE, VOUCHER_ID, VOUCHER_VALUE, VOUCHER_CREATE_DATE);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private VoucherService voucherService;

    @Test
    void testFindAllVouchersSuccess() throws Exception {
        // Arrange
        given(voucherService.findAllVouchers()).willReturn(List.of(VOUCHER));
        // Act & Assert
        mvc.perform(get("/api/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].voucherId").value(VOUCHER_ID.toString()))
                .andExpect(jsonPath("$.data[0].value").value(VOUCHER_VALUE))
                .andExpect(jsonPath("$.data[0].voucherType").value(VOUCHER_TYPE))
                .andExpect(jsonPath("$.data[0].createdAt").value(VOUCHER_CREATE_DATE.toString()));
    }

    @Test
    void testCreateVoucherSuccess() throws Exception {
        // Arrange
        var post = post("/api/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("""
                        {
                            "voucherType": 1,
                            "value": 20
                        }
                        """);
        // Act & Assert
        mvc.perform(post)
                .andExpect(status().isOk());
    }

    @Test
    void testCreateVoucherFail() throws Exception {
        // Arrange
        given(voucherService.createVoucher(any(VoucherServiceRequestDto.class))).willThrow(IllegalArgumentException.class);
        var post = post("/api/vouchers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("""
                        {
                            "voucherType": 2,
                            "value": 20
                        }
                        """);
        // Act & Assert
        mvc.perform(post)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testFindVoucherByIdSuccess() throws Exception {
        // Arrange
        given(voucherService.findVoucherById(any(VoucherServiceRequestDto.class))).willReturn(VOUCHER);
        var get = get("/api/vouchers/" + VOUCHER_ID);
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data['voucherId']").value(VOUCHER_ID.toString()))
                .andExpect(jsonPath("$.data['value']").value(VOUCHER_VALUE))
                .andExpect(jsonPath("$.data['voucherType']").value(VOUCHER_TYPE))
                .andExpect(jsonPath("$.data['createdAt']").value(VOUCHER_CREATE_DATE.toString()));
    }

    @Test
    void testFindVoucherByIdFail() throws Exception {
        // Arrange
        given(voucherService.findVoucherById(any(VoucherServiceRequestDto.class))).willThrow(RuntimeException.class);
        var get = get("/api/vouchers/" + VOUCHER_ID);
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testFindVouchersByDateSuccess() throws Exception {
        // Arrange
        given(voucherService.findVouchersByDate(any(VoucherServiceRequestDto.class))).willReturn(List.of(VOUCHER));
        var get = get("/api/vouchers").param("date", VOUCHER_CREATE_DATE.toString());
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].voucherId").value(VOUCHER_ID.toString()))
                .andExpect(jsonPath("$.data[0].value").value(VOUCHER_VALUE))
                .andExpect(jsonPath("$.data[0].voucherType").value(VOUCHER_TYPE))
                .andExpect(jsonPath("$.data[0].createdAt").value(VOUCHER_CREATE_DATE.toString()));
    }

    @Test
    void testFindVouchersByDateFail() throws Exception {
        // Arrange
        given(voucherService.findVouchersByDate(any(VoucherServiceRequestDto.class))).willThrow(RuntimeException.class);
        var get = get("/api/vouchers/date/" + VOUCHER_CREATE_DATE.plusDays(1));
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testFindVouchersByTypeSuccess() throws Exception {
        // Arrange
        given(voucherService.findVouchersByType(any(VoucherServiceRequestDto.class))).willReturn(List.of(VOUCHER));
        var get = get("/api/vouchers").param("type", String.valueOf(VOUCHER_TYPE));
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].voucherId").value(VOUCHER_ID.toString()))
                .andExpect(jsonPath("$.data[0].value").value(VOUCHER_VALUE))
                .andExpect(jsonPath("$.data[0].voucherType").value(VOUCHER_TYPE))
                .andExpect(jsonPath("$.data[0].createdAt").value(VOUCHER_CREATE_DATE.toString()));
    }

    @Test
    void testFindVouchersByTypeFail() throws Exception {
        // Arrange
        given(voucherService.findVouchersByType(any(VoucherServiceRequestDto.class))).willThrow(IllegalArgumentException.class);
        var get = get("/api/vouchers/type/" + 3);
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateVoucherSuccess() throws Exception {
        // Arrange
        var put = put("/api/vouchers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.format("""
                        {
                            "voucherId": "%s",
                            "value": 20
                        }
                        """, VOUCHER_ID));
        // Act & Assert
        mvc.perform(put)
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateVoucherFail() throws Exception {
        // Arrange
        doThrow(IllegalArgumentException.class).when(voucherService).updateVoucher(any(VoucherServiceRequestDto.class));
        var put = put("/api/vouchers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("""
                        {
                            "voucherId": "AAAA-AAAA-AAAA",
                            "value": 20
                        }
                        """);
        // Act & Assert
        mvc.perform(put)
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testDeleteVoucherSuccess() throws Exception {
        // Arrange
        var delete = delete("/api/vouchers/" + VOUCHER_ID);
        // Act & Assert
        mvc.perform(delete)
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteVoucherFail() throws Exception {
        // Arrange
        doThrow(IllegalArgumentException.class).when(voucherService).deleteVoucher(any(VoucherServiceRequestDto.class));
        var delete = delete("/api/vouchers/" + VOUCHER_ID);
        // Act & Assert
        mvc.perform(delete)
                .andExpect(status().is4xxClientError());
    }
}