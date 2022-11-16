package org.programmers.weekly.mission.domain.voucher.model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("주어진 할인율 만큼 할인해야 한다")
    void testDiscount() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        assertThat(percentDiscountVoucher.discount(1000), is(900L));
    }

    @Test
    @DisplayName("할인율은 마이너스가 될 수 없다")
    void testMinusPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10));
    }

    @Test
    @DisplayName("할인율은 100을 넘을 수 없다")
    void testOver100Percent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 110));
    }
}