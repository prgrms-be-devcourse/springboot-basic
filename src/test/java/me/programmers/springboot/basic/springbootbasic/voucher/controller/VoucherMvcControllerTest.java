package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoucherMvcController.class)
class VoucherMvcControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JdbcVoucherService voucherService;

    @Test
    void showVoucherPageTest() throws Exception {
        String url = "/vouchers";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }

    @Test
    void showCreateVoucherPageTest() throws Exception {
        String url = "/vouchers/new";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }

    @Test
    void showDetailVoucherPageTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        given(voucherService.getVoucherById(any(UUID.class))).willReturn(new FixedAmountVoucher(uuid, 2000));

        String url = "/vouchers/" + uuid;
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }

    @Test
    void createVoucherTest() throws Exception {
        String url = "/vouchers/new";

        VoucherCreateRequestDto createRequest = new VoucherCreateRequestDto("fixed", 1000, 0);

        mockMvc.perform(post(url)
                        .contentType("application/x-www-form-urlencoded")
                        .accept("application/x-www-form-urlencoded")
                        .param("type", "fixed")
                        .param("discountPrice", "1000")
                        .param("discountPercent", "0"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

}