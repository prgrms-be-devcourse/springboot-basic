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

    @Test
    @DisplayName("PercentDiscountVoucher discount 실패 테스트")
    void discountFail(){
        long discountPercent = 10;
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(discountPercent);
        assertNotEquals(80000, percentDiscountVoucher.discount(100000));
    }

    @Test
    @DisplayName("PercentDiscountVoucher beforeDiscount 음수 예외처리 성공 테스트")
    void discountExceptionSuccess(){
        long discountPercent = 10;
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(discountPercent);
        assertThrowsExactly(IllegalArgumentException.class, ()->{
            fixedAmountVoucher.discount(-100000);
        });
    }
}
