package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountValueTest {

    @DisplayName("할인 값이 최대값 1,000,000,000 최소값 1의 유효범위를 넘어가는 경우")
    @ParameterizedTest
    @ValueSource(longs = {
            -1,
            0,
            -2,
            9423452343123L
    })
    public void maxAndMinDiscountAmountTest(long amount) {
        assertThatThrownBy(() -> new FixedDiscount(amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 퍼센트가 최대값 100 최소값 1의 유효범위를 넘어가는 경우")
    @ParameterizedTest
    @ValueSource(longs = {
            0,
            -1,
            -100,
            101,
    })
    public void maxAndMinPercentTest(long percent) {
        assertThatThrownBy(() -> new PercentDiscount(percent))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
