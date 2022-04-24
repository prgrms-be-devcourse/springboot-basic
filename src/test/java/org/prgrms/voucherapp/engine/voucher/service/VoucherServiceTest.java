package org.prgrms.voucherapp.engine.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.voucherapp.engine.voucher.entity.FixedAmountVoucher;
import org.prgrms.voucherapp.engine.voucher.entity.PercentDiscountVoucher;
import org.prgrms.voucherapp.engine.voucher.entity.Voucher;
import org.prgrms.voucherapp.engine.voucher.repository.VoucherRepository;
import org.prgrms.voucherapp.engine.wallet.repository.WalletRepository;
import org.prgrms.voucherapp.exception.NullVoucherException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("db")
class VoucherServiceTest {

    @InjectMocks
    VoucherService voucherService;

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    WalletRepository walletRepository;

    private final Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);
    private final Voucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

    @Test
    @DisplayName("존재하는 바우처를 조회한다.")
    void testGetExistVoucher() {
        //Given
        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.of(fixedAmountVoucher));

        //When
        Voucher voucher = voucherService.getVoucher(fixedAmountVoucher.getVoucherId());

        //Then
        assertThat(voucher, samePropertyValuesAs(fixedAmountVoucher));
        verify(voucherRepository, atLeast(1)).findById(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("존재하지 않는 바우처를 조회한다.")
    void testGetNonExistCustomer() {
        //Given
        Voucher unknown = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        //When
        assertThrows(NullVoucherException.class, () -> voucherService.getVoucher(unknown.getVoucherId()));
        verify(voucherRepository, atLeast(1)).findById(unknown.getVoucherId());
    }

    @Test
    @DisplayName("존재하는 바우처 제거")
    void removeCustomer() {
        //Given
        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.of(fixedAmountVoucher));
        //When
        voucherService.removeVoucher(fixedAmountVoucher.getVoucherId());
        //Then
        verify(voucherRepository).deleteById(fixedAmountVoucher.getVoucherId());
        verify(walletRepository).deleteByVoucherId(fixedAmountVoucher.getVoucherId());
    }


}