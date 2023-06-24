package org.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("PercentDiscountVoucher discount 성공 테스트")
    void discountSuccess() {
        long percent = 10;
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(percent);
        assertEquals(90000,percentDiscountVoucher.discount(100000));
    }
}