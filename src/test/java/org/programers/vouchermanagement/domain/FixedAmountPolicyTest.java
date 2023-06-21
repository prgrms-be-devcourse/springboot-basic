package org.programers.vouchermanagement.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountPolicyTest {

    @Test
    void 정량적_할인을_적용한다() {
        // given
        FixedAmountPolicy policy = new FixedAmountPolicy();

        // when
        int result = policy.discount(1000);

        // then
        assertThat(result).isEqualTo(900);
    }
}
