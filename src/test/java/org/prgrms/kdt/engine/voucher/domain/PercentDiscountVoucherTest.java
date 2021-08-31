package org.prgrms.kdt.engine.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("주어진 할인율만큼 할인 해야한다")
    void testDiscount() {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 50L);
        assertThat(voucher.discount(1000L), is(500L));
    }

    @Test
    @DisplayName("할인율은 마이너스가 될 수 없다")
    void testMinusAmount() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -1L));
    }

    @Test
    @DisplayName("최종 금액은 최소 0이다")
    void testMinusDiscount() {
        var voucher = new PercentDiscountVoucher(UUID.randomUUID(), 100L);
        assertThat(voucher.discount(1000L), is(0L));
    }

    @Test
    @DisplayName("유효한 할인율로만 생성할 수 있다")
    void testVoucherCreation() {
        // 1이상 100이하만 가능
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0L));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100L));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 101L));
    }
}
