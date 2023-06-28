package org.programers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.programers.vouchermanagement.voucher.exception.WrongVoucherPolicyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PercentDiscountPolicyTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    void 할인비율이_유효하지_않으면_예외가_발생한다(int percent) {
        // given && when && then
        assertThatThrownBy(() -> new PercentDiscountPolicy(percent))
                .isInstanceOf(WrongVoucherPolicyException.class)
                .hasMessage("가능한 할인 비율은 0 이상 100 이하 입니다.");
    }

    @Test
    void 정률적_할인을_적용한다() {
        // given
        PercentDiscountPolicy policy = new PercentDiscountPolicy(20);

        // when
        int result = policy.discount(1000);

        // then
        assertThat(result).isEqualTo(800);
    }
}
