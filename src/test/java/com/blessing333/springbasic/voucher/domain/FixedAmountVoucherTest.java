package com.blessing333.springbasic.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


class FixedAmountVoucherTest {

    @DisplayName("할인 금액이 상품 금액보다 작은 경우 할인적용 전 금액에서 할인 금액을 뺀 값을 반환한다.")
    @Test
    void discount() {
        long beforePrice = 5000;
        long discountAmount = 200;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        long afterDiscount = voucher.discount(beforePrice);

        Assertions.assertThat(afterDiscount).isEqualTo(beforePrice - discountAmount);
    }

    @DisplayName("할인 금액이 상품 금액보다 큰 경우 할인이 적용된 가격으로 0을 반환한다")
    @Test
    void discountAllPrice() {
        long beforePrice = 5000;
        long discountAmount = 100000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        long afterDiscount = voucher.discount(beforePrice);

        Assertions.assertThat(afterDiscount).isZero();
    }

    @DisplayName("할인 금액이 0보다 작으면 IllegalArgumentException 발생시킨다")
    @Test
    void negativeDiscountAmountTest(){
        long discountAmount = -500;
        assertThrows(IllegalArgumentException.class,()->new FixedAmountVoucher(discountAmount));
    }
}