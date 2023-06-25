package com.ray.junho.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class FixedAmountVoucherTest {

    @DisplayName("할인 금액이 0보다 같거나 작다면 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-100, -1, 0})
    void 할인금액이_0보다_같거나작으면_예외발생(int discountValue) {
        assertThatThrownBy(() -> new FixedAmountVoucher(1L, discountValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액이 0보다 크면 예외를 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 100})
    void 할인금액이_0보다_크면_예외발생X(int discountValue) {
        assertThatNoException()
                .isThrownBy(() -> new FixedAmountVoucher(1L, discountValue));
    }

    @DisplayName("고정 금액으로 할인했을 경우 올바른 Currency 객체가 반환된다.")
    @Test
    void 고정금액할인_테스트() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1L, 900);

        Currency result = fixedAmountVoucher.discount(Currency.of(1000));

        assertThat(result).isEqualTo(Currency.of(100));
    }

    @DisplayName("ID와 할인 금액이 같으면 같은 객체의 동등성을 보장한다")
    @Test
    void 객체_동등성_테스트() {
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(1L, 100);
        FixedAmountVoucher fixedAmountVoucher2 = new FixedAmountVoucher(1L, 100);

        assertThat(fixedAmountVoucher1).isEqualTo(fixedAmountVoucher2);
        assertThat(fixedAmountVoucher1).hasSameHashCodeAs(fixedAmountVoucher2);
    }
}