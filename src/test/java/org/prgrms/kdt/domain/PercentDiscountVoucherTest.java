package org.prgrms.kdt.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher의 할인된 금액 확인")
    void discount() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        long afterDiscount = percentDiscountVoucher.discount(200L);

        assertThat(afterDiscount).isEqualTo(180L);
    }
}
