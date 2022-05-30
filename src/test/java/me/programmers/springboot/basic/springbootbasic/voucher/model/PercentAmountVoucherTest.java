package me.programmers.springboot.basic.springbootbasic.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentAmountVoucherTest {

    @Test
    @DisplayName("주어진 퍼센트만큼 할인")
    void testDiscount() {
        var sut = new PercentAmountVoucher(UUID.randomUUID(), 20);
        assertEquals(800, sut.discount(1000));
    }

    @Test
    @DisplayName("100%가 넘게 할인할 수 없다.")
    void testOverDiscountedPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentAmountVoucher(UUID.randomUUID(), 120));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new PercentAmountVoucher(UUID.randomUUID(), -20));
    }
}