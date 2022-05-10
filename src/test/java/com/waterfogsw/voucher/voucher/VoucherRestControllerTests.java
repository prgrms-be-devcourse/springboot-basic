package com.waterfogsw.voucher.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waterfogsw.voucher.voucher.controller.VoucherControllerAdvice;
import com.waterfogsw.voucher.voucher.controller.VoucherRestController;
import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFoundException;
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
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("findAllVoucher 메서드는")
    class Describe_voucherList {

        final String url = "/api/v1/vouchers";

        @Nested
        @DisplayName("인자 없이 요청되면")
        class Context_with_call {

            @Test
            @DisplayName("voucherService 의 findAllVoucher 메서드를 호출한다")
            void it_return_list() throws Exception {
                final var request = MockMvcRequestBuilders.get(url);

                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());
                verify(voucherService).findAllVoucher();
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
        @DisplayName("날짜만 입력되면")
        class Context_with_valid_period {

            @Test
            @DisplayName("해당 기간의 모든 바우처를 리턴한다")
            void it_return_list() throws Exception {
                final var fromDate = LocalDate.of(2022, 2, 22);
                final var toDate = LocalDate.of(2022, 3, 22);

                final var date = MessageFormat.format("?fromDate={0}&toDate={1}", fromDate, toDate);

                final var request = get(url + date);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());
                verify(voucherService).findByDuration(any());
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
        @DisplayName("타입만 입력되면")
        class Context_with_valid_type {

            @Test
            @DisplayName("")
            void it_return_list() throws Exception {
                final var url = "/api/v1/vouchers?voucherType=FIXED_AMOUNT";

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);
                resultActions.andExpect(status().isOk());
                verify(voucherService).findByType(any());
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
                when(voucherService.findVoucherById(anyLong())).thenReturn(voucher);

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
                when(voucherService.findVoucherById(anyLong())).thenThrow(new ResourceNotFoundException());

                final var url = "/api/v1/vouchers/1";

                final var request = get(url);
                final var resultActions = mockMvc.perform(request);

                resultActions.andExpect(status().isNotFound());

            }
        }
    }

    @Nested
    @DisplayName("deleteVoucherById 메서드는")
    class Describe_deleteVoucherById {

        @Nested
        @DisplayName("존재하는 id 값이 조회되면")
        class Context_with_existing {

            @Test
            @DisplayName("해당 id 값의 바우처 정보를 반환한다")
            void it_return_voucher_info() throws Exception {
                final var url = "/api/v1/vouchers/1";
                final var request = delete(url);
                final var resultActions = mockMvc.perform(request);

                resultActions.andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id 값이 조회되면")
        class Context_with_not_existing {

            @Test
            @DisplayName("not found 를 반환한다")
            void it_return_voucher_info() throws Exception {
                doThrow(ResourceNotFoundException.class).when(voucherService).deleteVoucherById(anyLong());

                final var url = "/api/v1/vouchers/1";

                final var request = delete(url);
                final var resultActions = mockMvc.perform(request);

                resultActions.andExpect(status().isNotFound());

            }
        }
    }
}
