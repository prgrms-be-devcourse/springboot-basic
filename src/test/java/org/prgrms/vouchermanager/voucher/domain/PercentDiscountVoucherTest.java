package org.prgrms.vouchermanager.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(-100));
    }

    @Test
    @DisplayName("디스카운트 된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        PercentDiscountVoucher PercentDiscountVoucher = new PercentDiscountVoucher(1000L);
        long discount = PercentDiscountVoucher.discount(100);

        assertEquals(discount, 0);
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(-100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(101))
        );
    }
}