package com.waterfogsw.voucher.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waterfogsw.voucher.voucher.controller.VoucherControllerAdvice;
import com.waterfogsw.voucher.voucher.controller.VoucherRestController;
import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.PercentDiscountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFound;
import com.waterfogsw.voucher.voucher.service.VoucherService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoucherRestControllerTests {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private VoucherService voucherService;
    @InjectMocks
    private VoucherRestController voucherRestController;
    private MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(voucherRestController)
                .setControllerAdvice(VoucherControllerAdvice.class)
                .build();
    }

    @Nested
    @DisplayName("voucherAdd 메서드는")
    class Describe_voucherAdd {

        final String url = "/api/v1/vouchers";

        @Nested
        @DisplayName("바우처 타입이 바디에 없으면")
        class Context_without_voucherType {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void it_response_BadRequest() throws Exception {
                Map<String, String> postRequest = new HashMap<>();
                postRequest.put("type", "");
                postRequest.put("value", "1000");

                final String content = objectMapper.writeValueAsString(postRequest);
                final var request = post(url)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("바우처 값이 바디에 없으면")
        class Context_without_value {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void it_response_BadRequest() throws Exception {
                Map<String, String> postRequest = new HashMap<>();
                postRequest.put("type", "FIXED_AMOUNT");
                postRequest.put("value", "");

                final String content = objectMapper.writeValueAsString(postRequest);
                final var request = post(url)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("모든 값이 존재하면")
        class Context_with_all {

            @Test
            @DisplayName("200 을 응답한다")
            void it_response_BadRequest() throws Exception {
                final Voucher voucher = new FixedAmountVoucher(1L, 2000);
                when(voucherService.saveVoucher(any(Voucher.class))).thenReturn(voucher);

                final String content = objectMapper.writeValueAsString(voucher);
                final var request = post(url)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("voucherList 메서드는")
    class Describe_voucherList {

        final String url = "/api/v1/vouchers";

        @Nested
        @DisplayName("요청되면")
        class Context_with_call {

            @Test
            @DisplayName("저장된 모든 바우처 리스트를 반환한다")
            void it_return_list() throws Exception {
                final Voucher voucher1 = new FixedAmountVoucher(1L, 2000);
                final Voucher voucher2 = new PercentDiscountVoucher(2L, 10);
                final List<Voucher> vouchers = new ArrayList<>(Arrays.asList(voucher1, voucher2));
                when(voucherService.findAllVoucher()).thenReturn(vouchers);

                final var request = MockMvcRequestBuilders.get(url);

                final var resultActions = mockMvc.perform(request);
                final var content = resultActions.andReturn().getResponse().getContentAsString();

                String expected = MessageFormat.format("[{0},{1}]",
                        objectMapper.writeValueAsString(voucher1),
                        objectMapper.writeValueAsString(voucher2));

                assertThat(expected, is(content));
            }
        }

        @Nested
        @DisplayName("시작날짜가 끝날짜보다 늦으면")
        class Context_with_invalidPeriod {

            @Test
            @DisplayName("BadRequest 를 반환한다")
            void it_return_list() throws Exception {
                final var fromDate = LocalDate.of(2022, 5, 1);
                final var toDate = LocalDate.of(2022, 4, 29);
                final var url = MessageFormat.format("/api/v1/vouchers?fromDate={0}&toDate={1}", fromDate, toDate);

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효한 날짜가 입력되면")
        class Context_with_valid_period {

            @Test
            @DisplayName("해당 기간의 모든 바우처를 리턴한다")
            void it_return_list() throws Exception {
                final var fromDate = LocalDate.of(2022, 5, 1);
                final var toDate = LocalDate.of(2022, 5, 11);
                final var url = MessageFormat.format("/api/v1/vouchers?fromDate={0}&toDate={1}", fromDate, toDate);

                final var createdTime1 = LocalDateTime.of(LocalDate.of(2022, 5, 1), LocalTime.now());
                final var createdTime2 = LocalDateTime.of(LocalDate.of(2022, 4, 29), LocalTime.now());

                final var voucher1 = new FixedAmountVoucher(0L, 1000, createdTime1, createdTime1);
                final var voucher2 = new FixedAmountVoucher(0L, 1000, createdTime2, createdTime2);

                final List<Voucher> vouchers = new ArrayList<>();
                vouchers.add(voucher1);
                vouchers.add(voucher2);

                when(voucherService.findAllVoucher()).thenReturn(vouchers);

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());
                final var resultContent = resultActions.andReturn().getResponse().getContentAsString();
                final var expectedContent = MessageFormat.format("[{0}]", objectMapper.writeValueAsString(voucher1));

                assertThat(resultContent, is(expectedContent));
            }
        }

        @Nested
        @DisplayName("알수없는 타입이 입력되면")
        class Context_with_invalid_type {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void it_return_list() throws Exception {
                final var url = "/api/v1/vouchers?voucherType=Hello";

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효한 타입이 입력되면")
        class Context_with_valid_type {

            @Test
            @DisplayName("해당 타입의 모든 바우처를 리턴한다")
            void it_return_list() throws Exception {
                final var url = "/api/v1/vouchers?voucherType=FIXED_AMOUNT";

                final var voucher1 = new FixedAmountVoucher(0L, 1000);
                final var voucher2 = new PercentDiscountVoucher(0L, 10);

                final List<Voucher> vouchers = new ArrayList<>();
                vouchers.add(voucher1);
                vouchers.add(voucher2);

                when(voucherService.findAllVoucher()).thenReturn(vouchers);

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());
                final var resultContent = resultActions.andReturn().getResponse().getContentAsString();
                final var expectedContent = MessageFormat.format("[{0}]", objectMapper.writeValueAsString(voucher1));

                assertThat(resultContent, is(expectedContent));
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("존재하는 id 값이 조회되면")
        class Context_with_existing {

            @Test
            @DisplayName("해당 id 값의 바우처 정보를 반환한다")
            void it_return_voucher_info() throws Exception {

                final var voucher = new FixedAmountVoucher(1L, 1000, LocalDateTime.now(), LocalDateTime.now());
                when(voucherService.findById(anyLong())).thenReturn(voucher);

                final var url = "/api/v1/vouchers/1";
                final var request = get(url);
                final var resultActions = mockMvc.perform(request);

                resultActions.andExpect(status().isOk());
                final var resultContent = resultActions.andReturn().getResponse().getContentAsString();
                final var expectedContent = objectMapper.writeValueAsString(voucher);

                assertThat(resultContent, is(expectedContent));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id 값이 조회되면")
        class Context_with_not_existing {

            @Test
            @DisplayName("not found 를 반환한다")
            void it_return_voucher_info() throws Exception {
                when(voucherService.findById(anyLong())).thenThrow(new ResourceNotFound());

                final var url = "/api/v1/vouchers/1";

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);

                resultActions.andExpect(status().isNotFound());

            }
        }
    }
}
