package com.programmers.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DiscountValueTest {

    @DisplayName("할인 값이 1미만 1,000,000,000 초과인 경우 에러를 반환한다")
    @ParameterizedTest
    @ValueSource(longs = {
            -1,
            0,
            1000000001,
    })
    public void maxAndMinDiscountAmountTest(long amount) {
        assertThatThrownBy(() -> new FixedDiscount(amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 값이 1이상 1,000,000,000 이하인 경우 정상 작동한다.")
    @ParameterizedTest
    @ValueSource(longs = {
            1,
            123424,
            5215355,
            999999999,
            1000000000,
    })
    public void validDiscountAmountTest(long amount) {
        assertThatNoException().isThrownBy(() -> new FixedDiscount(amount));
    }

    @DisplayName("할인 퍼센트가 최대값 100 최소값 1의 유효범위를 넘어가는 경우 에러를 반환한다.")
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

    @DisplayName("할인 퍼센트가 1이상 100 이하인 경우 정상 작동한다.")
    @ParameterizedTest
    @ValueSource(longs = {
            1,
            13,
            52,
            99,
            100,
    })
    public void validDiscountPercentTest(long percent) {
        assertThatNoException().isThrownBy(() -> new PercentDiscount(percent));
    }
}
