package org.programmers.springbootbasic.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.model.PercentDiscountVoucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("주어진 할인율만큼 할인을 해야한다.")
    void testDiscount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("유효한 할안율로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () ->  assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10)),
                () ->  assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 1000000)),
                () ->  assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0))
        );
    }
}