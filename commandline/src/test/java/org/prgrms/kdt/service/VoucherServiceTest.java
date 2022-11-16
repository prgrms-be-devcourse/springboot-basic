package org.prgrms.kdt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Test
    @DisplayName("Voucher가 생성될 때, 레포지토리의 insert가 실행된다.")
    void createVoucherTest() {
        //given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository);
        UUID voucherId = UUID.randomUUID();

        Voucher fixedVoucher = new FixedAmountVoucher(voucherId, 20L);
        when(voucherRepository.insert(fixedVoucher)).thenReturn(fixedVoucher);

        //when
        Voucher resultVoucher = voucherService.createVoucher("1", voucherId, 20L);

        //then
        verify(voucherRepository).insert(fixedVoucher);
        assertThat(resultVoucher, samePropertyValuesAs(fixedVoucher));
    }

    @Test
    @DisplayName("Voucher를 나열할 때, 레포지토리의 findAll이 실행된다.")
    void getAllVoucherTest() {
        //given
        VoucherRepository voucherRepository = mock(VoucherRepository.class);
        VoucherService voucherService = new VoucherService(voucherRepository);
        List<Voucher> vouchers = List.of(
                new FixedAmountVoucher(UUID.randomUUID(), 20L),
                new PercentDiscountVoucher(UUID.randomUUID(), 30L)
        );

        when(voucherRepository.findAll()).thenReturn(vouchers);

        //when
        voucherService.getAllVoucher();

        //then
        verify(voucherRepository).findAll();
    }
}