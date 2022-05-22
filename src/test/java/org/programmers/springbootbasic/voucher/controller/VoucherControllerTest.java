package org.programmers.springbootbasic.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.application.voucher.controller.VoucherController;
import org.programmers.springbootbasic.application.voucher.controller.VoucherConverter;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherController.class)
class VoucherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultVoucherService defaultVoucherService;

    @MockBean
    private VoucherConverter voucherConverter;

    @Test
    @DisplayName("Voucher page를 반환할 수 있다.")
    void viewVouchersPage() throws Exception {
        mockMvc.perform(get("/vouchers")
                        .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vouchers", defaultVoucherService.getVoucherList()))
                .andExpect(view().name("vouchers"));
    }

    @Test
    @DisplayName("new-vouchers page를 반환할 수 있다.")
    void viewNewVoucherPage() throws Exception {
        this.mockMvc.perform(get("/vouchers/new")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(view().name("new-vouchers"));
    }

    @Test
    @DisplayName("voucher를 update하고 redirect 할 수 있다.")
    void updateVoucher() throws Exception {
        this.mockMvc.perform(put("/vouchers/details/update")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/vouchers"))
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처를 추가 할 수 있다.")
    void addNewVoucher() throws Exception{
        this.mockMvc.perform(post("/vouchers/new")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/vouchers"))
                .andExpect(redirectedUrl("/vouchers"));
    }

    @Test
    @DisplayName("바우처를 삭제 할 수 있다.")
    void deleteVoucher() throws Exception {
        UUID voucherId = UUID.randomUUID();

        this.mockMvc.perform(delete("/vouchers/details/delete")
                        .param("voucherId", String.valueOf(voucherId))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/vouchers"))
                .andExpect(redirectedUrl("/vouchers"));
    }
    @Test
    @DisplayName("Voucher-detail page를 반환할 수 있다.")
    void getVoucher() throws Exception {
        UUID voucherId = UUID.randomUUID();

        mockMvc.perform(get("/{voucherId}", voucherId)
                        .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attribute("voucher", defaultVoucherService.getVoucher(voucherId)))
                .andExpect(view().name("/voucher-details"));
    }
}