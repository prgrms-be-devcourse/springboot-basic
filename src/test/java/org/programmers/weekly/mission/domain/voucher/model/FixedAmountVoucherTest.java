package org.programmers.weekly.mission.domain.voucher.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인해야 한다")
    void testDiscount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertThat(fixedAmountVoucher.discount(1000), is(900L));
    }

    @Test
    @DisplayName("금액은 할인 금액보다 크거나 같아야 한다")
    void testBeforeDiscount() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertThrows(IllegalArgumentException.class, () -> fixedAmountVoucher.discount(900));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testMinusDiscount() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다")
    void testVoucherValidation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 100000))
        );
    }
}