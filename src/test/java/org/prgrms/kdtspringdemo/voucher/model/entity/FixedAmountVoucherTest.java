package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

class FixedAmountVoucherTest {
    @Test
    void _할인금액이_0원_이하_일_경우_예외() {
        //given
        UUID voucherId = UUID.randomUUID();
        long discount = 0;

        //when & Then
        Assertions.assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, VoucherType.FIXED, discount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 범위가 아닙니다.");

    }
}
