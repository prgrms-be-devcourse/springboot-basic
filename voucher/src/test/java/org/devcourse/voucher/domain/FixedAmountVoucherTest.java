package org.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("FixedDiscountVoucher discount 성공 테스트")
    void discountSuccess() {
        long validBeforeDiscount = 100000;
        long validDiscountAmount = 5000;
        long expectedResult = 95000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(validDiscountAmount);
        assertEquals(expectedResult,fixedAmountVoucher.discount(validBeforeDiscount));
    }

    @Test
    @DisplayName("FixedDiscountVoucher discount 실패 테스트")
    void discountFail(){
        long validBeforeDiscount = 100000;
        long validDiscountAmount = 5000;
        long unexpectedResult = 5000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(validDiscountAmount);
        assertNotEquals(unexpectedResult,fixedAmountVoucher.discount(validBeforeDiscount));
    }

    @Test
    @DisplayName("FixedDiscountVoucher beforeDiscount 음수 예외처리 성공 테스트")
    void discountExceptionSuccess(){
        long invalidBeforeDiscount = -10000;
        long validDiscountAmount = 5000;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(validDiscountAmount);
        assertThrowsExactly(IllegalArgumentException.class, ()->{
            fixedAmountVoucher.discount(invalidBeforeDiscount);
        });
    }

}
