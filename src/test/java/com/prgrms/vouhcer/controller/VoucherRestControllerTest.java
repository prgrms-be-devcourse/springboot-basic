package com.prgrms.vouhcer.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.prgrms.common.util.Generator;
import com.prgrms.voucher.controller.VoucherRestController;
import com.prgrms.voucher.controller.dto.VoucherListRequest;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(VoucherRestController.class)
class VoucherRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VoucherService voucherService;

    class TestGenerator implements Generator {

        @Override
        public String makeKey() {
            return "22";
        }

        @Override
        public LocalDateTime makeDate() {
            String str = "2023-07-29 13:47:13.248";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            return LocalDateTime.parse(str, formatter);
        }
    }

    private TestGenerator testGenerator;

    @BeforeEach
    void setUp() {
        testGenerator = new TestGenerator();
    }

    @Test
    @DisplayName("바우처 타입과 생성 일을 기준으로 검색하면 관련 목록을 Json으로 반환하며 해당 목록에는 검색 조건에 해당하는 목록이 포함되어 있다.")
    void getVouchers_FilterWithVoucherTypeAndCreatedAt_Json() throws Exception {
        //given
        VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        Discount discount = new FixedDiscount(20);
        LocalDateTime createdAt = testGenerator.makeDate();
        String id = testGenerator.makeKey();


        Voucher voucher = new FixedAmountVoucher(id, discount, voucherType, LocalDateTime.now());
        VoucherServiceResponse voucherServiceResponse = new VoucherServiceResponse(voucher);
        VoucherServiceListRequest voucherListRequest = new VoucherServiceListRequest(voucherType, createdAt);

        given(voucherService.getAllVoucherList(voucherListRequest)).willReturn(
                List.of(voucherServiceResponse));

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/vouchers")
                        .param("voucherType", voucherType.toString())
                        .param("createdAt", createdAt.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].voucherType", is(voucherType.toString())))
                .andExpect(jsonPath("$[0].discount", is(20.0)));

        then(voucherService).should().getAllVoucherList(voucherListRequest);
    }

    @Test
    @DisplayName("바우처 아이디에 해당하는 바우처 삭제 요청이 들어왔을 때 삭제하고 나면 삭제한 바우처의 아이디를 반환한다.")
    void deleteVoucher_VoucherId_DeletedVoucherId() throws Exception {
        //given
        String voucherId = "1";
        given(voucherService.deleteByVoucherId(voucherId)).willReturn(voucherId);

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v2/vouchers/{voucherId}", voucherId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(voucherId)));

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
        mockMvc.perform(MockMvcRequestBuilders.get("/v2/vouchers/{voucherId}", voucherId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.voucherType", is(voucherType.toString())))
                .andExpect(jsonPath("$.discount", is(20.0)));

        then(voucherService).should().detailVoucher(voucherId);
    }

    @Test
    @DisplayName("바우처 생성 요청이 들어왔을 때 생성 후에 목록으로 redirect 한다.")
    void createVoucher_PostRequest_RedirectVouchers() throws Exception {
        //given
        VoucherServiceCreateRequest serviceCreateRequest = new VoucherServiceCreateRequest(VoucherType.FIXED_AMOUNT_VOUCHER,10);

        //when_then
        mockMvc.perform(MockMvcRequestBuilders.post("/v2/vouchers")
                        .param("voucherType", VoucherType.FIXED_AMOUNT_VOUCHER.toString())
                        .param("discountAmount", "10.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("redirect:/v1/vouchers"));

        then(voucherService).should()
                .createVoucher(anyString(), eq(serviceCreateRequest),
                        any(LocalDateTime.class));
    }

}

