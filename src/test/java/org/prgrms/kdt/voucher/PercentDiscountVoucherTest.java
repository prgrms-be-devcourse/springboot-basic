package org.prgrms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("할인율은 0 초과 100 미만이어야 한다.")
    void percentCheck() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -30))
        );
    }

    @Test
    @DisplayName("할인률만큼 할인할 수 있다.")
    void testDiscount() {
        final Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);
        assertEquals(7000, voucher.discount(10000));
    }

}