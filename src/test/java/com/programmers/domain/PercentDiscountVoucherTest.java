package com.programmers.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class PercentDiscountVoucherTest {

    @DisplayName("PercentDiscountVoucher 객체를 생성한다")
    @Test
    void PercentDiscountVoucher() {
        //given
        //when
        PercentDiscountVoucher result = new PercentDiscountVoucher(UUID.randomUUID(), "name", 1L);

        //then
        Assertions.assertThat(result).isInstanceOf(PercentDiscountVoucher.class);
    }

    @DisplayName("PercentDiscountVoucher 생성자에 이름이 입력되지 않은 경우 예외처리한다")
    @Test
    void PercentDiscountVoucherWithEmptyName() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), "", 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("PercentDiscountVoucher 생성자의 amount 값이 1보다 작거나 100보다 크면 예외처리한다")
    @ValueSource(strings = {"0", "101"})
    @ParameterizedTest
    void PercentDiscountVoucherWithWrongAmount(int input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), "", input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}