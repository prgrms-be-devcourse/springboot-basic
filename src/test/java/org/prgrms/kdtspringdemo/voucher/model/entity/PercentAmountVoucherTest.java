package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PercentAmountVoucherTest {
    @Test
    void _할인금액이_0퍼센트_이하_일_경우_예외() {
        //given
        long discount = 0;

        //when & Then
        Assertions.assertThatThrownBy(() -> new PercentAmountVoucher(discount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 범위가 아닙니다.");
    }

    @Test
    void _할인금액이_100퍼센트_초과_일_경우_예외() {
        //given
        long discount = 101;

        //when & Then
        Assertions.assertThatThrownBy(() -> new PercentAmountVoucher(discount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 범위가 아닙니다.");
    }
}
