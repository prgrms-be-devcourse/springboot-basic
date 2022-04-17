package org.programmers.springbootbasic.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.model.FixedAmountVoucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);

        assertEquals(900, fixedAmountVoucher.discount(1000));
    }

    @Test
    @DisplayName("디스 카운트된 금액은 마이너스가 될 수 없다.")
    void minusDiscountedAmountTest() {
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        assertEquals(0, fixedAmountVoucher.discount(900));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void voucherCreationTest() {
        assertAll("FixedAmountVoucher creation",
                () ->  assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () ->  assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () ->  assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 1000000))
        );
    }
}