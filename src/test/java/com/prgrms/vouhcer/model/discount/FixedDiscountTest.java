package com.prgrms.vouhcer.model.discount;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.prgrms.common.exception.NegativeArgumentException;
import com.prgrms.voucher.exception.AmountLimitException;
import com.prgrms.voucher.model.discount.FixedDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedDiscountTest {

    @Test
    @DisplayName("바우처의 고정 할인값에 음수가 들어오면 바우처 정책을 생성할 수 없어 예외를 던진다.")
    void validPositiveDiscount_NegativeValue_ExceptionThrown() {
        //given
        int value = -10;

        //when_then
        assertThatThrownBy(() -> new FixedDiscount(value))
                .isInstanceOf(NegativeArgumentException.class);
    }

    @Test
    @DisplayName("설정한 할인 금액의 값보다 큰 경우 할인 금액을 입력하면 예외를 던진다.")
    void validLimit_AboveLimit_ExceptionThrown() {
        //given
        int value = 20_000;

        //when_then
        assertThatThrownBy(() -> new FixedDiscount(value))
                .isInstanceOf(AmountLimitException.class);
    }

}
