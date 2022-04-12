package org.prgrms.vouchermanager.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercentDiscountVoucherTest {

    @Test
    @DisplayName("퍼센트로 금액을 할인한다")
    void testWithPercentDiscount() {
        //given
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(10);

        //when
        long discounted = percentDiscountVoucher.discount(100);

        //then
        assertEquals(90, discounted);

    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다.")
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(-100));
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