package org.prgrms.application.controller.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.application.controller.voucher.request.VoucherRegisterRequest;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherDto;
import org.prgrms.application.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;
import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(VoucherMvcController.class)
class VoucherMvcControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VoucherService voucherService;

    private VoucherDto voucher1;
    private VoucherDto voucher2;
    private List<Voucher> vouchers;

    @BeforeEach
    void setUp() {
        voucher1 = new VoucherDto(1L, FIXED.applyPolicy(1000));
        voucher2 = new VoucherDto(2L, PERCENT.applyPolicy(33));
        vouchers = Arrays.asList(Voucher.of(voucher1.voucherId(),voucher1.voucherTypePolicy()), Voucher.of(voucher2.voucherId(),voucher2.voucherTypePolicy()));
    }

    @Test
    @DisplayName("바우처의 정보를 모두 조회에 성공한다.")
    void getAllVouchers() throws Exception {
        List<VoucherDto> voucherDtos = vouchers.stream().map(VoucherDto::of).collect(Collectors.toList());
        //given
        given(voucherService.getVouchers()).willReturn(voucherDtos);

        mvc.perform(get("/api/v1/mvc/vouchers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherType").value(FIXED.name()))
                .andExpect(jsonPath("$[1].discountAmount").value(30));
    }

    @Test
    void getVouchersByType() throws Exception {
        given(voucherService.getVouchersByType(any())).willReturn(Collections.singletonList(voucher1));

        mvc.perform(get("/api/v1/mvc/vouchers/voucherType")
                        .param("voucherType", "FIXED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherType").value(FIXED.name()))
                .andExpect(jsonPath("$[0].discountAmount").value(10000));
    }

    @Test
    void createVoucher() throws Exception {
        VoucherRegisterRequest registerRequest = new VoucherRegisterRequest("PERCENT", 20);
        //given
        given(voucherService.createVoucher(any(), anyDouble())).willReturn(1L);

        mvc.perform(post("/api/v1/mvc/vouchers/new")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1L));
    }


    @Test
    void deleteVoucher() throws Exception {

        doNothing().when(voucherService).deleteVoucher(1L);

        mvc.perform(delete("/api/v1/mvc/vouchers/delete/{id}", 1L))
                .andExpect(status().isOk());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }


}