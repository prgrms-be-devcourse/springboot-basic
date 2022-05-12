package org.programmers.springbootbasic.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherController.class)
class VoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @Test
    @DisplayName("Voucher page를 반환할 수 있다.")
    void viewVouchersPage() throws Exception {
        mockMvc.perform(get("/vouchers")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vouchers", voucherService.getVoucherList()))
                .andExpect(view().name("vouchers"));
    }

    @Test
    void getVoucher() throws Exception {
        UUID voucherId = UUID.randomUUID();

        mockMvc.perform(get("/{voucherId}",String.valueOf(voucherId))
                        .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("voucher", voucherService.getVoucher(voucherId)))
                .andExpect(view().name("/voucher-details"));
    }

    @Test
    void viewNewVoucherPage() throws Exception {
        this.mockMvc.perform(get("/vouchers/new")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(view().name("new-vouchers"));
    }

    @Test
    void updateVoucher() throws Exception {
    }

    @Test
    void addNewVoucher() {
    }

    @Test
    void deleteVoucher() {
    }
}