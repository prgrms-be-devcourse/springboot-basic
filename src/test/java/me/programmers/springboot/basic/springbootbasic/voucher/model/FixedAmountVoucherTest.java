package me.programmers.springboot.basic.springbootbasic.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인")
    void testDiscount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        var sut = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, sut.discount(900));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100))
        );
    }

}