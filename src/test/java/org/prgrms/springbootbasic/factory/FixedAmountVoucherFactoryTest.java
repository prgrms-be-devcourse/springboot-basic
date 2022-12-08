package org.prgrms.springbootbasic.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FixedAmountVoucherFactoryTest {
    final FixedAmountVoucherFactory fixedAmountVoucherFactory = new FixedAmountVoucherFactory();

    @Test
    @DisplayName("fixedAmountVoucher 생성 - 성공")
    void testFixedAmountVoucherCreate_succeed() {
        long amount = 1000;
        Voucher voucher = fixedAmountVoucherFactory.createVoucher(amount);
        assertTrue(voucher instanceof FixedAmountVoucher);
    }

    @Test
    @DisplayName("fixedAmountVoucher 생성 - 실패 : 0 미만")
    void testFixedAmountVoucherCreate_fail_under_0() {
        long amount = -1;
        assertThrows(IllegalArgumentException.class, () -> fixedAmountVoucherFactory.createVoucher(amount));
    }
}