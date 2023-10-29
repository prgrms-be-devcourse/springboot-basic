package com.programmers.springbootbasic.domain.wallet.controller;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.domain.customer.exception.ErrorMsg;
import com.programmers.springbootbasic.domain.wallet.dto.WalletServiceRequestDto;
import com.programmers.springbootbasic.domain.wallet.entity.Wallet;
import com.programmers.springbootbasic.domain.wallet.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Wallet Controller Test")
class WalletControllerTest {
    private static final String VOUCHER_ID_STR = UUID.randomUUID().toString();
    private static final String EMAIL = "test@gmail.com";
    private static final String WRONG_EMAIL = "test@";
    @InjectMocks
    private WalletController walletController;
    @Mock
    private WalletService walletService;

    @Test
    void testAddWalletSuccess() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(UUID.fromString(VOUCHER_ID_STR))
                .build();
        when(walletService.createWallet(any(WalletServiceRequestDto.class))).thenReturn(expectedWallet);
        // Act
        CommonResult<String> actualResult = walletController.addWallet(EMAIL, VOUCHER_ID_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test addWallet Fail: Email verify Fail")
    @Test
    void testAddWalletFailWhenEmailVerifyFail() {
        // Act
        CommonResult<String> actualResult = walletController.addWallet(WRONG_EMAIL, VOUCHER_ID_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

    @DisplayName("Test addWallet Fail: UUID Format Mismatch")
    @Test
    void testAddWalletFailWhenUUIDFormatMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = walletController.addWallet(EMAIL, expectedVoucherId);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
    }

    @Test
    void testFindWalletsByCustomerEmailSuccess() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(UUID.fromString(VOUCHER_ID_STR))
                .build();
        List<Wallet> expectedWallets = List.of(expectedWallet);
        String expectedMessage = String.format("""
                --- %s user Vouchers ---
                %s
                """, EMAIL, VOUCHER_ID_STR);
        when(walletService.findWalletsByCustomerEmail(any(WalletServiceRequestDto.class))).thenReturn(expectedWallets);
        // Act
        CommonResult<String> actualResult = walletController.findWalletsByCustomerEmail(EMAIL);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(expectedMessage);
    }

    @DisplayName("Test findWalletsByCustomerEmail Fail: Email verify Fail")
    @Test
    void testFindWalletsByCustomerEmailWhenEmailVerifyFail() {
        // Act
        CommonResult<String> actualResult = walletController.findWalletsByCustomerEmail(WRONG_EMAIL);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

    @Test
    void testFindWalletsByVoucherIdSuccess() {
        // Arrange
        Wallet expectedWallet = Wallet.builder()
                .email(EMAIL)
                .voucherId(UUID.fromString(VOUCHER_ID_STR))
                .build();
        List<Wallet> expectedWallets = List.of(expectedWallet);
        String expectedMessage = String.format("""
                --- Contain %s Voucher User list ---
                %s
                """, VOUCHER_ID_STR, EMAIL);
        when(walletService.findWalletsByVoucherId(any(WalletServiceRequestDto.class))).thenReturn(expectedWallets);
        // Act
        CommonResult<String> actualResult = walletController.findWalletsByVoucherId(VOUCHER_ID_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(expectedMessage);
    }

    @DisplayName("Test findWalletsByVoucherId Fail: UUID Format Mismatch")
    @Test
    void testFindWalletsByVoucherIdFailWhenUUIDFormatMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = walletController.findWalletsByVoucherId(expectedVoucherId);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
    }

    @Test
    void testDeleteWalletSuccess() {
        // Arrange
        doNothing().when(walletService).deleteWallet(any(WalletServiceRequestDto.class));
        // Act
        CommonResult<String> actualResult = walletController.deleteWallet(EMAIL, VOUCHER_ID_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isTrue();
        assertThat(actualResult.getData()).isEqualTo(CommonResult.getSuccessResult().getData());
    }

    @DisplayName("Test deleteWallet Fail: Email verify Fail")
    @Test
    void testDeleteWalletFailWhenEmailVerifyFail() {
        // Act
        CommonResult<String> actualResult = walletController.deleteWallet(WRONG_EMAIL, VOUCHER_ID_STR);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(ErrorMsg.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

    @DisplayName("Test deleteWallet Fail: UUID Format Mismatch")
    @Test
    void testDeleteWalletFailWhenUUIDFormatMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult<String> actualResult = walletController.deleteWallet(EMAIL, expectedVoucherId);
        // Assert
        assertThat(actualResult.isSuccess()).isFalse();
        assertThat(actualResult.getData()).isEqualTo(com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.UUID_FORMAT_MISMATCH.getMessage());
    }
}