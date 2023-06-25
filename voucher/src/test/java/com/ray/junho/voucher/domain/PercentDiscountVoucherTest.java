package com.ray.junho.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PercentDiscountVoucherTest {

    @DisplayName("할인 비율이 올바르지 못하면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0, 101, 1000})
    void name(int discountRate) {
        assertThatThrownBy(() -> new PercentDiscountVoucher(1L, discountRate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 비율이 올바르면 예외를 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 50, 100})
    void name1(int discountRate) {
        assertThatNoException()
                .isThrownBy(() -> new PercentDiscountVoucher(1L, discountRate));
    }

    @DisplayName("할인율을 적용했을 경우 올바른 Currency 객체가 반환된다.")
    @Test
    void 퍼센트할인_테스트() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(1L, 25);

        Currency result = percentDiscountVoucher.discount(Currency.of(1000));

        assertThat(result).isEqualTo(Currency.of(750));
    }

    @DisplayName("ID와 할인 비율이 같으면 같은 객체의 동등성을 보장한다")
    @Test
    void 객체_동등성_테스트() {
        PercentDiscountVoucher percentDiscountVoucher1 = new PercentDiscountVoucher(1L, 50);
        PercentDiscountVoucher percentDiscountVoucher2 = new PercentDiscountVoucher(1L, 50);

        assertThat(percentDiscountVoucher1).isEqualTo(percentDiscountVoucher2);
        assertThat(percentDiscountVoucher1).hasSameHashCodeAs(percentDiscountVoucher2);
    }
}