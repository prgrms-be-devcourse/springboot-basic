package org.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("FixedDiscountVoucher 생성 테스트")
    void successPercentDiscountVoucher() {
        UUID voucherId = UUID.randomUUID();
        long amount = 5000;

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, amount);
        assertEquals(95000,fixedAmountVoucher.discount(100000));
    }
}