package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.request.AssignVoucherRequest;
import com.prgmrs.voucher.dto.request.RemoveVoucherRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Wallet;
import com.prgmrs.voucher.model.wrapper.Username;
import com.prgmrs.voucher.repository.UserRepository;
import com.prgmrs.voucher.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("지갑 서비스 레이어를 테스트한다.")
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;

    private UUID voucherUUID;
    private User user;
    private Username username;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        voucherUUID = UUID.randomUUID();

        UUID userUUID = UUID.randomUUID();
        username = new Username("tyler");
        user = new User(userUUID, username);
    }

    @Test
    @DisplayName("지갑 할당을 테스트한다.")
    void AssignVoucher_WalletRequest_WalletResponseSameAsGivenWallet() {
        // Given
        AssignVoucherRequest assignVoucherRequest = new AssignVoucherRequest(username.value(), voucherUUID.toString());
        given(userRepository.findByUsername(username)).willReturn(user);

        // When
        WalletResponse walletResponse = walletService.assignVoucher(assignVoucherRequest);

        // Then
        ArgumentCaptor<Wallet> walletCaptor = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository, times(1)).save(walletCaptor.capture());
        Wallet capturedWallet = walletCaptor.getValue();

        assertThat(walletResponse).isNotNull();
        assertThat(capturedWallet.voucherId()).isEqualTo(voucherUUID);
        assertThat(capturedWallet.userId()).isEqualTo(user.userId());
    }

    @Test
    @DisplayName("지갑 할당 해지를 테스트한다.")
    void FreeVoucher_WalletRequest_WalletResponseSameAsGivenWallet() {
        // Given
        RemoveVoucherRequest removeVoucherRequest = new RemoveVoucherRequest(voucherUUID.toString());
        given(userRepository.getUserByVoucherId(voucherUUID)).willReturn(user);

        // When
        WalletResponse walletResponse = walletService.removeVoucher(removeVoucherRequest);

        // Then
        ArgumentCaptor<Wallet> walletCaptor = ArgumentCaptor.forClass(Wallet.class);
        verify(walletRepository, times(1)).remove(walletCaptor.capture());
        Wallet capturedWallet = walletCaptor.getValue();

        assertThat(walletResponse).isNotNull();
        assertThat(capturedWallet.voucherId()).isEqualTo(voucherUUID);
        assertThat(capturedWallet.userId()).isEqualTo(user.userId());
    }
}