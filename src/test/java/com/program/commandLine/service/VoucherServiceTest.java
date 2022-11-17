package com.program.commandLine.service;

import com.program.commandLine.repository.MemoryVoucherRepository;
import com.program.commandLine.repository.VoucherRepository;
import com.program.commandLine.voucher.FixedAmountVoucher;
import com.program.commandLine.voucher.VoucherFactory;
import com.program.commandLine.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VoucherServiceTest {

    private final String NUMBER_FIXED_TYPE = "1";
    private final String NUMBER_PERCENT_TYPE = "2";

    @DisplayName("fixed amount voucher가 생성된다. - mock")
    @Test
    void testCreateFixedAmountVoucher() {
        // Given
        var voucherRepositoryMock = mock(VoucherRepository.class);
        var voucherFactoryMock = mock(VoucherFactory.class);
        UUID voucherId = UUID.randomUUID();
        var sut = new VoucherService(voucherRepositoryMock, voucherFactoryMock);

        // When
        var voucher = sut.createVoucher(NUMBER_FIXED_TYPE, voucherId, 20);

        // Then
        verify(voucherFactoryMock).createVoucher(VoucherType.FIXED_AMOUNT_DISCOUNT, voucherId, 20);
        verify(voucherRepositoryMock).insertVoucher(voucher);

    }

    @DisplayName("percent discount voucher가 생성된다. - mock")
    @Test
    void testCreatePercentDiscountVoucher() {
        // Given
        var voucherRepositoryMock = mock(VoucherRepository.class);
        var voucherFactoryMock = mock(VoucherFactory.class);
        UUID voucherId = UUID.randomUUID();
        var sut = new VoucherService(voucherRepositoryMock, voucherFactoryMock);

        // When
        var voucher = sut.createVoucher(NUMBER_PERCENT_TYPE, voucherId, 20);

        // Then
        verify(voucherFactoryMock).createVoucher(VoucherType.PERCENT_DISCOUNT, voucherId, 20);
        verify(voucherRepositoryMock).insertVoucher(voucher);

    }

    @Test
    @DisplayName("추가된 바우처가 list로 반환된다. - stub")
    void testGetAllVoucher() {
        // Given
        var voucherRepository = new MemoryVoucherRepository();
        var voucherFactory = new VoucherFactory();
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insertVoucher(voucher);

        var sut = new VoucherService(voucherRepository, voucherFactory);

        // When
        var allVoucher = sut.getAllVoucher();

        // Then
        assertThat(allVoucher.size(), is(1));
        assertThat(allVoucher.get(0), is(voucher));
        assertThat(allVoucher.get(0).getVoucherId(), is(voucher.getVoucherId()));

    }

}