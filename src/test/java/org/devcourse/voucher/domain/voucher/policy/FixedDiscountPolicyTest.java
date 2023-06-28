package org.devcourse.voucher.domain.voucher.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedDiscountPolicyTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 100, 10000, 300})
    @DisplayName("고정 할인 정책을 생성에 성공한다")
    void successCreateFixedDiscountPolicy(int amount) {
        FixedDiscountPolicy fixedDiscountPolicy = new FixedDiscountPolicy(amount);

        assertThat(fixedDiscountPolicy).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1999, 100000000})
    @DisplayName("고정 할인 정책을 생성에 실패한다")
    void failCreateFixedDiscountPolicy(int amount) {
        assertThatThrownBy(() -> new FixedDiscountPolicy(amount))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("입력 범위를 벗어났습니다");
    }

    @Test
    @DisplayName("할인 결과가 0 미만인 경우 0으로 반환한다")
    void returnZeroIfUnderDiscountZeroResult() {
        FixedDiscountPolicy fixedDiscountPolicy = new FixedDiscountPolicy(10000);

        int discountedResult = fixedDiscountPolicy.discount(1000);

        assertThat(discountedResult).isEqualTo(0);
    }

    @Test
    @DisplayName("할인 후 결과 값을 반환한다")
    void returnDiscountResult() {
        FixedDiscountPolicy fixedDiscountPolicy = new FixedDiscountPolicy(1000);

        int discountedResult = fixedDiscountPolicy.discount(10000);

        assertThat(discountedResult).isEqualTo(9000);
    }

}
