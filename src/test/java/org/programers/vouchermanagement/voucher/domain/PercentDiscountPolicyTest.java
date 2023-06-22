package org.programers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.Test;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountPolicyTest {

    @Test
    void 정률적_할인을_적용한다() {
        // given
        PercentDiscountPolicy policy = new PercentDiscountPolicy();

        // when
        int result = policy.discount(1000);

        // then
        assertThat(result).isEqualTo(800);
    }
}
