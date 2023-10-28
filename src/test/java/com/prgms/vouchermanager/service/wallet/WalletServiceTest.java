package com.prgms.vouchermanager.service.wallet;

import com.prgms.vouchermanager.domain.wallet.Wallet;
import com.prgms.vouchermanager.dto.CreateWalletDto;
import com.prgms.vouchermanager.repository.wallet.WalletRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    WalletService walletService;
    @Mock
    private WalletRepository walletRepository;

    @Test
    @DisplayName("service의 create()를 통해 repository의 save()를 정상 실행하고, 값을 받아올 수 있다.")
    void createWalletSuccess() {

        //given
        UUID voucherId = UUID.randomUUID();
        CreateWalletDto dto = new CreateWalletDto(1L, voucherId);
        when(walletRepository.save(any(Wallet.class))).thenReturn(new Wallet(1L, 1L, voucherId));

        //when
        Wallet wallet = walletService.save(dto);

        //then
        Assertions.assertThat(voucherId).isEqualTo(wallet.getVoucherId());

    }

    @Test
    @DisplayName("service의 create()를 통해 존재하지 않는 회원값,UUID쿠폰값을 입력하면 DataIntegrityViolationException예외를 터트린다. ")
    void createWalletFail() {
        //given
        CreateWalletDto dto = new CreateWalletDto(1L, UUID.randomUUID());
        when(walletRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        //when
        //then
        Assertions.assertThatThrownBy(() -> walletService.save(dto)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("service의 findByCustomerId()를 통해 repository의 findByCustomerId()가 정상적으로 실행된다.")
    void findByCustomerIdWalletSuccess() {
        //given
        Long customerId = 2L;
        List<Wallet> wallets = List.of(new Wallet(1L, customerId, UUID.randomUUID()));
        when(walletRepository.findByCustomerId(customerId)).thenReturn(wallets);

        //when
        List<Wallet> byCustomerIdWallets = walletService.findByCustomerId(customerId);

        //then
        Assertions.assertThat(wallets).isEqualTo(byCustomerIdWallets);
    }

    @Test
    @DisplayName("service의 findByCustomerId()실행시 존재하지 않는 customerId 입력시 DataIntegrityViolationException 예외를 터트린다.  ")
    void findByCustomerIdWalletFail() {

        //given
        Long customerId = 2L;

        //when
        when(walletRepository.findByCustomerId(customerId)).thenThrow(DataIntegrityViolationException.class);

        //then
        Assertions.assertThatThrownBy(() -> walletService.findByCustomerId(customerId)).isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    @DisplayName("service의 findByVoucherId()를 통해 repository의 findByVoucherId()가 정상적으로 실행된다.")
    void findByVoucherIdWalletSuccess() {

        //given
        UUID voucherId = UUID.randomUUID();
        Wallet wallet = new Wallet(1L, 2L, voucherId);
        when(walletRepository.findByVoucherId(voucherId)).thenReturn(Optional.of(wallet));

        //when
        Wallet byVoucherIdWallet = walletService.findByVoucherId(voucherId);

        //then
        Assertions.assertThat(wallet).isEqualTo(byVoucherIdWallet);
    }

    @Test
    @DisplayName("service의 findByVoucherId()에 존재하지 않는 voucherId입력시 DataIntegrityViolationException 예외가 발생한다.")
    void findByVoucherIdWalletFail() {

        //given
        UUID voucherId = UUID.randomUUID();

        //when
        when(walletRepository.findByVoucherId(voucherId)).thenThrow(DataIntegrityViolationException.class);

        //then
        Assertions.assertThatThrownBy(() -> walletService.findByVoucherId(voucherId)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("service의 deleteByCustomerId()를 통해 repository의 deleteByCustomerId()를 정상적으로 실행한다")
    void deleteByCustomerId() {

        //given
        Long customerId = 1L;

        //when
        walletService.deleteByCustomerId(customerId);

        //then
        verify(walletRepository, atLeastOnce()).deleteByCustomerId(customerId);

    }

}
