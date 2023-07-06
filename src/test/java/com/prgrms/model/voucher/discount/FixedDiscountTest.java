package com.prgrms.model.voucher.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class FixedDiscountTest {
    @Test
    @DisplayName("음수인 경우 예외를 던지는지 테스트한다.")
    public void validPositiveDiscount_NegativeValue_ExceptionThrown() {
        //given
        int value = -10;
        //when_then
        assertThatThrownBy(()->new FixedDiscount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("제한값보다 큰 경우 예외를 던지는지 테스트한다.")
    public void validLimit_AboveLimit_ExceptionThrown() {
        //given
        int value = 20_000;
        //when_then
        assertThatThrownBy(()->new FixedDiscount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
