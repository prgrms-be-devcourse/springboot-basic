package org.prgrms.kdt.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher의 할인된 금액 확인")
    void discount() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        long afterDiscount = percentDiscountVoucher.discount(200L);

        assertThat(afterDiscount).isEqualTo(180L);
    }
}
