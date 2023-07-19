package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.controller.console.VoucherController;
import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.dto.request.UsernameRequest;
import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.Percent;
import com.prgmrs.voucher.service.VoucherService;
import com.prgmrs.voucher.util.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("바우처 컨트롤러 레이어를 테스트한다.")
class VoucherJsonControllerTest {

    @InjectMocks
    VoucherController voucherController;

    @Mock
    VoucherService voucherService;

    private VoucherRequest voucherRequest;
    private VoucherResponse voucherResponse;
    private VoucherListResponse voucherListResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(new Percent(10));
        UUID voucherUuid1 = UUIDGenerator.generateUUID();
        Voucher voucher1 = new Voucher(voucherUuid1, percentDiscountStrategy);
        voucherRequest = new VoucherRequest("percent", "10");
        voucherResponse = new VoucherResponse(voucherUuid1, percentDiscountStrategy);

        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(new Amount(300));
        UUID voucherUuid2 = UUIDGenerator.generateUUID();
        Voucher voucher2 = new Voucher(voucherUuid2, fixedAmountDiscountStrategy);
        voucherListResponse = new VoucherListResponse(Arrays.asList(voucher1, voucher2));
    }

    @Test
    @DisplayName("바우처를 생성한다.")
    void CreateVoucher_VoucherRequest_SameVoucherResponse() {
        // Given
        given(voucherService.createVoucher(voucherRequest)).willReturn(voucherResponse);

        // When
        ResponseDTO<?> responseDTO = voucherController.createVoucher(voucherRequest);

        // Then
        assertEquals(voucherResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("유저 이름이 해당하는 할당된 바우처 리스트를 받는다.")
    void GetAssignedVoucherListByUsername_StringUsername_SameVoucherListResponse() {
        // Given
        UsernameRequest usernameRequest = new UsernameRequest("tyler");
        given(voucherService.getAssignedVoucherListByUsername(usernameRequest)).willReturn(voucherListResponse);

        // When
        ResponseDTO<?> responseDTO = voucherController.getAssignedVoucherListByUsername(usernameRequest);

        // Then
        assertEquals(voucherListResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("할당되지 않은 바우처 리스트를 요청한다.")
    void GetNotAssignedVoucher_NoParam_SameVoucherListResponse() {
        // Given
        given(voucherService.getNotAssignedVoucher()).willReturn(voucherListResponse);

        // When
        ResponseDTO<?> responseDTO = voucherController.getNotAssignedVoucherList();

        // Then
        assertEquals(voucherListResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("할당 된 바우처 리스트를 요청한다.")
    void GetAssignedVoucherList_NoParam_SameVoucherListResponse() {
        // Given
        given(voucherService.getAssignedVoucherList()).willReturn(voucherListResponse);

        // When
        ResponseDTO<?> responseDTO = voucherController.getAssignedVoucherList();

        // Then
        assertEquals(voucherListResponse, responseDTO.getData());
    }
}