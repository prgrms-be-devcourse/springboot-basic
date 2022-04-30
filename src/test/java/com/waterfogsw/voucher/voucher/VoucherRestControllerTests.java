package com.waterfogsw.voucher.voucher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.voucher.voucher.controller.VoucherRestController;
import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.PercentDiscountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
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

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoucherRestControllerTests {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private VoucherService voucherService;
    @InjectMocks
    private VoucherRestController voucherRestController;
    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(voucherRestController)
                .build();
    }

    @Nested
    @DisplayName("voucherAdd 메서드는")
    class Describe_voucherAdd {

        final String url = "/api/v1/vouchers";

        @Nested
        @DisplayName("바우처 타입이 헤더에 없으면")
        class Context_without_voucherType {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void it_response_BadRequest() {
                Map<String, String> postRequest = new HashMap<>();
                postRequest.put("type", "");
                postRequest.put("value", "1000");

                try {
                    final String content = objectMapper.writeValueAsString(postRequest);
                    final var request = post(url)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON);

                    final var resultActions = mockMvc.perform(request);
                    resultActions.andExpect(status().isBadRequest());
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        @Nested
        @DisplayName("바우처 값이 헤더에 없으면")
        class Context_without_value {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void it_response_BadRequest() {
                Map<String, String> postRequest = new HashMap<>();
                postRequest.put("type", "FIXED_AMOUNT");
                postRequest.put("value", "");

                try {
                    final String content = objectMapper.writeValueAsString(postRequest);
                    final var request = post(url)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON);

                    final var resultActions = mockMvc.perform(request);
                    resultActions.andExpect(status().isBadRequest());
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        @Nested
        @DisplayName("모든 값이 존재하면")
        class Context_with_all {

            @Test
            @DisplayName("200 을 응답한다")
            void it_response_BadRequest() {
                final Voucher voucher = new FixedAmountVoucher(1L, 2000);
                when(voucherService.saveVoucher(any(Voucher.class))).thenReturn(voucher);

                try {
                    final String content = objectMapper.writeValueAsString(voucher);
                    final var request = post(url)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON);

                    final var resultActions = mockMvc.perform(request);
                    resultActions.andExpect(status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
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
            void it_return_list() {
                final Voucher voucher1 = new FixedAmountVoucher(1L, 2000);
                final Voucher voucher2 = new PercentDiscountVoucher(2L, 10);
                final List<Voucher> vouchers = new ArrayList<>(Arrays.asList(voucher1, voucher2));
                when(voucherService.findAllVoucher()).thenReturn(vouchers);

                final var request = MockMvcRequestBuilders.get(url);

                try {
                    final var resultActions = mockMvc.perform(request);
                    final var content = resultActions.andReturn().getResponse().getContentAsString();
                    assertThat(
                            "[{\"id\":1,\"type\":\"FIXED_AMOUNT\",\"value\":2000}," +
                                    "{\"id\":2,\"type\":\"PERCENT_DISCOUNT\",\"value\":10}]",
                            is(content)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }
}
