package kdt.vouchermanagement;

import com.google.gson.Gson;
import kdt.vouchermanagement.domain.voucher.controller.ControllerAdvice;
import kdt.vouchermanagement.domain.voucher.controller.VoucherApiController;
import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VoucherApiControllerTest {

    @InjectMocks
    VoucherApiController voucherApiController;

    @Mock
    VoucherService voucherService;

    MockMvc mockMvc;
    Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(voucherApiController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @ParameterizedTest
    @MethodSource("invalidVoucherCreateParameter")
    @DisplayName("바우처 생성시 잘못된 파라미터로 인하여 BAD_REQUEST ResponseEntity를 반환_실패")
    void responseBAD_REQUEST(final VoucherType type, final int discountValue) throws Exception {
        //given
        String url = "/api/v1/vouchers";
        VoucherRequest request = new VoucherRequest(type, discountValue);
        when(voucherService.createVoucher(any())).thenThrow(new IllegalArgumentException());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("바우처 생성시 쿼리 실행 오류로 인하여 INTERNAL_SERVER_ERROR ResponseEntity를 반환_실패")
    void responseINTERNAL_SERVER_ERROR() throws Exception {
        //given
        String url = "/api/v1/vouchers";
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);
        when(voucherService.createVoucher(any())).thenThrow(new IllegalStateException());

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );

        //then
        resultActions.andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("바우처 생성 요청에 대한 ResponseEntity 반환_성공")
    void responseCreateVoucher() throws Exception {
        //given
        String url = "/api/v1/vouchers";
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);
        Voucher voucher = request.toDomain();

        lenient().doReturn(voucher).when(voucherService).createVoucher(voucher);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request))
        );

        //then
        verify(voucherService, times(1)).createVoucher(any(Voucher.class));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("바우처 목록 조회 요청에 대한 ResponseEntity 반환_성공")
    void responseFindVouchers() throws Exception {
        //given
        String url = "/api/v1/vouchers";
        Voucher voucher = new FixedAmountVoucher(1L, VoucherType.FIXED_AMOUNT, 100, LocalDateTime.now(), LocalDateTime.now());
        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(voucher);

        doReturn(vouchers).when(voucherService).findVouchers();

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

        //then
        verify(voucherService, times(1)).findVouchers();
        resultActions.andExpect(status().isOk());

        List<Voucher> responseVouchers = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), List.class);

        assertThat(responseVouchers.size()).isEqualTo(vouchers.size());
    }

    @Test
    @DisplayName("바우처 삭제시 바우처가 존재하지 않아서 BAD_REQUEST ResponseEntity를 반환_실패")
    void responseBadRequestWhenDeleteVoucher() {
        //given
        String url = "/api/v1/vouchers/1";
        doThrow(new IllegalArgumentException()).when(voucherService).deleteVoucher(any());

        try {
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

            //then
            resultActions.andExpect(status().isBadRequest());
        } catch (Exception e) {
            new RuntimeException();
        }
    }

    @Test
    @DisplayName("바우처 삭제 요청_성공")
    void requestDeleteVoucher() {
        //given
        String url = "/api/v1/vouchers/1";

        try {
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(url));

            //then
            verify(voucherService, times(1)).deleteVoucher(any());
            resultActions.andExpect(status().isNoContent());
        } catch (Exception e) {
            new RuntimeException();
        }
    }

    @Test
    @DisplayName("바우처 조회시 바우처가 존재하지 않아서 BAD_REQUEST ResponseEntity를 반환_실패")
    void responseBadRequestWhenFindVoucherById() {
        //given
        String url = "/api/v1/vouchers/1";
        doThrow(new IllegalArgumentException()).when(voucherService).findVoucher(any());

        try {
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

            //then
            resultActions.andExpect(status().isBadRequest());
        } catch (Exception e) {
            new RuntimeException();
        }
    }

    @Test
    @DisplayName("바우처 조회 요청_성공")
    void requestWhenFindVoucherById() {
        //given
        String url = "/api/v1/vouchers/1";

        try {
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

            //then
            verify(voucherService, times(1)).findVoucher(any());
            resultActions.andExpect(status().isOk());
        } catch (Exception e) {
            new RuntimeException();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"FIXED_AMOUNT", "PERCENT_DISCOUNT"})
    @DisplayName("바우처 할인타입별 조회 요청_성공")
    void requestWhenFindVoucherByType(String type) {
        //given
        String url = "/api/v1/vouchers/category?type=";

        try {
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url + type));

            //then
            verify(voucherService, times(1)).findVouchers();
            resultActions.andExpect(status().isOk());
        } catch (Exception e) {
            new RuntimeException();
        }
    }

    @Test
    @DisplayName("바우처 생성날짜별 조회 요청_성공")
    void requestWhenFindVoucherByDate() {
        //given
        String url = "/api/v1/vouchers/date?date=2022-05-01";

        try {
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url));

            //then
            verify(voucherService, times(1)).findVouchers();
            resultActions.andExpect(status().isOk());
        } catch (Exception e) {
            new RuntimeException();
        }
    }

    private static Stream<Arguments> invalidVoucherCreateParameter() {
        return Stream.of(
                Arguments.of(VoucherType.FIXED_AMOUNT, 0),
                Arguments.of(VoucherType.FIXED_AMOUNT, -1),
                Arguments.of(VoucherType.PERCENT_DISCOUNT, 0),
                Arguments.of(VoucherType.PERCENT_DISCOUNT, -1),
                Arguments.of(VoucherType.PERCENT_DISCOUNT, 1000)
        );
    }
}
