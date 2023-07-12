package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.VoucherRequest;
import com.prgmrs.voucher.dto.response.VoucherListResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.enums.VoucherSelectionType;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.Percent;
import com.prgmrs.voucher.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("바우처 컨트롤러 레이어를 테스트한다.")

class VoucherControllerTest {

    @InjectMocks
    VoucherController voucherController;

    @Mock
    VoucherService voucherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("바우처를 생성한다.")
    void CreateVoucher_VoucherRequest_SameVoucherResponse() {
        // Given
        Percent percent = new Percent(10);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID uuid = UUID.randomUUID();
        Voucher voucher = new Voucher(uuid, percentDiscountStrategy);
        VoucherRequest voucherRequest = new VoucherRequest(VoucherSelectionType.FIXED_AMOUNT_VOUCHER, "10");
        VoucherResponse voucherResponse = new VoucherResponse(voucher);
        given(voucherService.createVoucher(voucherRequest)).willReturn(voucherResponse);

        // When
        VoucherResponse response = voucherController.createVoucher(voucherRequest);

        // Then
        assertEquals(voucherResponse, response);
    }

    @Test
    @DisplayName("유저 이름이 해당하는 할당된 바우처 리스트를 받는다.")
    void GetAssignedVoucherListByUsername_StringUsername_SameVoucherListResponse() {
        // Given
        Percent percent = new Percent(20);
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Voucher voucher1 = new Voucher(uuid1, fixedAmountDiscountStrategy);
        Voucher voucher2 = new Voucher(uuid2, percentDiscountStrategy);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);
        VoucherListResponse voucherListResponse = new VoucherListResponse(voucherList);
        given(voucherService.getAssignedVoucherListByUsername("tyler")).willReturn(voucherListResponse);

        // When
        VoucherListResponse response = voucherController.getAssignedVoucherListByUsername("tyler");

        // Then
        assertEquals(voucherListResponse, response);
    }

    @Test
    @DisplayName("할당되지 않은 바우처 리스트를 요청한다.")
    void GetNotAssignedVoucher_NoParam_SameVoucherListResponse() {
        // Given
        Percent percent = new Percent(20);
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Voucher voucher1 = new Voucher(uuid1, fixedAmountDiscountStrategy);
        Voucher voucher2 = new Voucher(uuid2, percentDiscountStrategy);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);
        VoucherListResponse voucherListResponse = new VoucherListResponse(voucherList);
        given(voucherService.getNotAssignedVoucher()).willReturn(voucherListResponse);

        // When
        VoucherListResponse response = voucherController.getNotAssignedVoucherList();

        // Then
        assertEquals(voucherListResponse, response);
    }

    @Test
    @DisplayName("할당 된 바우처 리스트를 요청한다.")
    void GetAssignedVoucherList_NoParam_SameVoucherListResponse() {
        // Given
        Percent percent = new Percent(20);
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        Voucher voucher1 = new Voucher(uuid1, fixedAmountDiscountStrategy);
        Voucher voucher2 = new Voucher(uuid2, percentDiscountStrategy);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);
        VoucherListResponse voucherListResponse = new VoucherListResponse(voucherList);
        given(voucherService.getAssignedVoucherList()).willReturn(voucherListResponse);

        // When
        VoucherListResponse response = voucherController.getAssignedVoucherList();

        // Then
        assertEquals(voucherListResponse, response);
    }
}