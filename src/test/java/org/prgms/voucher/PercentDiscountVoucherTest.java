package org.prgms.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.prgms.voucher.VoucherType.PERCENT_DISCOUNT;

class PercentDiscountVoucherTest {
    @Test
    @DisplayName("[성공] 주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        var sut = new PercentDiscountVoucher(UUID.randomUUID(), 10L, PERCENT_DISCOUNT);
        assertEquals(10, sut.discount(100L));

    }

    @Test
    @DisplayName("[실패]유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -100,PERCENT_DISCOUNT)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0, PERCENT_DISCOUNT)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 100000, PERCENT_DISCOUNT))
        );
    }

}
