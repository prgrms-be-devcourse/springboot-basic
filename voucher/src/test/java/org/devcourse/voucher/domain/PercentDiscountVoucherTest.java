package org.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("PercentDiscountVoucher 생성 테스트")
    void successPercentDiscountVoucher() {
        UUID voucherId = UUID.randomUUID();
        long percent = 10L;

        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, percent);
        assertEquals(10000,percentDiscountVoucher.discount(100000));
    }
}