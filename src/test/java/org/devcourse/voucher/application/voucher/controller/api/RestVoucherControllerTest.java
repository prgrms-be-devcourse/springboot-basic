package org.devcourse.voucher.application.voucher.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.controller.dto.VoucherResponse;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.devcourse.voucher.core.exception.ControllerExceptionHandler;
import org.devcourse.voucher.core.exception.NotFoundException;
import org.devcourse.voucher.core.exception.stub.VoucherStubs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.devcourse.voucher.core.exception.ErrorType.NOT_FOUND_VOUCHER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RestVoucherControllerTest {

    @InjectMocks
    private RestVoucherController voucherController;

    @Mock
    private VoucherService voucherService;

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String URL = "/api/v1/voucher";

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(voucherController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("바우처 생성 테스트")
    void postCreateVoucherTest() throws Exception {
        //given
        VoucherRequest request = VoucherStubs.voucherRequest();
        VoucherResponse response = VoucherStubs.voucherResponse(UUID.randomUUID());

        // when
        doReturn(response)
                .when(voucherService)
                .createVoucher(any(VoucherType.class), anyLong());
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(CREATED.value()))
                .andExpect(jsonPath("$.data.voucherId").value(response.getVoucherId().toString()))
                .andExpect(jsonPath("$.data.voucherType").value(response.getVoucherType().toString()))
                .andExpect(jsonPath("$.data.discount").value(response.getDiscount()));
    }

    @Test
    @DisplayName("바우처 목록 가져오기 테스트")
    void getVoucherListTest() throws Exception {
        // given
        List<VoucherResponse> response = VoucherStubs.voucherResponseList();

        // when
        doReturn(response)
                .when(voucherService)
                .recallAllVoucher(any(Pageable.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(URL)
                        .param("page", "0")
                        .param("size", "5")
        );
        // then
        // TODO 리스트로 값을 받을때 반복해서 확인하는 방법이 없을까?
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].voucherId").value(response.get(0).getVoucherId().toString()))
                .andExpect(jsonPath("$.data.[1].voucherId").value(response.get(1).getVoucherId().toString()))
                .andExpect(jsonPath("$.data.[2].voucherId").value(response.get(2).getVoucherId().toString()))
                .andExpect(jsonPath("$.data.[3].voucherId").value(response.get(3).getVoucherId().toString()))
                .andExpect(jsonPath("$.data.[4].voucherId").value(response.get(4).getVoucherId().toString()));
    }

    @Test
    @DisplayName("바우처 ID를 통해서 바우처 가져오기 테스트")
    void getVoucherByIdTest() throws Exception {
        // given
        UUID request = UUID.randomUUID();
        VoucherResponse response = VoucherStubs.voucherResponse(request);

        // when
        doReturn(response)
                .when(voucherService)
                .recallVoucherById(any(UUID.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(URL + "/" + request)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.voucherId").value(response.getVoucherId().toString()))
                .andExpect(jsonPath("$.data.voucherType").value(response.getVoucherType().toString()))
                .andExpect(jsonPath("$.data.discount").value(response.getDiscount()));
    }

    @Test
    @DisplayName("존재하지 않는 바우처를 가져올 경우 예외 발생 테스트")
    void notValidGetVoucherByIdTest() throws Exception {
        // given
        UUID request = UUID.randomUUID();
        NotFoundException notFoundException = new NotFoundException(NOT_FOUND_VOUCHER, request);
        // when
        doThrow(notFoundException)
                .when(voucherService)
                .recallVoucherById(any(UUID.class));
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(URL + "/" + request)
        );
        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").value(notFoundException.getMessage()));
    }

    @Test
    @DisplayName("바우처 업데이트 테스트")
    void patchUpdateVoucherTest() throws Exception {
        // given
        UUID requestId = UUID.randomUUID();
        VoucherRequest request = VoucherStubs.voucherRequest();
        VoucherResponse response = VoucherStubs.voucherResponse(requestId);

        // when
        doReturn(response)
                .when(voucherService)
                .updateVoucher(any(UUID.class), anyLong());
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(URL + "/" + requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(OK.value()))
                .andExpect(jsonPath("$.data.voucherId").value(response.getVoucherId().toString()))
                .andExpect(jsonPath("$.data.voucherType").value(response.getVoucherType().toString()))
                .andExpect(jsonPath("$.data.discount").value(response.getDiscount()));
    }

    @Test
    @DisplayName("존재하지 않는 바우처를 테스트할 경우 예외 발생 테스트")
    void notValidPatchUpdateVoucherTest() throws Exception {
        // given
        UUID requestId = UUID.randomUUID();
        VoucherRequest request = VoucherStubs.voucherRequest();
        NotFoundException notFoundException = new NotFoundException(NOT_FOUND_VOUCHER, requestId);

        // when
        doThrow(notFoundException)
                .when(voucherService)
                .updateVoucher(any(UUID.class), anyLong());
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(URL + "/" + requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").value(notFoundException.getMessage()));
    }
}