package com.program.commandLine.service;

import com.program.commandLine.model.VoucherInputData;
import com.program.commandLine.model.voucher.FixedAmountVoucher;
import com.program.commandLine.model.voucher.VoucherFactory;
import com.program.commandLine.model.voucher.VoucherType;
import com.program.commandLine.repository.MemoryVoucherRepository;
import com.program.commandLine.repository.VoucherRepository;
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
        var inputData = new VoucherInputData(NUMBER_FIXED_TYPE, "20");
        var sut = new VoucherService(voucherRepositoryMock, voucherFactoryMock);

        // When
        var voucher = sut.createVoucher(inputData);

        // Then
        verify(voucherFactoryMock).createVoucher(VoucherType.FIXED_AMOUNT_DISCOUNT, inputData.getVoucherId(), 20);
        verify(voucherRepositoryMock).insert(voucher);

    }

    @DisplayName("percent discount voucher가 생성된다. - mock")
    @Test
    void testCreatePercentDiscountVoucher() {
        // Given
        var voucherRepositoryMock = mock(VoucherRepository.class);
        var voucherFactoryMock = mock(VoucherFactory.class);
        var inputData = new VoucherInputData(NUMBER_PERCENT_TYPE, "20");
        var sut = new VoucherService(voucherRepositoryMock, voucherFactoryMock);

        // When
        var voucher = sut.createVoucher(inputData);

        // Then
        verify(voucherFactoryMock).createVoucher(VoucherType.PERCENT_DISCOUNT, inputData.getVoucherId(), 20);
        verify(voucherRepositoryMock).insert(voucher);

    }

    @Test
    @DisplayName("추가된 바우처가 list로 반환된다. - stub")
    void testGetAllVoucher() {
        // Given
        var voucherRepository = new MemoryVoucherRepository();
        var voucherFactory = new VoucherFactory();
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(voucher);

        var sut = new VoucherService(voucherRepository, voucherFactory);

        // When
        var allVoucher = sut.getAllVoucher();

        // Then
        assertThat(allVoucher.size(), is(1));
        assertThat(allVoucher.get(0), is(voucher));
        assertThat(allVoucher.get(0).getVoucherId(), is(voucher.getVoucherId()));

    }

}