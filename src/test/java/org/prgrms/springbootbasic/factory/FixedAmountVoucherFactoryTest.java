package org.prgrms.springbootbasic.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.voucher.Voucher;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherFactoryTest {
    final FixedAmountVoucherFactory fixedAmountVoucherFactory = new FixedAmountVoucherFactory();

    @Test
    @DisplayName("fixedAmountVoucher 생성 - 성공")
    void testFixedAmountVoucherCreate() {
        long amount = 1000;
        Voucher voucher = fixedAmountVoucherFactory.createVoucher(amount);
        assertTrue(voucher instanceof FixedAmountVoucher);
    }

}