package org.prgms.kdtspringvoucher.voucher.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {


    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherService voucherService;

    @Test
    @DisplayName("Repo에 저장된 데이터를 보여주고 반환해야한다.")
    void showAllVoucherTest() {
        //given
        when(voucherRepository.findAll()).thenReturn(List.of(
                new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 100L, VoucherType.FIXED, LocalDateTime.now()),
                new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 200L, VoucherType.FIXED, LocalDateTime.now()),
                new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 300L, VoucherType.FIXED, LocalDateTime.now())
        ));
        //when
        List<Voucher> vouchers = voucherService.getVouchers();
        //then
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.size(), is(3));
    }

    @ParameterizedTest
    @DisplayName("FIXED 바우처가 생성 되어야한다.")
    @CsvSource({"FIXED,1000", "FIXED,10", "FIXED,3000", "FIXED,20"})
    void createFixedVoucherTest(VoucherType voucherType, Long amount) {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now());
        when(voucherRepository.save(any())).thenReturn(fixedAmountVoucher);

        Voucher voucher = voucherService.createVoucher(voucherType, amount);

        assertThat(voucher, instanceOf(fixedAmountVoucher.getClass()));
        assertThat(voucher, is(fixedAmountVoucher));
    }

    @ParameterizedTest
    @DisplayName("FIXED 바우처가 생성 되어야한다.")
    @CsvSource({"PERCENT,11", "PERCENT,10", "PERCENT,33", "PERCENT,20"})
    void createPercentVoucherTest(VoucherType voucherType, Long amount) {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), amount, voucherType, LocalDateTime.now());
        when(voucherRepository.save(any())).thenReturn(percentDiscountVoucher);

        Voucher voucher = voucherService.createVoucher(voucherType, amount);

        assertThat(voucher, instanceOf(percentDiscountVoucher.getClass()));
        assertThat(voucher, is(percentDiscountVoucher));
    }
}