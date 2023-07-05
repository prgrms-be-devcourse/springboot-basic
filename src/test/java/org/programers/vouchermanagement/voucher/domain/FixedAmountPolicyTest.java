package org.programers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.Test;
import org.programers.vouchermanagement.voucher.exception.WrongVoucherPolicyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FixedAmountPolicyTest {
    @Test
    void 할인금액이_0보다_작으면_예외가_발생한다() {
        // given && when && then
        assertThatThrownBy(() -> new FixedAmountPolicy(-1))
                .isInstanceOf(WrongVoucherPolicyException.class)
                .hasMessage("가능한 할인 금액은 0 이상 입니다.");
    }

    @Test
    void 할인금액이_상품가격보다_크면_예외가_발생한다() {
        // given && when && then
        assertThatThrownBy(() -> new FixedAmountPolicy(101).discount(100))
                .isInstanceOf(WrongVoucherPolicyException.class)
                .hasMessage("기존 금액을 초과해서 할인할 수 없습니다.");
    }

    @Test
    void 정량적_할인을_적용한다() {
        // given
        FixedAmountPolicy policy = new FixedAmountPolicy(100);

        // when
        int result = policy.discount(1000);

        // then
        assertThat(result).isEqualTo(900);
    }
}
