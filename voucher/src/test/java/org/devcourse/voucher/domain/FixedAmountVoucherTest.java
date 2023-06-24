package org.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("FixedDiscountVoucher discount 성공 테스트")
    void discountSuccess() {
        long discountAmount = 5000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(discountAmount);
        assertEquals(95000,fixedAmountVoucher.discount(100000));
    }

    @Test
    @DisplayName("FixedDiscountVoucher discount 실패 테스트")
    void discountFail(){
        long discountAmount = 5000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(discountAmount);
        assertNotEquals(5000,fixedAmountVoucher.discount(100000));
    }

}