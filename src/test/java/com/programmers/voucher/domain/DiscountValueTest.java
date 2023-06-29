package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountValueTest {

    @DisplayName("할인 값이 1미만 1,000,000,000 초과인 경우 에러를 반환한다")
    @ParameterizedTest
    @ValueSource(longs = {
            -1,
            0,
            1000000001,
            1,
            1000000000
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
