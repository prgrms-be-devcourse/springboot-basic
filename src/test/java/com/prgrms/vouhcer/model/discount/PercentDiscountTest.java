package com.prgrms.vouhcer.model.discount;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.voucher.model.discount.PercentDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountTest {

    @Test
    @DisplayName("사용자가 입력한 할인율이 100%이상이 넘어가는 경우 예외를 던진다.")
    void validLimit_AboveLimit_ExceptionThrown() {
        //given
        int value = 120;

        //when_then
        assertThatThrownBy(() -> new PercentDiscount(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
