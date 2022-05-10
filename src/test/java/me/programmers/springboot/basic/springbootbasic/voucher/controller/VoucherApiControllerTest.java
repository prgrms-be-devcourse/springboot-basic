package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoucherApiController.class)
class VoucherApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JdbcVoucherService voucherService;

    @Test
    void findAllTest() throws Exception {
        given(voucherService.getAllVouchers()).willReturn(List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        mockMvc.perform(get("/api/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findByIdTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixVoucher = new FixedAmountVoucher(uuid, 1000);
        given(voucherService.getVoucherById(uuid)).willReturn(fixVoucher);

        mockMvc.perform(get("/api/vouchers/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherId", is(fixVoucher.getVoucherId().toString())));
    }

    @Test
    void findByIdTestFail() throws Exception {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixVoucher = new FixedAmountVoucher(uuid, 1000);
        given(voucherService.getVoucherById(uuid)).willReturn(fixVoucher);

        mockMvc.perform(get("/api/vouchers/" + uuid + "dd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllFixVouchersTest() throws Exception {
        given(voucherService.getAllFixVouchers()).willReturn(List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        mockMvc.perform(get("/api/vouchers?type=fixed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findAllFixVouchersTestFail() throws Exception {
        given(voucherService.getAllFixVouchers()).willReturn(List.of(new FixedAmountVoucher(UUID.randomUUID(), 1000)));

        mockMvc.perform(get("/api/vouchers?type=fixedd"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void deleteByIdTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        FixedAmountVoucher fixVoucher = new FixedAmountVoucher(uuid, 1000);
        given(voucherService.getVoucherById(uuid)).willReturn(fixVoucher);

        mockMvc.perform(delete("/api/vouchers/" + uuid))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdTestFail() throws Exception {
        mockMvc.perform(delete("/api/vouchers/ddewqcxzds"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTest() throws Exception {
        VoucherCreateRequestDto createRequestDto = new VoucherCreateRequestDto("fixed", 1000L, 0L);

        given(voucherService.save(any())).willReturn(new FixedAmountVoucher(UUID.randomUUID(), 1000));

        mockMvc.perform(post("/api/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(objectMapper.writeValueAsString(createRequestDto)))
                .andExpect(status().isCreated());
    }

}