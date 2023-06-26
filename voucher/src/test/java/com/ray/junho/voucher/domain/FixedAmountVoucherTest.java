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
    void when_DiscountedValueIsEqualToOrLessThanZero_ThrowException(int discountValue) {
        assertThatThrownBy(() -> new FixedAmountVoucher(1L, discountValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인 금액이 0보다 크면 예외를 발생하지 않는다")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 100})
    void when_DiscountedValueIsGreaterThanZero_DoesNotThrowException(int discountValue) {
        assertThatNoException()
                .isThrownBy(() -> new FixedAmountVoucher(1L, discountValue));
    }

    @DisplayName("고정 금액으로 할인했을 경우 올바른 Currency 객체가 반환된다.")
    @Test
    void when_DiscountUsingFixedAmountVoucher_Expects_ReturnDiscountedCurrency() {
        // Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1L, 900);

        // When
        Currency result = fixedAmountVoucher.discount(Currency.of(1000));

        // Then
        assertThat(result).isEqualTo(Currency.of(100));
    }

    @DisplayName("할인 적용되는 고정 금액이 현재 금액 보다 크다면 예외를 발생한다.")
    @Test
    void when_DiscountedValueIsGreaterThanCurrentCurrency_Expects_ThrowException() {
        // Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1L, 1100);

        // When, Then
        assertThatThrownBy(() -> fixedAmountVoucher.discount(Currency.of(1000)))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("할인 적용되는 고정 금액이 현재 금액 보다 같거나 적다면 예외를 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 99, 100})
    void when_DiscountedValueIsEqualToOrLessThanCurrentCurrency_Expects_DoesNotThrowException(int value) {
        // Given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(1L, value);

        // When, Then
        assertThatNoException()
                .isThrownBy(() -> fixedAmountVoucher.discount(Currency.of(100)));
    }

    @DisplayName("ID와 할인 금액이 같으면 같은 객체의 동등성을 보장한다")
    @Test
    void when_GivenSameIdAndSameDiscountedValue_Expects_SameObject() {
        // Given, When
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(1L, 100);
        FixedAmountVoucher fixedAmountVoucher2 = new FixedAmountVoucher(1L, 100);

        // Then
        assertThat(fixedAmountVoucher1).isEqualTo(fixedAmountVoucher2);
        assertThat(fixedAmountVoucher1).hasSameHashCodeAs(fixedAmountVoucher2);
    }
}