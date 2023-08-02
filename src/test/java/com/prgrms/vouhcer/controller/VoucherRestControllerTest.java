package com.prgrms.vouhcer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.voucher.controller.VoucherRestController;
import com.prgrms.voucher.controller.dto.VoucherCreateRequest;
import com.prgrms.voucher.controller.dto.VoucherListRequest;
import com.prgrms.voucher.controller.mapper.VoucherControllerConverter;
import com.prgrms.voucher.model.voucher.FixedAmountVoucher;
import com.prgrms.voucher.model.voucher.Voucher;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.FixedDiscount;
import com.prgrms.voucher.service.VoucherService;
import com.prgrms.voucher.service.dto.VoucherServiceCreateRequest;
import com.prgrms.voucher.service.dto.VoucherServiceListRequest;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(VoucherRestController.class)
class VoucherRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VoucherService voucherService;

    @MockBean
    private VoucherControllerConverter voucherControllerConverter;

    @Test
    @DisplayName("바우처 타입과 생성 일을 기준으로 검색하면 관련 목록을 Json으로 반환하며 해당 목록에는 검색 조건에 해당하는 목록이 포함되어 있다.")
    void getVouchers_FilterWithVoucherTypeAndCreatedAt_Json() throws Exception {
        //given
        String voucherId = "22";
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);
        LocalDateTime createdAt = LocalDateTime.now();


        Voucher voucher = new FixedAmountVoucher(voucherId, discount, voucherType, createdAt);
        VoucherListRequest voucherListRequest = new VoucherListRequest(voucherType,"2023-07-20T10:30:00");
        VoucherServiceListRequest voucherServiceListRequest = voucherControllerConverter.ofVoucherServiceListRequest(voucherListRequest);
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);


        given(voucherService.getAllVoucherList(voucherServiceListRequest)).willReturn(
                List.of(voucherServiceResponse));

        //when_then
        mockMvc.perform(get("/api/vouchers")
                        .param("voucherType", voucherType.toString())
                        .param("createdAt", createdAt.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultMsg", is("SELECT SUCCESS")));


        then(voucherService).should().getAllVoucherList(voucherServiceListRequest);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 바우처 삭제 요청이 들어왔을 때 삭제하고 나면 삭제한 바우처의 아이디를 반환한다.")
    void deleteVoucher_VoucherId_DeletedVoucherId() throws Exception {
        //given
        String voucherId = "1";
        given(voucherService.deleteByVoucherId(voucherId)).willReturn(voucherId);

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/vouchers/{voucherId}", voucherId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.deletedVoucherId", is(voucherId)));

        then(voucherService).should().deleteByVoucherId(voucherId);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 상세 게시글 요청이 들어오면 해당 게시글에 대한 정보를 Json으로 반환한다.")
    void detailVoucher_VoucherID_JsonVoucherResponse() throws Exception {
        //given
        String voucherId = "100";
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);

        Voucher voucher = new FixedAmountVoucher(voucherId, discount, voucherType,
                LocalDateTime.now());
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);

        given(voucherService.detailVoucher(voucherId)).willReturn(voucherServiceResponse);

        //when_then
        mockMvc.perform(get("/api/vouchers/{voucherId}", voucherId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.voucherType", is(voucherType.toString())))
                .andExpect(jsonPath("$.result.discount", is(20.0)));

        then(voucherService).should().detailVoucher(voucherId);
    }

    @Test
    @DisplayName("바우처 생성 요청이 들어왔을 때 생성 후에 목록으로 redirect 한다.")
    void createVoucher_PostRequest_RedirectVouchers() throws Exception {
        // given
        String voucherId = "1";
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);
        LocalDateTime createdAt = LocalDateTime.now();

        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(voucherType,20.0);
        Voucher voucher = new FixedAmountVoucher(voucherId, discount, voucherType, createdAt);
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);
        VoucherServiceCreateRequest voucherServiceCreateRequest = new VoucherServiceCreateRequest(voucherType,20.0);

        given(voucherControllerConverter.ofVoucherServiceCreateRequest(voucherCreateRequest)).willReturn(voucherServiceCreateRequest);
        given(voucherService.createVoucher(voucherServiceCreateRequest)).willReturn(voucherServiceResponse);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/vouchers")
                        .contentType(MediaType.APPLICATION_JSON_UTF8) // Use APPLICATION_JSON_UTF8 here
                        .content(new ObjectMapper().writeValueAsString(voucherCreateRequest)))

                // then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) // Use APPLICATION_JSON_UTF8 here
                .andExpect(jsonPath("$.voucherId").value(voucherId))
                .andExpect(jsonPath("$.voucherType").value(voucherType.toString()))
                .andExpect(jsonPath("$.discount").value(20));


        then(voucherService).should()
                .createVoucher( eq(voucherServiceCreateRequest));
    }

}

