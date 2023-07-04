package com.programmers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PercentDiscountPolicyTest {

    @Nested
    @DisplayName("할인률을 검증한다.")
    class validationAmount {

        @ParameterizedTest
        @ValueSource(ints = {1, 50, 100})
        @DisplayName("할인률이 1에서 100 사이인 경우 예외가 발생하지 않는다.")
        void isBetween1And100(int amount) {
            // when & then
            assertThatCode(() -> new PercentDiscountPolicy(amount))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 0, 101})
        @DisplayName("할인률이 1에서 100 사이가 아닌 경우 예외가 발생한다.")
        void isNotBetween1And100(int amount) {
            // when & then
            assertThatThrownBy(() -> new PercentDiscountPolicy(amount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The discount percentage must be between 1 and 100%.");
        }
    }

    @ParameterizedTest
    @CsvSource({"10, 9000", "50, 5000", "100, 0", "33, 6700"})
    @DisplayName("정률 할인을 적용한다.")
    void discount(int amount, int expected) {
        // given
        DiscountPolicy discountPolicy = new PercentDiscountPolicy(amount);

        // when
        int result = discountPolicy.discount(10000);

        // then
        assertThat(result).isEqualTo(expected);
    }
}