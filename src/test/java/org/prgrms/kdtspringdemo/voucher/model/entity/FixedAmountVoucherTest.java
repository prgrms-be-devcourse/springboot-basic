package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
class FixedAmountVoucherTest {
    @Test
    void _할인금액이_0원_이하_일_경우_예외() {
        //given
        long discount = 0;

        //when & Then
        Assertions.assertThatThrownBy(() -> new FixedAmountVoucher(discount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 범위가 아닙니다.");

    }
}
