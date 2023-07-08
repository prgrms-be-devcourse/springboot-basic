package com.prgrms.model.voucher.discount;

import com.prgrms.model.voucher.dto.discount.PercentDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountTest {

    @Test
    @DisplayName("제한값보다 큰 경우 예외를 던지는지 테스트한다.")
    public void validLimit_AboveLimit_ExceptionThrown() {
        //given
        int value = 120;

        //when_then
        assertThatThrownBy(() -> new PercentDiscount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
