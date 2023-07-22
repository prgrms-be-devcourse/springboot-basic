package com.prgrms.vouhcer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.common.KeyGenerator;
import com.prgrms.voucher.controller.VoucherV1Controller;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(VoucherV1Controller.class)
class VoucherV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @MockBean
    private KeyGenerator keyGenerator;

    @Test
    @DisplayName("바우처 타입과 생성 일을 기준으로 검색하면 관련 목록을 Model에 저장하고 view에 반환한다.")
    void getVouchers_FilterWithVoucherTypeAndCreatedAt_ㅡModelAndView() throws Exception {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);
        LocalDateTime createdAt = LocalDateTime.of(2023, 7, 22, 12, 0);

        Voucher voucher = new FixedAmountVoucher(50,discount,voucherType,LocalDateTime.now());
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);

        given(voucherService.getAllVoucherList(voucherType, createdAt)).willReturn(List.of(voucherServiceResponse));

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vouchers")
                        .param("voucherType", voucherType.toString())
                        .param("createdAt", createdAt.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("vouchers"))
                .andExpect(MockMvcResultMatchers.view().name("views/vouchers"));

        then(voucherService).should().getAllVoucherList(voucherType, createdAt);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 바우처 삭제 요청이 들어왔을 때 삭제 후 목록 조회 화면으로 redirect 한다.")
    void deleteVoucher_VoucherId_RedirectVouchers() throws Exception {
        //given
        int voucherId = 1;

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vouchers/delete/{voucherId}", voucherId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/v1/vouchers"));

        then(voucherService).should().deleteByVoucherId(voucherId);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 상세 게시글 요청이 들어오면 해당 게시글에 대한 정보를 Model에 저장하고 이를 View에 반환한다.")
    void detailVoucher_VoucherId_ModelAndView() throws Exception {
        //given
        int voucherId = 50;
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);

        Voucher voucher = new FixedAmountVoucher(voucherId,discount,voucherType,LocalDateTime.now());
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);

        given(voucherService.detailVoucher(voucherId)).willReturn(voucherServiceResponse);

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vouchers/detail/{voucherId}", voucherId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("voucher"))
                .andExpect(MockMvcResultMatchers.view().name("views/detail"));

        then(voucherService).should().detailVoucher(voucherId);
    }

    @Test
    @DisplayName("바우처 생성 요청이 들어왔을 때 생성 후에 목록으로 redirect 한다.")
    void createVoucher_PostRequest_RedirectVouchers() throws Exception {
        //when_then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/vouchers/new")
                        .param("voucherType", VoucherType.FIXED_AMOUNT_VOUCHER.toString())
                        .param("discountAmount", "10.0"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/v1/vouchers"));

        then(voucherService).should().createVoucher(anyInt(), eq(VoucherType.FIXED_AMOUNT_VOUCHER), eq(10.0), any(LocalDateTime.class));
    }

}
