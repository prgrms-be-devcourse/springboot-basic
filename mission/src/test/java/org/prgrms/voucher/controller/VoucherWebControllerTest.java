package org.prgrms.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.controller.web.VoucherWebController;
import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.prgrms.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoucherWebController.class)
public class VoucherWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoucherService voucherService;

    @Nested
    @DisplayName("createVoucher 메서드는")
    class DescribeCreateVoucher {

        @Nested
        @DisplayName("요청된 데이터의 할인값이 음수면")
        class ContextReceiveWrongDiscountValue {

            RequestBuilder request = MockMvcRequestBuilders
                    .post("/api/v1/vouchers")
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"discountValue\":-1, \"voucherType\":\"FIXED_AMOUNT\"}")
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("요청 데이터의 바우처 타입이  null이면")
        class ContextReceiveWrongVoucherType {

            RequestBuilder request = MockMvcRequestBuilders
                    .post("/api/v1/vouchers")
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"discountValue\":1, \"voucherType\":\"null\"}")
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("요청된 데이터의 할인값 1, 바우처타입 FIXED_AMOUNT을 데이터를 받으면")
        class ContextPostRequestMapping {

            RequestBuilder request = MockMvcRequestBuilders
                    .post("/api/v1/vouchers")
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"discountValue\":1, \"voucherType\":\"FIXED_AMOUNT\"}")
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("서비스의 create() 메서드를 호출하고 Success를 반환한다.")
            void itSaveVoucherReturnSuccessResponse() throws Exception {

                    mockMvc.perform(request)
                            .andExpect(status().isOk());

                verify(voucherService).create(any(VoucherDto.VoucherRequest.class));
            }
        }
    }

    @Nested
    @DisplayName("findVouchers 메서드는")
    class DescribeFindVouchers {

        @Nested
        @DisplayName("get요청을 받으면")
        class ContextGetRequest {

            @Test
            @DisplayName("바우처 리스트를 json형태로 반환한다.")
            void itReturnVouchersByJsonType() throws Exception {

                FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now());
                FixedAmountVoucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now());

                List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);

                when(voucherService.list()).thenReturn(vouchers);

                mockMvc.perform(get("/api/v1/vouchers"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].voucherId").value("1"))
                        .andExpect(jsonPath("$[0].discountValue").value("100"))
                        .andExpect(jsonPath("$[0].voucherType").value("FIXED_AMOUNT"))
                        .andExpect(jsonPath("$[1].voucherId").value("2"))
                        .andExpect(jsonPath("$[1].discountValue").value("100"))
                        .andExpect(jsonPath("$[1].voucherType").value("FIXED_AMOUNT"));

            }
        }
    }
}