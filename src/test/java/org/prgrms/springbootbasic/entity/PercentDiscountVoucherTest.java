package org.prgrms.springbootbasic.entity;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PercentDiscountVoucherTest {

    @DisplayName("PercentDiscountVoucher 테스트")
    @Test
    void test() {
        //given
        UUID voucherId = UUID.randomUUID();
        int percent = 10;

        //when
        var voucher = new PercentDiscountVoucher(voucherId, percent);

        //then
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getPercent()).isEqualTo(percent);
    }
}