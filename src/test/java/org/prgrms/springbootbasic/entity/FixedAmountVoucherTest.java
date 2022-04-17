package org.prgrms.springbootbasic.entity;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FixedAmountVoucherTest {

    @DisplayName("FixedAmountVoucher 테스트")
    @Test
    void test() {
        //given
        UUID voucherId = UUID.randomUUID();
        int amount = 10;

        //when
        var voucher = new FixedAmountVoucher(voucherId, amount);

        //then
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getAmount()).isEqualTo(amount);
    }

}