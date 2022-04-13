package com.blessing333.springbasic.voucher.domain;

import com.blessing333.springbasic.component.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.component.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class FixedAmountVoucherTest {

    @DisplayName("할인 금액이 상품 금액보다 작은 경우")
    @Test
    void discount() {
        long beforePrice = 5000;
        long discountAmount = 200;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        long afterDiscount = voucher.discount(beforePrice);

        Assertions.assertThat(afterDiscount).isEqualTo(beforePrice - discountAmount);
    }

    @DisplayName("할인 금액이 상품 금액보다 큰 경우")
    @Test
    void discountAllPrice() {
        long beforePrice = 5000;
        long discountAmount = 100000;
        Voucher voucher = new FixedAmountVoucher(discountAmount);
        long afterDiscount = voucher.discount(beforePrice);

        Assertions.assertThat(afterDiscount).isZero();
    }
}