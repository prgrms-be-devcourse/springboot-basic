package org.prgrms.kdt.engine.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.engine.voucher.VoucherType;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("주어진 금액만큼 할인 해야한다")
    void testDiscount() {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
        assertThat(voucher.discount(1000L), is(900L));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testMinusAmount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -1L));
    }

    @Test
    @DisplayName("최종 금액은 마이너스가 될 수 없다")
    void testMinusDiscount() {
        var voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        assertThat(voucher.discount(900L), is(0L));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다")
    void testVoucherCreation() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0L));
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100L));
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000000L));
    }
}
