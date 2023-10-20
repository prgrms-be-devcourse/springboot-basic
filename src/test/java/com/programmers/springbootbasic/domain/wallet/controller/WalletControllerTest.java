package com.programmers.springbootbasic.domain.wallet.controller;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.response.service.ResponseFactory;
import com.programmers.springbootbasic.domain.wallet.dto.WalletRequestDto;
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

import static com.programmers.springbootbasic.domain.customer.exception.ErrorMsg.emailTypeNotMatch;
import static com.programmers.springbootbasic.domain.voucher.exception.ErrorMsg.UUIDFormatMismatch;
import static org.junit.jupiter.api.Assertions.*;
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
        when(walletService.createWallet(any(WalletRequestDto.class))).thenReturn(expectedWallet);
        // Act
        CommonResult actualResult = walletController.addWallet(EMAIL, VOUCHER_ID_STR);
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(ResponseFactory.getSuccessResult().getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test addWallet Fail: Email verify Fail")
    @Test
    void testAddWalletFailWhenEmailVerifyFail() {
        // Act
        CommonResult actualResult = walletController.addWallet(WRONG_EMAIL, VOUCHER_ID_STR);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(emailTypeNotMatch.getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test addWallet Fail: UUID Format Mismatch")
    @Test
    void testAddWalletFailWhenUUIDFormatMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult actualResult = walletController.addWallet(EMAIL, expectedVoucherId);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(UUIDFormatMismatch.getMessage(), actualResult.getMessage());
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
        when(walletService.findWalletsByCustomerEmail(any(WalletRequestDto.class))).thenReturn(expectedWallets);
        // Act
        CommonResult actualResult = walletController.findWalletsByCustomerEmail(EMAIL);
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(expectedMessage, actualResult.getMessage());
    }

    @DisplayName("Test findWalletsByCustomerEmail Fail: Email verify Fail")
    @Test
    void testFindWalletsByCustomerEmailWhenEmailVerifyFail() {
        // Act
        CommonResult actualResult = walletController.findWalletsByCustomerEmail(WRONG_EMAIL);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(emailTypeNotMatch.getMessage(), actualResult.getMessage());
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
        when(walletService.findWalletsByVoucherId(any(WalletRequestDto.class))).thenReturn(expectedWallets);
        // Act
        CommonResult actualResult = walletController.findWalletsByVoucherId(VOUCHER_ID_STR);
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(expectedMessage, actualResult.getMessage());
    }

    @DisplayName("Test findWalletsByVoucherId Fail: UUID Format Mismatch")
    @Test
    void testFindWalletsByVoucherIdFailWhenUUIDFormatMismatch() {
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult actualResult = walletController.findWalletsByVoucherId(expectedVoucherId);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(UUIDFormatMismatch.getMessage(), actualResult.getMessage());
    }

    @Test
    void testDeleteWalletSuccess() {
        // Arrange
        doNothing().when(walletService).deleteWallet(any(WalletRequestDto.class));
        // Act
        CommonResult actualResult = walletController.deleteWallet(EMAIL, VOUCHER_ID_STR);
        // Assert
        assertTrue(actualResult.isSuccess());
        assertEquals(ResponseFactory.getSuccessResult().getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test deleteWallet Fail: Email verify Fail")
    @Test
    void testDeleteWalletFailWhenEmailVerifyFail(){
        // Act
        CommonResult actualResult = walletController.deleteWallet(WRONG_EMAIL, VOUCHER_ID_STR);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(emailTypeNotMatch.getMessage(), actualResult.getMessage());
    }

    @DisplayName("Test deleteWallet Fail: UUID Format Mismatch")
    @Test
    void testDeleteWalletFailWhenUUIDFormatMismatch(){
        // Arrange
        String expectedVoucherId = "AAAA-AAAA-AAAA-AAAA";
        // Act
        CommonResult actualResult = walletController.deleteWallet(EMAIL, expectedVoucherId);
        // Assert
        assertFalse(actualResult.isSuccess());
        assertEquals(UUIDFormatMismatch.getMessage(), actualResult.getMessage());
    }
}