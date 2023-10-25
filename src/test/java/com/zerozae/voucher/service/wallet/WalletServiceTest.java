package com.zerozae.voucher.service.wallet;

import com.zerozae.voucher.domain.wallet.Wallet;
import com.zerozae.voucher.dto.wallet.WalletRequest;
import com.zerozae.voucher.dto.wallet.WalletResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.repository.wallet.WalletRepository;
import com.zerozae.voucher.service.wallet.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    WalletService walletService;
    WalletRepository walletRepository;
    Wallet wallet;

    @BeforeEach
    void setUp() {
        walletRepository = Mockito.mock(WalletRepository.class);
        walletService = new WalletService(walletRepository);

        wallet = new Wallet(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    @DisplayName("지갑 생성 성공 테스트")
    void createWallet_Success_Test() {
        // Given
        WalletRequest walletRequest = new WalletRequest(wallet.getCustomerId(), wallet.getVoucherId());
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        // When
        WalletResponse walletResponse = walletService.createWallet(walletRequest);

        // Then
        assertEquals(walletRequest.getCustomerId(), walletResponse.getCustomerId());
        assertEquals(walletRequest.getCustomerId(), walletResponse.getCustomerId());
    }

    @Test
    @DisplayName("지갑 생성 실패 (지갑이 이미 존재) 테스트")
    void createWallet_AlreadyExist_Failed_Test() {
        // Given
        WalletRequest walletRequest = new WalletRequest(wallet.getCustomerId(), wallet.getVoucherId());
        when(walletRepository.findWallet(wallet.getCustomerId(), wallet.getVoucherId())).thenReturn(Optional.of(wallet));

        // When & Then
        assertThrows(ErrorMessage.class,() -> {
            walletService.createWallet(walletRequest);
        });
    }

    @Test
    @DisplayName("회원이 보유한 바우처를 찾기 위해 회원 아이디로 지갑 조회 성공 테스트")
    void findWalletByCustomerId_Success_Test() {
        // Given
        List.of(wallet);
        when(walletRepository.findByCustomerId(wallet.getCustomerId())).thenReturn(List.of(wallet));

        // When
        List<WalletResponse> wallets = walletService.findWalletByCustomerId(wallet.getCustomerId());

        // Then
        assertEquals(wallets.get(0).getCustomerId(), wallet.getCustomerId());
        assertEquals(wallets.get(0).getVoucherId(), wallet.getVoucherId());
    }

    @Test
    @DisplayName("바우처를 보유한 회원을 찾기 위해 바우처 아이디로 지갑 조회 성공 테스트")
    void findWalletByVoucherId_Success_test() {
        // Given
        List.of(wallet);
        when(walletRepository.findByVoucherId(wallet.getVoucherId())).thenReturn(List.of(wallet));

        // When
        List<WalletResponse> wallets = walletService.findWalletByVoucherId(wallet.getVoucherId());

        // Then
        assertEquals(wallets.get(0).getCustomerId(), wallet.getCustomerId());
        assertEquals(wallets.get(0).getVoucherId(), wallet.getVoucherId());
    }

    @Test
    @DisplayName("회원에게 할당한 바우처 회수 성공 테스트")
    void deleteWalletFromCustomer_Success_Test() {
        // Given
        when(walletRepository.findWallet(wallet.getCustomerId(), wallet.getVoucherId())).thenReturn(Optional.of(wallet));

        // When
        walletService.deleteWalletFromCustomer(wallet.getCustomerId(), wallet.getVoucherId());

        // Then
        verify(walletRepository, times(1)).findWallet(wallet.getCustomerId(), wallet.getVoucherId());
        verify(walletRepository, times(1)).deleteByAllId(wallet.getCustomerId(), wallet.getVoucherId());
    }

    @Test
    @DisplayName("회원에게 할당한 바우처 회수 실패 (존재하지 않는 아이디) 테스트")
    void deleteWalletFromCustomer_NotExist_Failed_Test() {
        // Given
        when(walletRepository.findWallet(wallet.getCustomerId(), wallet.getVoucherId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorMessage.class , () -> {
           walletService.deleteWalletFromCustomer(wallet.getCustomerId(), wallet.getVoucherId());
        });
    }

    @Test
    @DisplayName("지갑 전체 삭제 성공 테스트")
    void deleteAllWallet_Success_Test() {
        // Given

        // When
        walletService.deleteAllWallets();

        // Then
        verify(walletRepository, times(1)).deleteAll();
    }
}
