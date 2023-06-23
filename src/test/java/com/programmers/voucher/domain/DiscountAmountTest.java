package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "100"
            , "235123412351235"
            , "-572356"
            , "0"
            , "25"
            , "21"
            , "342345"
    })
    public void discountAmountTest(long amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(AmountValueException.class);
    }

}