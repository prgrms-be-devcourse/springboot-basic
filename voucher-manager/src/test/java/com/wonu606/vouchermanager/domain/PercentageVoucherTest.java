package com.wonu606.vouchermanager.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentageVoucherTest {

    @Test
    @DisplayName("0보다 작을 경우 에러가 발생한다.")
    void calculateDiscountedPriceShouldThrowErrorWhenNegative() {
        // given
        UUID uuid = UUID.randomUUID();
        double fixedAmount = 1000d;
        double percentage = -10d;

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> new PercentageVoucher(uuid, percentage));
    }

    @Test
    @DisplayName("100보다 클 경우 에러가 발생한다.")
    void calculateDiscountedPriceShouldThrowErrorWhenGreaterThanHundred() {
        // given
        UUID uuid = UUID.randomUUID();
        double percentage = 200d;
        double originalPrice = 20000d;

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> new PercentageVoucher(uuid, percentage));
    }

    @Test
    @DisplayName("0~100 안의 값일 경우 해당 퍼센트만큼 할인한다.")
    void calculateDiscountedPriceShouldDiscountByPercentageWhenValueIsBetweenZeroAndHundred() {
        // given
        UUID uuid = UUID.randomUUID();
        double percentage = 50d;
        double originalPrice = 20000d;

        // when
        Voucher voucher = new PercentageVoucher(uuid, percentage);
        double discountedPrice = voucher.calculateDiscountedPrice(originalPrice);

        // then
        assertEquals(10000d, discountedPrice);
    }
}
