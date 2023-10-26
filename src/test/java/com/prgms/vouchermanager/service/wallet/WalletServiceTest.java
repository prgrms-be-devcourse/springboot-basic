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

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    WalletService walletService;


    @Test
    @DisplayName("service의 create()를 통해 repository의 save()를 정상 실행하고, 값을 받아올 수 있다.")
    void createWalletSuccess() {

        //given
        UUID voucherId = UUID.randomUUID();
        CreateWalletDto dto = new CreateWalletDto(1L, voucherId);
        when(walletRepository.save(any(Wallet.class))).thenReturn(new Wallet(1L,1L,voucherId));

        //when
        Wallet wallet = walletService.save(dto);

        //then
        Assertions.assertThat(voucherId).isEqualTo(wallet.getVoucher_id());
        verify(walletRepository,atLeastOnce()).save(any(Wallet.class));

    }

    @Test
    @DisplayName("service의 create()를 통해 존재하지 않는 회원값,UUID쿠폰값을 입력하면 예외를 터트린다. ")
    void createWalletFail() {
        //given
        CreateWalletDto dto = new CreateWalletDto(1L, UUID.randomUUID());
        when(walletRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        //when
        //then
        Assertions.assertThatThrownBy(() -> walletService.save(dto)).isInstanceOf(DataIntegrityViolationException.class);
        verify(walletRepository, atLeastOnce()).save(any(Wallet.class));

    }

    @Test
    @DisplayName("service의 findByCustomerId()를 통해 repository의 findByCustomerId()가 정상적으로 실행된다.")
    void walletfindByCustomerId() {
        //given
        Long customerId = 1L;
        when(walletRepository.findByCustomerId(1L)).thenReturn(any());

        //when
        walletService.findByCustomerId(customerId);

        //then
        verify(walletRepository, atLeastOnce()).findByCustomerId(customerId);
    }

    @Test
    @DisplayName("service의 findByVoucherId()를 통해 repository의 findByVoucherId()가 정상적으로 실행된다.")
    void walletFindByVoucherIdSuccess() {

        //given
        UUID voucherId = UUID.randomUUID();
        Wallet wallet = new Wallet(1L, 2L, voucherId);
        when(walletRepository.findByVoucherId(voucherId)).thenReturn(Optional.of(wallet));

        //when
        walletService.findByVoucherId(voucherId);

        //then
        verify(walletRepository, atLeastOnce()).findByVoucherId(voucherId);

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
