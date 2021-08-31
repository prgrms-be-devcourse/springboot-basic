package org.programmers.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    private final VoucherRepository voucherRepositoryMock = mock(VoucherRepository.class);
    private final VoucherFactory voucherFactoryMock = mock(VoucherFactory.class);
    private final VoucherService sut = new VoucherService(voucherRepositoryMock, voucherFactoryMock);

    @Test
    @DisplayName("바우처가 생성되어야 한다")
    void createVoucher() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        when(voucherFactoryMock.getVoucherType(VoucherType.FIXED, fixedAmountVoucher.getVoucherId(), 100L)).thenReturn(fixedAmountVoucher);

        // when
        Voucher voucher = sut.createVoucher(VoucherType.FIXED, fixedAmountVoucher.getVoucherId(), 100L);

        // then
        assertThat(voucher.getClass().getSimpleName(), is(FixedAmountVoucher.class.getSimpleName()));
        assertThat(voucher.getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(voucher.getValue(), is(fixedAmountVoucher.getValue()));

        verify(voucherFactoryMock).getVoucherType(VoucherType.FIXED, fixedAmountVoucher.getVoucherId(), 100L);
    }

    @Test
    @DisplayName("특정 id의 바우처를 불러온다")
    void getVoucher() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        when(voucherRepositoryMock.findById(fixedAmountVoucher.getVoucherId())).thenReturn(java.util.Optional.of(fixedAmountVoucher));

        // when
        Voucher voucher = sut.getVoucher(fixedAmountVoucher.getVoucherId());

        // then
        assertThat(voucher.getClass().getSimpleName(), is(FixedAmountVoucher.class.getSimpleName()));
        assertThat(voucher.getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(voucher.getValue(), is(fixedAmountVoucher.getValue()));

        verify(voucherRepositoryMock).findById(fixedAmountVoucher.getVoucherId());
    }

}