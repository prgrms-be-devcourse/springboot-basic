package org.prgrms.kdt.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("FixedAmountVoucher의 할인된 금액 확인")
    void discount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        long afterDiscount = fixedAmountVoucher.discount(200L);

        assertThat(afterDiscount).isEqualTo(190L);
    }
}
