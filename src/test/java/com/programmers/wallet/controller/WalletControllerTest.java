package com.programmers.wallet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import com.programmers.customer.controller.CustomerController;
import com.programmers.io.Console;
import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.wallet.dto.WalletRequestDto;
import com.programmers.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class WalletControllerTest {

    @Mock
    private Console console;

    @Mock
    private VoucherController voucherController;

    @Mock
    private CustomerController customerController;

    @Mock
    private WalletService walletService;

    private WalletController walletController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        walletController = new WalletController(console, voucherController, customerController, walletService);
    }

    @DisplayName("고객이 어떤 바우처를 보유하고 있는지 조회한다")
    @Test
    void searchCustomerToGetVouchers() {
        //given
        String customerId = UUID.randomUUID().toString();
        when(console.readInput()).thenReturn(customerId);

        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), "voucher1", 10L));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), "voucher2", 20L));

        VouchersResponseDto vouchersResponseDto = new VouchersResponseDto(vouchers);
        when(walletService.findByCustomerId(UUID.fromString(customerId))).thenReturn(vouchersResponseDto);
        when(voucherController.getVouchersResult(vouchersResponseDto.vouchers())).thenReturn(vouchers);

        //when
        List<Voucher> result = walletController.searchCustomerToGetVouchers();

        //then
        assertThat(result.size(), is(vouchers.size()));
    }

    @DisplayName("회원이 보유한 한 개의 바우처를 제거한다")
    @Test
    void deleteOneVoucher() {
        //given
        UUID customerId = UUID.randomUUID();
        when(console.readInput()).thenReturn(customerId.toString());
        UUID voucherId = UUID.randomUUID();
        when(console.readInput()).thenReturn(voucherId.toString());

        //when
        walletController.deleteOneVoucher(customerId);
        WalletRequestDto walletRequestDto = new WalletRequestDto(voucherId, customerId);

        //then
        verify(console, times(1)).printDeleteVoucherIdMessage();
        verify(console, times(1)).readInput();
        verify(walletService, times(1)).deleteByVoucherIdAndCustomerId(walletRequestDto);
        verify(console, times(1)).printDeleteVoucherCompleteMessage();
    }

    @DisplayName("회원이 보유한 모든 바우처를 제거한다")
    @Test
    void deleteAllVouchers() {
        //given
        UUID customerId = UUID.randomUUID();
        when(console.readInput()).thenReturn(customerId.toString());

        //when
        walletController.deleteAllVouchers(customerId);

        //then
        verify(walletService, times(1)).deleteAllByCustomerId(customerId);
        verify(console, times(1)).printDeleteAllVouchersCompleteMessage();
    }
}