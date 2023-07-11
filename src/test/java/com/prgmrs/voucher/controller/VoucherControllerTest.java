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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

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
    void testCreateVoucher() {
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
    void testGetAssignedVoucherListByUsername() {
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
    void testGetNotAssignedVoucher() {
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
        VoucherListResponse response = voucherController.getNotAssignedVoucher();

        // Then
        assertEquals(voucherListResponse, response);
    }

    @Test
    void testgetAssignedVoucherList() {
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