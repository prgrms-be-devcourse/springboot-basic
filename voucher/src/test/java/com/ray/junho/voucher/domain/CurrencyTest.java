package com.ray.junho.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CurrencyTest {

    @DisplayName("입력 값이 0 미만일 경우 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -2, -1})
    void 입력_0_미만_예외(int value) {
        assertThatThrownBy(() -> Currency.of(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 값이 0보다 같거나 클 경우 예외를 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 100})
    void 입력_0_이상_예외X(int value) {
        assertThatNoException()
                .isThrownBy(() -> Currency.of(value));
    }

    @DisplayName("할인 적용되는 고정 금액이 현재 금액 보다 크다면 예외를 발생한다.")
    @Test
    void 할인고정금액이_할인금액_보다크면_예외() {
        Currency money = Currency.of(100);
        Currency discountValue = Currency.of(101);

        assertThatThrownBy(() -> money.discountedWithFixedPrice(discountValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 적용되는 고정 금액이 현재 금액 보다 같거나 적다면 예외를 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 99, 100})
    void 할인고정금액이_할인금액_작거나같으면_예외X(int value) {
        Currency money = Currency.of(100);
        Currency discountValue = Currency.of(value);

        assertThatNoException()
                .isThrownBy(() -> money.discountedWithFixedPrice(discountValue));
    }

    @DisplayName("같은 금액의 Currency 객체의 동등성을 보장한다.")
    @Test
    void eqauals_hashcode_동등성비교() {
        Currency money1 = Currency.of(1000);
        Currency money2 = Currency.of(1000);

        assertThat(money1).isEqualTo(money2);
        assertThat(money1).hasSameHashCodeAs(money2);
    }

    @DisplayName("고정 금액으로 할인했을 경우 올바른 Currency 객체가 반환된다.")
    @Test
    void 고정금액할인_테스트() {
        Currency money = Currency.of(1000);

        Currency result = money.discountedWithFixedPrice(Currency.of(900));

        assertThat(result).isEqualTo(Currency.of(100));
    }

    @DisplayName("할인율을 적용했을 경우 올바른 Currency 객체가 반환된다.")
    @Test
    void 퍼센트할인_테스트() {
        Currency money = Currency.of(1000);

        Currency currency = money.discountedWithPercentage(25);

        System.out.println(currency.toString());
        assertThat(currency).isEqualTo(Currency.of(750));
    }
}