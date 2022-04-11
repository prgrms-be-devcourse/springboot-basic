package com.programmers.springbootbasic.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("유효하지 않은 생성자 인자가 들어왔을 때 예외를 발생시킨다.")
    void ConstructorTest() {
        //Given
        Long fixedAmountUpperLimit = FixedAmountVoucher.FIXED_AMOUNT_UPPER_LIMIT;
        Long zero = 0L;
        Long underZero = -15L;
        Long validInput = 1500L;

        //When and then
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), fixedAmountUpperLimit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효한 값이 아닙니다.");
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), zero))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효한 값이 아닙니다.");
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), underZero))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효한 값이 아닙니다.");

        assertThat(new FixedAmountVoucher(UUID.randomUUID(), validInput).getFixedAmount())
                .isEqualTo(validInput);
    }

    @Test
    @DisplayName("기존 금액에서 할인 금액을 뺀 최종 금액은 음수가 될 수 없다.")
    void discountTest() {
        //Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 6000L);

        //When
        Long lessThanFixedAmount = 5000L;
        Long greaterThanFixedAmount = 10000L;

        //Then
        assertThat(fixedAmountVoucher.discount(lessThanFixedAmount)).isEqualTo(0);
        assertThat(fixedAmountVoucher.discount(greaterThanFixedAmount)).isEqualTo(4000L);
    }

}