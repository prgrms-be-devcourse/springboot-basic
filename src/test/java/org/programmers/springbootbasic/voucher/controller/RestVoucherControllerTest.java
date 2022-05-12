package org.programmers.springbootbasic.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestVoucherController.class)
class RestVoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VoucherService voucherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createVoucher() throws Exception {
        String param = objectMapper.writeValueAsString(
                new CreateVoucherRequest(1500, "FIXED"));

        mockMvc.perform(post("/api/v1/vouchers")
                                .content(param)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.value").value("1000"))
                .andExpect(jsonPath("$.data.voucherType").value("FIXED"));
    }

    @Test
    void findVouchers() {
    }

    @Test
    void findVoucher() {
    }

    @Test
    void updateVoucher() {
    }

    @Test
    void deleteVoucher() {
    }
}