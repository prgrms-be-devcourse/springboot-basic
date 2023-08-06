package com.programmers.springbootbasic.domain.voucher;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTest {

    @Test
    void 정상입력값_바우처생성_성공() {
        //given
        UUID voucherId = UUID.randomUUID();
        int amount = 10_000;

        // when
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, amount);

        // then
        assertThat(fixedAmountVoucher).isNotNull();
    }

    @Test
    void 잘못된바우처아이디_바우처생성_예외발생() {
        // given
        UUID voucherId = null;
        int amount = 10_000;

        // when && then
        assertThatThrownBy(() -> new FixedAmountVoucher(voucherId, amount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
