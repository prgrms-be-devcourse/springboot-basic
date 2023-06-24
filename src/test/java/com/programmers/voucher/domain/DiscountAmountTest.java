package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountAmountTest {

    @DisplayName("할인 값이 최대값 최소값의 유효범위를 넘어가는 경우")
    @ParameterizedTest
    @ValueSource(strings = {
            "100",
            "235123412351235",
            "-572356",
            "0",
            "25",
            "21",
            "342345"
    })
    public void maxAndMinDiscountAmountTest(long amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(AmountValueException.class);
    }
}