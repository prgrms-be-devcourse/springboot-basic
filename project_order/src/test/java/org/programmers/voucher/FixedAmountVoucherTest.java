package org.programmers.voucher;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인이 되어야한다")
    public void discount() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        // when
        long discountedAmount = fixedAmountVoucher.discount(100L);

        // then
        assertThat(discountedAmount, is(90L));
    }

}