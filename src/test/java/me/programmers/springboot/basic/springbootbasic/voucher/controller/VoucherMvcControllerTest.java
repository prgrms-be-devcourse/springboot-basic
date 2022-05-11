package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoucherMvcController.class)
class VoucherMvcControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcVoucherService voucherService;

    @Test
    void showVoucherPageTest() throws Exception {
        String url = "/vouchers";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")));
    }



}