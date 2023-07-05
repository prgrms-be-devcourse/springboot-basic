package com.programmers.domain.voucher;

import com.programmers.domain.voucher.PercentDiscountVoucher;
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

    @DisplayName("할인이 적용된 최종 값을 구한다")
    @Test
    void discount() {
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testVoucher", 30L);
        long originalAmount = 100L;

        //when
        long result = percentDiscountVoucher.discount(originalAmount);

        //then
        Assertions.assertThat(result).isEqualTo(70L);
    }
}