package com.blessing333.springbasic.component.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    @Test
    void discount() {
        long beforePrice = 5000;
        int discountPercent = 50;
        long expectedResult = 2500;

        Voucher voucher = new PercentDiscountVoucher(discountPercent);
        long afterDiscountPrice = voucher.discount(beforePrice);

        Assertions.assertThat(afterDiscountPrice).isEqualTo(expectedResult);
    }
}