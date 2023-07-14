package com.devcourse.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountPolicyTest {

    @ParameterizedTest
    @DisplayName("고정 할인 정책으로 계산하면 예상 결과와 동일해야 한다.")
    @CsvSource({"1000, 500, 500",
                "2500, 1000, 1500",
                "127, 1, 126"})
    void fixedDiscountTest(int price, int discountAmount, BigDecimal result) {
        // given
        FixedAmountPolicy policy = new FixedAmountPolicy();

        // when
        BigDecimal discounted = policy.discount(price, discountAmount);

        // then
        assertThat(discounted).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("퍼센트 할인 정책으로 계산하면 예상 결과와 동일해야 한다.")
    @CsvSource({"1000, 50, 500.0",
                "2500, 100, 0.0",
                "120, 10, 108.0"})
    void percentDiscountTest(int price, int discountRate, BigDecimal result) {
        // given
        PercentDiscountPolicy policy = new PercentDiscountPolicy();

        // when
        BigDecimal discounted = policy.discount(price, discountRate);

        // then
        assertThat(discounted).isEqualTo(result);
    }
}
