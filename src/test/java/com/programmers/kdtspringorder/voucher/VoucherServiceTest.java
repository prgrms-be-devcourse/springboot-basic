package com.programmers.kdtspringorder.voucher;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.factory.VoucherFactory;
import com.programmers.kdtspringorder.voucher.repository.VoucherRepository;
import com.programmers.kdtspringorder.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {


    @Test
    @DisplayName("바우처 ID를 입력하면 일치하는 바우처를 리턴한다")
    void getVoucher() {
        // Given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherFactory voucherFactory = mock(VoucherFactory.class);

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        when(voucherRepository.findById(fixedAmountVoucher.getVoucherId())).thenReturn(Optional.of(fixedAmountVoucher));

        VoucherService voucherService = new VoucherService(voucherRepository, voucherFactory);

        // When
        Voucher voucher = voucherService.findByID(fixedAmountVoucher.getVoucherId());

        // Then
        assertThat(voucher).isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("없는 바우처 ID를 입력하면 RuntimeException을 던진다")
    public void getVoucherWithRuntimeException() {
        // Given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherFactory voucherFactory = mock(VoucherFactory.class);

        when(voucherRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());

        VoucherService voucherService = new VoucherService(voucherRepository, voucherFactory);

        // When Then
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> voucherService.findByID(UUID.randomUUID())
        );
    }

    @Test
    @DisplayName("바우처를 생성한다")
    void createVoucher() {
        // Given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherFactory voucherFactory = mock(VoucherFactory.class);

        VoucherType voucherType = VoucherType.FIXED;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        when(voucherFactory.createVoucher(voucherType, 10L)).thenReturn(fixedAmountVoucher);

        VoucherService voucherService = new VoucherService(voucherRepository, voucherFactory);

        // When
        Voucher actual = voucherService.createVoucher(VoucherType.FIXED, 2000L);

        // Then
        assertThat(actual).isEqualTo(fixedAmountVoucher);
    }
}