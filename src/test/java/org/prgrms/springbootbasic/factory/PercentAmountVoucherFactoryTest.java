package org.prgrms.springbootbasic.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.entity.voucher.PercentAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

import static org.junit.jupiter.api.Assertions.*;

class PercentAmountVoucherFactoryTest {

    PercentAmountVoucherFactory percentAmountVoucherFactory = new PercentAmountVoucherFactory();

    @Test
    @DisplayName("percentAmountVoucher 생성 - 성공")
    void testPercentAmountVoucherCreate_success() {
        long percent = 20;
        Voucher voucher = percentAmountVoucherFactory.createVoucher(percent);

        assertTrue(voucher instanceof PercentAmountVoucher);
    }

    @Test
    @DisplayName("percentAmountVoucher 생성 - 실패 : percent 100 초과")
    void testPercentAmountVoucherCreate_fail_exceed_100() {
        long percent = 101;
        assertThrows(IllegalArgumentException.class, () -> percentAmountVoucherFactory.createVoucher(percent));
    }

    @Test
    @DisplayName("percentAmountVoucher 생성 - 실패 : percent 0 미만")
    void testPercentAmountVoucherCreate_fail_under_0() {
        long percent = -1;
        assertThrows(IllegalArgumentException.class, () -> percentAmountVoucherFactory.createVoucher(percent));
    }

}