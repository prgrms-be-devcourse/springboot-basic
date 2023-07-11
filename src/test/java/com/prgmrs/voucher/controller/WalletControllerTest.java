package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.Percent;
import com.prgmrs.voucher.service.WalletService;
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

class WalletControllerTest {

    @InjectMocks
    WalletController walletController;

    @Mock
    WalletService walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignVoucher() {
        // Given
        Percent percent = new Percent(20);
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID voucherUuid1 = UUID.randomUUID();
        UUID voucherUuid2 = UUID.randomUUID();
        Voucher voucher1 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        Voucher voucher2 = new Voucher(voucherUuid2, percentDiscountStrategy);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        String order = "1";
        Wallet wallet = new Wallet(userUuid, voucherUuid1);
        WalletRequest walletRequest = new WalletRequest(username, order, voucherList);
        WalletResponse walletResponse = new WalletResponse(wallet, username);
        given(walletController.assignVoucher(walletRequest)).willReturn(walletResponse);

        // When
        WalletResponse response = walletController.assignVoucher(walletRequest);

        // Then
        assertEquals(walletResponse, response);
    }

    @Test
    void testFreeVoucher() {
        // Given
        Percent percent = new Percent(20);
        Amount amount = new Amount(500);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy(percent);
        UUID voucherUuid1 = UUID.randomUUID();
        UUID voucherUuid2 = UUID.randomUUID();
        Voucher voucher1 = new Voucher(voucherUuid1, fixedAmountDiscountStrategy);
        Voucher voucher2 = new Voucher(voucherUuid2, percentDiscountStrategy);
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        String order = "1";
        Wallet wallet = new Wallet(userUuid, voucherUuid1);
        WalletRequest walletRequest = new WalletRequest(username, order, voucherList);
        WalletResponse walletResponse = new WalletResponse(wallet, username);
        given(walletController.freeVoucher(walletRequest)).willReturn(walletResponse);

        // When
        WalletResponse response = walletController.freeVoucher(walletRequest);

        // Then
        assertEquals(walletResponse, response);
    }
}