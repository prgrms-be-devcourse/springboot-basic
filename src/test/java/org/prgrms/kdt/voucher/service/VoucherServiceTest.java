package org.prgrms.kdt.voucher.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VoucherServiceTest {
    private VoucherRepository mockVoucherRepository;
    private VoucherService voucherService;

    @BeforeEach
    public void setUp(){
        mockVoucherRepository = mock(VoucherRepository.class);
        voucherService = new VoucherService(mockVoucherRepository);
    }

    @Test
    public void createVoucherTest_FixedVoucher생성(){
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID());
        when(mockVoucherRepository.insert(fixedAmountVoucher)).thenReturn(fixedAmountVoucher);

        Voucher voucher = voucherService.createVoucher(VoucherType.FIXED);

        assertThat(voucher, is(instanceOf(FixedAmountVoucher.class)));
    }

    @Test
    public void createVoucherTest_PercentDiscountVoucher생성(){
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID());
        when(mockVoucherRepository.insert(percentDiscountVoucher)).thenReturn(percentDiscountVoucher);

        Voucher voucher = voucherService.createVoucher(VoucherType.PERCENT);

        assertThat(voucher, is(instanceOf(PercentDiscountVoucher.class)));
    }
}