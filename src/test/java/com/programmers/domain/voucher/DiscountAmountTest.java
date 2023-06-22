package com.programmers.domain.voucher;

import com.programmers.exception.AmountValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "100"
            , "235123412351235"
            , "-572356"
            , "0"
    })
    public void discountAmountTest(long amount) {

        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(AmountValueException.class);
    }

}