package com.prgrms.vouhcer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.prgrms.common.KeyGenerator;
import com.prgrms.voucher.controller.VoucherV2Controller;
import com.prgrms.voucher.model.FixedAmountVoucher;
import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.FixedDiscount;
import com.prgrms.voucher.service.VoucherService;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(VoucherV2Controller.class)
class VoucherV2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @MockBean
    private KeyGenerator keyGenerator;

    @Test
    @DisplayName("바우처 타입과 생성 일을 기준으로 검색하면 관련 목록을 Json으로 반환하며 해당 목록에는 검색 조건에 해당하는 목록이 포함되어 있다.")
    void getVouchers_FilterWithVoucherTypeAndCreatedAt_Json() throws Exception {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = new FixedAmountVoucher(50,discount,voucherType,LocalDateTime.now());
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);
        when(voucherService.getAllVoucherList(voucherType, createdAt)).thenReturn(List.of(voucherServiceResponse));

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/vouchers")
                        .param("voucherType", voucherType.toString())
                        .param("createdAt", createdAt.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherType", is(voucherType.toString())))
                .andExpect(jsonPath("$[0].discount", is(20.0)));

        verify(voucherService).getAllVoucherList(voucherType, createdAt);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 바우처 삭제 요청이 들어왔을 때 삭제하고 나면 삭제한 바우처의 아이디를 반환한다.")
    void deleteVoucher_VoucherId_DeletedVoucherId() throws Exception {
        //given
        Integer voucherId = 1;
        when(voucherService.deleteByVoucherId(voucherId)).thenReturn(voucherId);

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v2/vouchers/{voucherId}", voucherId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(voucherId)));

        verify(voucherService).deleteByVoucherId(voucherId);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 상세 게시글 요청이 들어오면 해당 게시글에 대한 정보를 Json으로 반환한다.")
    void detailVoucher_VoucherID_JsonVoucherResponse() throws Exception {
        //given
        int voucherId =100;
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);

        Voucher voucher = new FixedAmountVoucher(voucherId,discount,voucherType,LocalDateTime.now());
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);
        when(voucherService.detailVoucher(voucherId)).thenReturn(voucherServiceResponse);

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/vouchers/{voucherId}", voucherId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherType", is(voucherType.toString())))
                .andExpect(jsonPath("$.discount", is(20.0)));

        verify(voucherService).detailVoucher(voucherId);
    }

    @Test
    @DisplayName("바우처 생성 요청이 들어왔을 때 생성 후에 목록으로 redirect 한다.")
    void createVoucher_PostRequest_RedirectVouchers()  throws Exception {
        //when_then
        mockMvc.perform(MockMvcRequestBuilders.post("/v2/vouchers")
                        .param("voucherType", VoucherType.FIXED_AMOUNT_VOUCHER.toString())
                        .param("discountAmount", "10.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("redirect:/v1/vouchers"));

        verify(voucherService).createVoucher(anyInt(), eq(VoucherType.FIXED_AMOUNT_VOUCHER), eq(10.0),
                any(LocalDateTime.class));
    }

}

