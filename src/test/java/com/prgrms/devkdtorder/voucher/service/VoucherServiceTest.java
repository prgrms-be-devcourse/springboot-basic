package com.prgrms.devkdtorder.voucher.service;

import com.prgrms.devkdtorder.voucher.domain.FixedAmountVoucher;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class VoucherServiceTest {


    @Test
    @DisplayName("voucherId에 해당하는 Voucher를 가져올 수 있어야 한다")
    void testGetVoucher() {

        //given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService sut = new VoucherService(voucherRepository);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.of(fixedAmountVoucher));

        //when
        Voucher voucher = sut.getVoucher(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(voucher).isEqualTo(fixedAmountVoucher);
        assertThat(voucher).isExactlyInstanceOf(FixedAmountVoucher.class);
        verify(voucherRepository).findById(voucher.getVoucherId());
    }

    @Test
    @DisplayName("voucherId에 해당하는 Voucher가 없으면 예외가 발생해야 한다")
    void testGetVoucherException() {

        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService sut = new VoucherService(voucherRepository);
        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> sut.getVoucher(fixedAmountVoucher.getVoucherId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(fixedAmountVoucher.getVoucherId().toString());
        verify(voucherRepository).findById(fixedAmountVoucher.getVoucherId());

    }

}
