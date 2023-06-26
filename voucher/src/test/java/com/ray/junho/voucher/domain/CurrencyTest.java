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
    void when_UnderZero_Expects_ThrowException(int value) {
        assertThatThrownBy(() -> Currency.of(value))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 값이 0보다 같거나 클 경우 예외를 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 100})
    void when_EqualToOrOverZero_Expects_DoesNotThrowException(int value) {
        assertThatNoException()
                .isThrownBy(() -> Currency.of(value));
    }

    @DisplayName("같은 금액의 Currency 객체의 동등성을 보장한다.")
    @Test
    void when_GivenSameValueOfCurrency_Expects_Same() {
        // Given, When
        Currency money1 = Currency.of(1000);
        Currency money2 = Currency.of(1000);

        // Then
        assertThat(money1).isEqualTo(money2);
        assertThat(money1).hasSameHashCodeAs(money2);
    }

    @DisplayName("특정 값을 뺀 값을 반환한다.")
    @Test
    void when_SubtractValue_Expects_ReturnSubtractedValue() {
        // Given
        Currency money = Currency.of(1000);

        // When
        long actual = money.minus(100);

        // Then
        assertThat(actual).isEqualTo(900);

    }

    @DisplayName("특정 값보다 currency의 값이 적으면 true를 반환한다.")
    @Test
    void when_CurrencyValueIsLessThanGivenValue_Expects_ReturnTrue() {
        // Given
        Currency money = Currency.of(1000);

        // When
        boolean actual = money.isLessThan(1001);

        // Then
        assertThat(actual).isTrue();
    }

    @DisplayName("특정 값보다 currency의 값이 같거나 크면 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 999, 1000})
    void when_CurrencyValueIsEqualToOrGreaterThanGivenValue_Expects_ReturnFalse(int value) {
        // Given
        Currency money = Currency.of(1000);

        // When
        boolean actual = money.isLessThan(value);

        // Then
        assertThat(actual).isFalse();
    }
}