package org.devcourse.voucher.domain.voucher.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountPolicyTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 100, 0, 20, 34, 66})
    @DisplayName("퍼센트 할인 정책을 생성에 성공한다")
    void successCreatePercentDiscountPolicy(int amount) {
        PercentDiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(amount);

        assertThat(percentDiscountPolicy).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1999, 100000000, 101})
    @DisplayName("고정 할인 정책을 생성에 실패한다")
    void failCreatePercentDiscountPolicy(int amount) {
        assertThatThrownBy(() -> new PercentDiscountPolicy(amount))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("입력 범위를 벗어났습니다");
    }

    @Test
    @DisplayName("할인 후 결과 값을 반환한다")
    void returnDiscountResult() {
        PercentDiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(20);

        int discountedResult = percentDiscountPolicy.discount(10000);

        assertThat(discountedResult).isEqualTo(8000);
    }

}
