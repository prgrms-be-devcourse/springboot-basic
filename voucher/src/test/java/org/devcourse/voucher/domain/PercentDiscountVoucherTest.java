package org.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("PercentDiscountVoucher discount 성공 테스트")
    void discountSuccess() {
        long validDiscountPercent = 10;
        long validBeforeDiscount = 100000;
        long expectedResult = 90000;
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(validDiscountPercent);
        assertEquals(expectedResult,percentDiscountVoucher.discount(validBeforeDiscount));
    }

    @Test
    @DisplayName("PercentDiscountVoucher discount 실패 테스트")
    void discountFail(){
        long validBeforeDiscount = 100000;
        long validDiscountPercent = 10;
        long unexpectedResult = 80000;
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(validDiscountPercent);
        assertNotEquals(unexpectedResult, percentDiscountVoucher.discount(validBeforeDiscount));
    }

    @Test
    @DisplayName("PercentDiscountVoucher beforeDiscount 음수 예외처리 성공 테스트")
    void discountExceptionSuccess(){
        long validDiscountPercent = 10;
        long invalidBeforeDiscount = -100000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(validDiscountPercent);
        assertThrowsExactly(IllegalArgumentException.class, ()->{
            fixedAmountVoucher.discount(invalidBeforeDiscount);
        });
    }
}
