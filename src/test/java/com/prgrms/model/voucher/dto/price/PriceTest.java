package com.prgrms.model.voucher.dto.price;

import com.prgrms.model.order.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @Test
    @DisplayName("가격이 음수인 경우 예외를 던진다.")
    void getValue_NegativePrice_ThrowsException() {
        //given
        double value = -100_000;

        //when_then
        assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
