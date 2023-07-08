package com.programmers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountTypeTest {

    @Nested
    @DisplayName("할인 유형에 적합한 DiscountType 을 반환한다.")
    class from_success {

        @Test
        @DisplayName("fix 가 들어온 경우")
        void WhenFixComeInto() {
            // given
            String number = "1";  // Fix amount

            // when
            DiscountType result = DiscountType.from(number);

            // then
            assertThat(result).isEqualTo(DiscountType.FIX);
        }

        @Test
        @DisplayName("percent 가 들어온 경우")
        void WhenPercentComeInto() {
            // given
            String number = "2";    // Percent

            // when
            DiscountType result = DiscountType.from(number);

            // then
            assertThat(result).isEqualTo(DiscountType.PERCENT);
        }
    }

    @Test
    @DisplayName("존재하지 않는 할인 유형이 들어오면 에외가 발생한다.")
    void from_exception() {
        // given
        String number = "3";

        // when & then
        assertThatThrownBy(() -> DiscountType.from(number))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 할인 유형 입니다.");
    }
}