package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.validator.OrderValidator;
import com.prgmrs.voucher.model.validator.UserValidator;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.repository.UserRepository;
import com.prgmrs.voucher.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;

    @Mock
    private UserValidator userValidator;

    @Mock
    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignVoucher() {
        // Given
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);

        Wallet wallet1 = new Wallet(userUuid, voucherUuid1);

        WalletRequest walletRequest = new WalletRequest(user.username(), "1", voucherList);
        given(userValidator.isValidNameFormat(user.username())).willReturn(true);
        given(orderValidator.isValidOrder("1", voucherList)).willReturn(true);
        given(userRepository.findByUsername(user.username())).willReturn(user);

        // When
        WalletResponse walletResponse = walletService.assignVoucher(walletRequest);

        // Then
        Wallet retrievedWallet = walletResponse.wallet();
        assertThat(retrievedWallet.voucherId()).isEqualTo(voucher1.voucherId());
        assertThat(retrievedWallet.userId()).isEqualTo(user.userId());
        verify(walletRepository, times(1)).save(wallet1);
    }

    @Test
    void freeVoucher() {
        // Given
        UUID userUuid = UUID.randomUUID();
        String username = "tyler";
        User user = new User(userUuid, username);

        UUID voucherUuid1 = UUID.randomUUID();
        Amount amount1 = new Amount(300);
        FixedAmountDiscountStrategy discountStrategy1 = new FixedAmountDiscountStrategy(amount1);
        Voucher voucher1 = new Voucher(voucherUuid1, discountStrategy1);

        UUID voucherUuid2 = UUID.randomUUID();
        Amount amount2 = new Amount(200);
        FixedAmountDiscountStrategy discountStrategy2 = new FixedAmountDiscountStrategy(amount2);
        Voucher voucher2 = new Voucher(voucherUuid2, discountStrategy2);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher1);
        voucherList.add(voucher2);

        Wallet wallet1 = new Wallet(userUuid, voucherUuid1);

        WalletRequest walletRequest = new WalletRequest(user.username(), "1", voucherList);
        given(userValidator.isValidNameFormat(user.username())).willReturn(true);
        given(orderValidator.isValidOrder("1", voucherList)).willReturn(true);
        given(userRepository.findByUsername(user.username())).willReturn(user);

        // When
        WalletResponse walletResponse = walletService.freeVoucher(walletRequest);

        // Then
        Wallet retrievedWallet = walletResponse.wallet();
        assertThat(retrievedWallet.voucherId()).isEqualTo(voucher1.voucherId());
        assertThat(retrievedWallet.userId()).isEqualTo(user.userId());
        verify(walletRepository, times(1)).free(wallet1);
    }
}