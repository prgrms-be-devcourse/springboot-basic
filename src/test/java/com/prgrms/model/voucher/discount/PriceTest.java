package com.prgrms.model.voucher.discount;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void getValue_NegativePrice_ThrowsException() {
        //given
        double value = -100_000;
        //when_then
        assertThatThrownBy(()->new Price(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
