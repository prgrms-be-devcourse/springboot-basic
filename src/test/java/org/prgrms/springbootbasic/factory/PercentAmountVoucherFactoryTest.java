package org.prgrms.springbootbasic.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.voucher.Voucher;

import static org.junit.jupiter.api.Assertions.*;

class PercentAmountVoucherFactoryTest {

    PercentAmountVoucherFactory percentAmountVoucherFactory = new PercentAmountVoucherFactory();

    @Test
    @DisplayName("percentAmountVoucher 생성 - 성공")
    void testPercentAmountVoucherCreate() {
        long percent = 20;
        Voucher voucher = percentAmountVoucherFactory.createVoucher(percent);

        assertTrue(voucher instanceof PercentAmountVoucher);
    }

}