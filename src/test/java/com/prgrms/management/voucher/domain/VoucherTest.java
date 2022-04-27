package com.prgrms.management.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class VoucherTest {

    @Test
    void Voucher_생성() {
        FixedVoucher fixedVoucher = new FixedVoucher(UUID.randomUUID(), LocalDateTime.now(),null,1000, VoucherType.FIXED);
        Assertions.assertThat(fixedVoucher.getVoucherId()).isNotNull();
    }

    @Test
    void Voucher_생성_실패() {
        Assertions.assertThatThrownBy(() -> new FixedVoucher(UUID.randomUUID(), LocalDateTime.now(),null,1000000, VoucherType.FIXED))
                .isInstanceOf(NumberFormatException.class);
    }

}