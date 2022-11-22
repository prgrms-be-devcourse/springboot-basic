package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("유효한 할인율로 생성해야 한다")
    void createPercentDiscountVoucher_amountLessThanZero_throwException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -20L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0L)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 120L))
        );
    }

    @Test
    @DisplayName("정상적인 객체 생성")
    void createFixedAmountVoucher_withinRange_instanceOfClass(){
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30L);
        assertThat(percentDiscountVoucher).isInstanceOf(PercentDiscountVoucher.class);
    }


    @Test
    @DisplayName("정상적인 할인 금액")
    void calculateAmount_lessThanBeforeAmount_becomeDiscountedAmount() {
        long beforeDiscount = 30000L;
        long discountPercent = 30L;
        long expectedDiscount = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent).discount(beforeDiscount);
        assertThat(beforeDiscount * (100 - discountPercent) / 100, equalTo(expectedDiscount));
    }
}