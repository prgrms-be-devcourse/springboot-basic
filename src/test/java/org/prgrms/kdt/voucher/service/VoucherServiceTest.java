package org.prgrms.kdt.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VoucherServiceTest {
    private VoucherRepository mockVoucherRepository;
    private VoucherService voucherService;

    @BeforeEach
    void setup() {
        mockVoucherRepository = mock(VoucherRepository.class);
        voucherService = new VoucherService(mockVoucherRepository);
    }

    @Test
    void creat_FixedVoucher_생성() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID());
        when(mockVoucherRepository.insert(fixedAmountVoucher)).thenReturn(fixedAmountVoucher);

        //when
        Voucher cratedVoucher = voucherService.createVoucher(VoucherType.FIXED);

        //then
        assertThat(cratedVoucher, is(instanceOf(FixedAmountVoucher.class)));
    }

    @Test
    void create_PercentDiscountVoucher_생성() {
        //given
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID());
        when(mockVoucherRepository.insert(percentDiscountVoucher)).thenReturn(percentDiscountVoucher);

        //when
        Voucher cratedVoucher = voucherService.createVoucher(VoucherType.PERCENT);

        //then
        assertThat(cratedVoucher, is(instanceOf(PercentDiscountVoucher.class)));
    }
}