package com.wonu606.vouchermanager.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("음수일 경우 에러가 발생한다.")
    void calculateDiscountedPriceShouldThrowErrorWhenNegative() {
        // given
        UUID uuid = UUID.randomUUID();
        double fixedAmount = -1000d;

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> new FixedAmountVoucher(uuid, fixedAmount));
    }

    @Test
    @DisplayName("가격보다 작은 양수일 경우 해당 금액만큼 할인된다.")
    void calculateDiscountedPriceShouldReduceAmountWhenAmountIsLessThanPrice() {
        // given
        UUID uuid = UUID.randomUUID();
        double fixedAmount = 1000d;
        double originalPrice = 20000d;

        // when
        Voucher voucher = new FixedAmountVoucher(uuid, fixedAmount);
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertEquals(19000, discountedPrice);
    }

    @Test
    @DisplayName("가격과 할인 금액이 같다면 0원이 된다.")
    void calculateDiscountedPriceShouldReturnZeroWhenDiscountAmountEqualsPrice() {
        // given
        UUID uuid = UUID.randomUUID();
        double fixedAmount = 20000d;
        double originalPrice = 20000d;

        // when
        Voucher voucher = new FixedAmountVoucher(uuid, fixedAmount);
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertEquals(0, discountedPrice);
    }

    @Test
    @DisplayName("가격보다 큰 할인 금액일 경우 0원이 된다.")
    void calculateDiscountedPriceShouldBecomeZeroWhenDiscountAmountIsGreaterThanPrice() {
        // given
        UUID uuid = UUID.randomUUID();
        double fixedAmount = 25000d;
        double originalPrice = 20000d;

        // when
        Voucher voucher = new FixedAmountVoucher(uuid, fixedAmount);
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertEquals(0, discountedPrice);
    }
}
