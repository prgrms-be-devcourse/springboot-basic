package org.prgrms.voucher.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.controller.web.VoucherWebController;
import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.PercentDiscountVoucher;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

                FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
                FixedAmountVoucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());

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

        @Nested
        @DisplayName("get요청과 존재하지 않는 바우처 티입을 받으면")
        class ContextGetRequestWithParamWrongVoucherType {

            FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            FixedAmountVoucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);

            @Test
            @DisplayName("잘못된 인자 예외를 반환한다.")
            void itReturnEmptyListByJsonType() throws Exception {

                when(voucherService.list()).thenReturn(vouchers);

                mockMvc.perform(get("/api/v1/vouchers?voucherType=HELLO"))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("get요청과 PERCENT_DISCOUNT 바우처 티입을 받으면")
        class ContextGetRequestWithParamVoucherType {

            FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            FixedAmountVoucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            PercentDiscountVoucher thirdVoucher = new PercentDiscountVoucher(3L, 100,VoucherType.PERCENT_DISCOUNT, LocalDateTime.now(), LocalDateTime.now());

            List<Voucher> vouchers = List.of(firstVoucher, secondVoucher, thirdVoucher);

            @Test
            @DisplayName("해당 바우처타입의 바우처들을 json형태로 반환한다.")
            void itReturnVouchersByJsonType() throws Exception {

                when(voucherService.list()).thenReturn(vouchers);

                mockMvc.perform(get("/api/v1/vouchers?voucherType=PERCENT_DISCOUNT"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$[0].voucherId").value("3"))
                        .andExpect(jsonPath("$[0].discountValue").value("100"))
                        .andExpect(jsonPath("$[0].voucherType").value("PERCENT_DISCOUNT"));
            }
        }

        @Nested
        @DisplayName("get요청과 해당하지 않는 날짜 1000/11/11 이전 범위를 받으면")
        class ContextGetRequestWithParamWrongDateTime {

            FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            FixedAmountVoucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);

            @Test
            @DisplayName("비어있는 리스트를 반환한다.")
            void itReturnEmptyListByJsonType() throws Exception {

                when(voucherService.list()).thenReturn(vouchers);

                mockMvc.perform(get("/api/v1/vouchers?before=1000-11-11"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.size()").value(0));
            }
        }

        @Nested
        @DisplayName("get요청과 해당하는 날짜 2022-03-18 이후 범위를 받으면")
        class ContextGetRequestWithParamDateTime {

            FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            FixedAmountVoucher secondVoucher = new FixedAmountVoucher(2L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
            List<Voucher> vouchers = List.of(firstVoucher, secondVoucher);

            @Test
            @DisplayName("해당하는 바우처들 리스트를 반환한다.")
            void itReturnEmptyListByJsonType() throws Exception {

                when(voucherService.list()).thenReturn(vouchers);

                mockMvc.perform(get("/api/v1/vouchers?after=2022-03-18"))
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

    @Nested
    @DisplayName("findVoucherById 메서드는")
    class DescribeFindById {

        @Nested
        @DisplayName("존재하지 않는 id가 인자로 들어왔을때")
        class ContextReceiveNullId {

            Long wrongId = -1L;

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequestResponse() throws Exception {

                when(voucherService.getVoucherById(wrongId)).thenThrow(IllegalArgumentException.class);

                mockMvc.perform(get("/api/v1/vouchers/-1"))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("Id를 인자로 받으면")
        class ContextReceiveParamVoucherId {

            @Test
            @DisplayName("해당 id의 바우처를 json 형태로 반환한다.")
            void itReturnVoucherByJsonType() throws Exception {

                FixedAmountVoucher firstVoucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());
                Long paramId = 1L;

                when(voucherService.getVoucherById(paramId)).thenReturn(firstVoucher);

                mockMvc.perform(get("/api/v1/vouchers/1"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.voucherId").value("1"))
                        .andExpect(jsonPath("$.discountValue").value("100"))
                        .andExpect(jsonPath("$.voucherType").value("FIXED_AMOUNT"));
            }
        }
    }

    @Nested
    @DisplayName("deleteVoucherById 메서드는")
    class DescribeDeleteById {

        @Nested
        @DisplayName("존재하지 않는 ID를 삭제 요청 받으면")
        class ContextRequestWrongId {

            Long wrongId = -1L;

            @Test
            @DisplayName("잘못된 요청 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                doThrow(IllegalArgumentException.class).when(voucherService).deleteVoucherById(wrongId);

                mockMvc.perform(delete("/api/v1/vouchers/-1"))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("존재하는 ID를 ")
        class ContextRequestId {

            @Test
            @DisplayName("서비스의 deleteVoucherById메서드를 호출하고 성공 응답을 반환한다.")
            void itDeleteVoucherAndReturnOk() throws Exception {

                mockMvc.perform(delete("/api/v1/vouchers/1"))
                        .andExpect(status().isOk());

                verify(voucherService).deleteVoucherById(any());
            }
        }
    }
}