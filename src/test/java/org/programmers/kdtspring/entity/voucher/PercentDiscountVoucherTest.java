package org.programmers.kdtspring.entity.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("PercentDiscountVoucher 테스트")
    void createPercentDiscountVoucher() {
        UUID voucherId = UUID.randomUUID();
        int percent = 10;

        Voucher voucher = new PercentDiscountVoucher(voucherId, percent, VoucherType.PercentDiscountVoucher.name());

        assertAll(
                () -> assertThat(voucher.getVoucherId()).isEqualTo(voucherId),
                () -> assertThat(voucher.getDiscount()).isEqualTo(percent)
        );
    }
}