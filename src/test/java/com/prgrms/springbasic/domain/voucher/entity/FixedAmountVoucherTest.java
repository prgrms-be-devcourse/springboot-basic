package com.prgrms.springbasic.domain.voucher.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인")
    void testDiscount() {
        Voucher sut = FixedAmountVoucher.create(UUID.randomUUID(), "fixed", 100);
        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다")
    void testMinusDiscountedValue() {
        Voucher sut = FixedAmountVoucher.create(UUID.randomUUID(), "fixed", 100);
        assertEquals(0, sut.discount(90));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testDiscountWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> FixedAmountVoucher.create(UUID.randomUUID(), "fixed", -100));
    }
}
