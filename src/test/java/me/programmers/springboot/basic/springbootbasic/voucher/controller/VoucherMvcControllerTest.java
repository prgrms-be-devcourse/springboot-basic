package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherUpdateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
                        .param("type", createRequest.getType())
                        .param("discountPrice", String.valueOf(createRequest.getDiscountPrice()))
                        .param("discountPercent", String.valueOf(createRequest.getDiscountPercent())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void updateVoucherTest() throws Exception {
        UUID uuid = UUID.randomUUID();

        FixedAmountVoucher voucher = new FixedAmountVoucher(uuid, 2000);
        given(voucherService.getVoucherById(any(UUID.class))).willReturn(voucher);

        VoucherUpdateRequestDto updateRequest = new VoucherUpdateRequestDto();
        updateRequest.setDiscountPrice(4000);

        String url = "/vouchers/" + uuid;
        mockMvc.perform(put(url)
                        .contentType("application/x-www-form-urlencoded")
                        .accept("application/x-www-form-urlencoded")
                        .param("discountPrice", String.valueOf(updateRequest.getDiscountPrice()))
                        .param("discountPercent", String.valueOf(updateRequest.getDiscountPercent())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertThat(voucher.getAmount()).isEqualTo(4000);
    }

}