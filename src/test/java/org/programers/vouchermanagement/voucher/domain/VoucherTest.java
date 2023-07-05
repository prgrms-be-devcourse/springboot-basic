package org.programers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {
    @Test
    void 정량적_할인을_적용한다() {
        // given
        Voucher voucher = new Voucher(new FixedAmountPolicy(100), VoucherType.FIXED_AMOUNT);

        // when
        int result = voucher.discount(1000);

        // then
        assertThat(result).isEqualTo(900);
    }

    @Test
    void 정률적_할인을_적용한다() {
        // given
        Voucher voucher = new Voucher(new PercentDiscountPolicy(20), VoucherType.PERCENT);

        // when
        int result = voucher.discount(1000);

        // then
        assertThat(result).isEqualTo(800);

    }
}
