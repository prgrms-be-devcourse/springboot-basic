package org.programmers.kdtspring.entity.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher 테스트")
    void createFixedAmountVoucher() {
        UUID voucherId = UUID.randomUUID();
        int amount = 10000;

        Voucher voucher = new FixedAmountVoucher(voucherId, amount, VoucherType.FixedAmountVoucher.name());

        assertAll(
                () -> assertThat(voucher.getVoucherId()).isEqualTo(voucherId),
                () -> assertThat(voucher.getDiscount()).isEqualTo(amount)
        );
    }
}