package org.prgrms.vouchermanager.voucher.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("Amount는 0이 될 수 없다.")
    void testVoucherCreationWithZeroPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(0));
    }

    @Test
    @DisplayName("Amount는 마이너스가 될 수 없다.")
    void testVoucherCreationWithMinusPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(-100));
    }

    @Test
    @DisplayName("Amount는 MAX_VOUCHER_AMOUNT을 초과할 수 없다.")
    void testVoucherCreationWithOverHundredPercent() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscountVoucher(100001));
    }
}