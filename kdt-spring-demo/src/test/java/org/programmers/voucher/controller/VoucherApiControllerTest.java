package org.programmers.voucher.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.programmers.voucher.model.CreateVoucherRequest;
import org.programmers.voucher.model.SearchVoucherTypeRequest;
import org.programmers.voucher.model.VoucherBase;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.service.VoucherJdbcService;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    VoucherJdbcService voucherJdbcService;

    @InjectMocks
    public VoucherApiController voucherApiController;

    VoucherBase voucherBase;

    @BeforeAll
    public void createController() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voucherApiController).build();
        voucherBase = new VoucherBase(UUID.randomUUID(), VoucherType.FIXED, 10L, LocalDateTime.now());
        voucherJdbcService.save(voucherBase);
        System.out.println("voucherBase Id : " + voucherBase.getVoucherId());
    }

    @Test
    public void getVouchersTest() throws Exception {
        VoucherBase testVoucherBase = new VoucherBase(UUID.randomUUID(), VoucherType.FIXED, 10L, LocalDateTime.now());
        List<VoucherBase> list = Arrays.asList(testVoucherBase);

        when(voucherJdbcService.findAllVouchers()).thenReturn(list);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/vouchers").contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print());
        verify(voucherJdbcService).findAllVouchers();
    }


    @Test
    public void createVouchersTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/voucher")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new CreateVoucherRequest(VoucherType.FIXED, 22))))
                .andExpect(status().isOk())
                .andDo(print());

    }

}