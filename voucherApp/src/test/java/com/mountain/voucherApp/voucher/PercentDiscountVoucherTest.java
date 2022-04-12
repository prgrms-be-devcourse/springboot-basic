package com.mountain.voucherApp.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("100퍼 센트 할인은 공짜(0원) 이다.")
    void testMinusDiscount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 100);
        assertEquals(0, sut.discount(900));
    }

    @Test
    @DisplayName("할인 은 마이너스가 될 수 없다.")
        // @Disabled
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("유효한 할인율로만 생성할 수 있다.")
        // @Disabled
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 101))
        );
    }
}