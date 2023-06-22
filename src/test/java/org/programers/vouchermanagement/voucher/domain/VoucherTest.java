package org.programers.vouchermanagement.voucher.domain;

import org.junit.jupiter.api.Test;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.domain.Voucher;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTest {

    @Test
    void 정량적_할인을_적용한다() {
        // given
        Voucher voucher = new Voucher(new FixedAmountPolicy());

        // when
        int result = voucher.discount(1000);

        // then
        assertThat(result).isEqualTo(900);
    }

    @Test
    void 정률적_할인을_적용한다() {
        // given
        Voucher voucher = new Voucher(new PercentDiscountPolicy());

        // when
        int result = voucher.discount(1000);

        // then
        assertThat(result).isEqualTo(800);

    }
}
