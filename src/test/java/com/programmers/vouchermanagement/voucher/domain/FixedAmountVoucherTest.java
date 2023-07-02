package com.programmers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class FixedAmountVoucherTest {

    @Nested
    @DisplayName("할인 금액을 검증한다.")
    class validationAmount {

        @ParameterizedTest
        @ValueSource(ints = {1, 10})
        @DisplayName("최소 할인 금액 이상인 경우 예외가 발생하지 않는다.")
        void isMoreThanMinAmount(int amount) {
            // when & then
            assertThatCode(() -> new FixedAmountVoucher(amount))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1})
        @DisplayName("최소 할인 금액 미만인 경우 예외가 발생한다.")
        void lessThanMinAmount(int amount) {
            // when & then
            assertThatThrownBy(() -> {
                new FixedAmountVoucher(amount);
            })
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("The minimum discount amount is 1.");
        }
    }

    @ParameterizedTest
    @CsvSource({"20000, 10000", "10000, 0", "5000, 0"})
    @DisplayName("정액 할인을 적용한다.")
    void discount(int originalPrice, int expected) {
        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(10000);

        // when
        int result = fixedAmountVoucher.discount(originalPrice);

        // then
        assertThat(result).isEqualTo(expected);
    }
}