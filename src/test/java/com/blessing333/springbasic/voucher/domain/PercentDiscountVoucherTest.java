package com.blessing333.springbasic.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {

    @DisplayName("할인 비율만큼 할인된 가격을 반환한다.")
    @Test
    void discount() {
        long beforePrice = 5000;
        int discountPercent = 50;
        long expectedResult = 2500;

        Voucher voucher = new PercentDiscountVoucher(discountPercent);
        long afterDiscountPrice = voucher.discount(beforePrice);

        Assertions.assertThat(afterDiscountPrice).isEqualTo(expectedResult);
    }

    @DisplayName("할인 비율이 1보다 작은 경우 IllegalArgumentException 발생시킨다.")
    @Test
    void discountPercentIsUnder0() {
        int discountPercent = 0;
        assertThrows(IllegalArgumentException.class,()->new PercentDiscountVoucher(discountPercent));
    }

    @DisplayName("할인 비율이 101 이상인 경우 IllegalArgumentException 발생시킨다.")
    @Test
    void discountPercentIsOver100() {
        int discountPercent = 101;
        assertThrows(IllegalArgumentException.class,()->new PercentDiscountVoucher(discountPercent));
    }

}