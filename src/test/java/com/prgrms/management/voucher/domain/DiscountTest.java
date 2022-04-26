package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DiscountTest {

    @Test
    void Voucher_생성() {
        FixedVoucher fixedVoucher = new FixedVoucher(1000);
        Assertions.assertThat(fixedVoucher.getVoucherId()).isNotNull();
    }

    @Test
    void Voucher_생성_실패() {
        Assertions.assertThatThrownBy(()-> new PercentVoucher(1000000))
                .isInstanceOf(NumberFormatException.class);
    }

}