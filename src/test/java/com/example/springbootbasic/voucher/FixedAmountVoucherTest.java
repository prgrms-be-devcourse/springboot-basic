package com.example.springbootbasic.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("기본적인 assertEquals 테스트")
    void testAssertEquals() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다")
    void testDiscount() {
        var fixedAmountVoucher = new FixedAmountVoucher(1000);
        assertEquals(9000, fixedAmountVoucher.discount(10000));
    }

    @Test
    @DisplayName("할인 금액은 음수가 될 수 없다")
    void testWithMinus() {
        assertThrows(IllegalStateException.class, () -> new FixedAmountVoucher(-100));
    }

    @Test
    @DisplayName("할인 된 금액은 음수가 될 수 없다")
    void testMinusDiscountAmount() {
        assertEquals(0, new FixedAmountVoucher(1000).discount(100));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher Creation",
                () -> assertThrows(IllegalStateException.class, () -> new FixedAmountVoucher(-100)),
                () -> assertThrows(IllegalStateException.class, () -> new FixedAmountVoucher(0)),
                () -> assertThrows(NumberFormatException
                        .class, () -> new FixedAmountVoucher("string")));
    }
}