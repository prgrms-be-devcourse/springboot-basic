package org.prgrms.dev.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.dev.voucher.exception.InvalidArgumentException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {
    @DisplayName("주어진 할인율만큼 할인을 해야한다.")
    @Test
    void discountTest() {
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);
        assertEquals(900, percentDiscountVoucher.discount(3000));
    }

    @DisplayName("유효한 할인율(0%~100%)으로만 생성할 수 있다.")
    @Test
    void voucherCreationTest() {
        assertAll("PercentDiscountVoucher creation",
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), -10)),
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(InvalidArgumentException.class, () -> new PercentDiscountVoucher(UUID.randomUUID(), 1000))
        );
    }

}